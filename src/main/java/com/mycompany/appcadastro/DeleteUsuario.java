package com.mycompany.appcadastro;
 
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
 

public class DeleteUsuario extends JFrame {
 
    private List<Usuario> usuarios;
 
    private JTable tblUsuarios;
    private DefaultTableModel tableModel;
    private JTextField txtId;
    private JLabel lblResposta;
 
    public DeleteUsuario() {
        setTitle("Deletar Usuário");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
 
        // --- Título ---
        JLabel lblTitulo = new JLabel("DELETAR USUÁRIO", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setOpaque(true);
        lblTitulo.setBackground(new Color(50, 50, 60));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(lblTitulo, BorderLayout.NORTH);
 
        // --- Tabela ---
        tableModel = new DefaultTableModel(new String[]{"ID", "Nome", "Email", "Telefone"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // impede edição direta na tabela
            }
        };
 
        tblUsuarios = new JTable(tableModel);
        tblUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblUsuarios.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblUsuarios.setRowHeight(24);
 
        // Ao clicar na linha, preenche o campo de ID automaticamente
        tblUsuarios.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int linha = tblUsuarios.getSelectedRow();
                if (linha != -1) {
                    String id = tableModel.getValueAt(linha, 0).toString();
                    txtId.setText(id);
                    lblResposta.setText("");
                }
            }
        });
 
        JScrollPane scrollPane = new JScrollPane(tblUsuarios);
        add(scrollPane, BorderLayout.CENTER);
 
        // --- Painel inferior: campo ID + botão + label resposta ---
        JPanel painelInferior = new JPanel(new GridBagLayout());
        painelInferior.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;
 
        // Label "ID do usuário para deletar"
        JLabel lblCampoId = new JLabel("ID do usuário para deletar:");
        lblCampoId.setFont(new Font("Segoe UI", Font.BOLD, 13));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1;
        painelInferior.add(lblCampoId, gbc);
 
        // Campo de texto do ID
        txtId = new JTextField();
        txtId.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.3;
        painelInferior.add(txtId, gbc);
 
        // Botão deletar
        JButton btnDeletar = new JButton("Deletar");
        btnDeletar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnDeletar.setBackground(new Color(200, 50, 50));
        btnDeletar.setForeground(Color.WHITE);
        btnDeletar.setFocusPainted(false);
        btnDeletar.addActionListener(e -> deletarUsuario());
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.1;
        painelInferior.add(btnDeletar, gbc);
 
        // Label de resposta (sucesso/erro)
        lblResposta = new JLabel("");
        lblResposta.setFont(new Font("Segoe UI", Font.BOLD, 13));
        gbc.gridx = 2; gbc.gridy = 1; gbc.weightx = 0.6;
        painelInferior.add(lblResposta, gbc);
 
        add(painelInferior, BorderLayout.SOUTH);
 
        // Carrega os usuários ao abrir a tela
        carregarUsuarios();
    }
 
    private void carregarUsuarios() {
        usuarios = MySQLRepository.getInstance().listarUsuarios();
 
        tableModel.setRowCount(0);
 
        for (Usuario u : usuarios) {
            tableModel.addRow(new Object[]{
                u.getId(),
                u.getNome(),
                u.getEmail(),
                u.getTelefone()
            });
        }
    }
 
    private void deletarUsuario() {
        int id;
 
        try {
            id = Integer.parseInt(txtId.getText().trim());
        } catch (NumberFormatException e) {
            lblResposta.setForeground(new Color(200, 50, 50));
            lblResposta.setText("ID inválido! Digite um número.");
            return;
        }
 
        boolean sucesso = MySQLRepository.getInstance().deletarUsuario(id, (ArrayList<Usuario>) usuarios);
 
        if (sucesso) {
            lblResposta.setForeground(new Color(50, 160, 50));
            lblResposta.setText("Usuário deletado com sucesso!");
            txtId.setText("");
            carregarUsuarios(); 
        } else {
            lblResposta.setForeground(new Color(200, 50, 50));
            lblResposta.setText("Erro: ID não encontrado ou falha no banco.");
        }
    }
}
 