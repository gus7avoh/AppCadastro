package com.mycompany.appcadastro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Tela para editar usuários.
 * @author Gustavo Henrique
 */
public class EditUser extends JFrame {

    private List<Usuario> usuarios;

    private JTable tblUsuarios;
    private DefaultTableModel tableModel;

    private JTextField txtId;
    private JTextField txtNome;
    private JTextField txtEmail;
    private JTextField txtTelefone;
    private JPasswordField txtSenha;
    private JLabel lblResposta;

    public EditUser() {
        setTitle("Editar Usuário");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // --- Título ---
        JLabel lblTitulo = new JLabel("EDITAR USUÁRIO", SwingConstants.CENTER);
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
                return false;
            }
        };

        tblUsuarios = new JTable(tableModel);
        tblUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblUsuarios.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblUsuarios.setRowHeight(24);

        // Ao clicar na linha, preenche os campos automaticamente
        tblUsuarios.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int linha = tblUsuarios.getSelectedRow();
                if (linha != -1) {
                    txtId.setText(tableModel.getValueAt(linha, 0).toString());
                    txtNome.setText(tableModel.getValueAt(linha, 1).toString());
                    txtEmail.setText(tableModel.getValueAt(linha, 2).toString());
                    txtTelefone.setText(tableModel.getValueAt(linha, 3).toString());
                    txtSenha.setText("");
                    lblResposta.setText("");
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(tblUsuarios);
        scrollPane.setPreferredSize(new Dimension(680, 180));
        add(scrollPane, BorderLayout.CENTER);

        // --- Painel inferior: campos de edição ---
        JPanel painelInferior = new JPanel(new GridBagLayout());
        painelInferior.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ID (somente leitura)
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.1;
        painelInferior.add(new JLabel("ID:"), gbc);

        txtId = new JTextField();
        txtId.setEditable(false);
        txtId.setBackground(new Color(220, 220, 220));
        gbc.gridx = 0; gbc.gridy = 1;
        painelInferior.add(txtId, gbc);

        // Nome
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.3;
        painelInferior.add(new JLabel("Nome:"), gbc);

        txtNome = new JTextField();
        gbc.gridx = 1; gbc.gridy = 1;
        painelInferior.add(txtNome, gbc);

        // Email
        gbc.gridx = 2; gbc.gridy = 0; gbc.weightx = 0.3;
        painelInferior.add(new JLabel("Email:"), gbc);

        txtEmail = new JTextField();
        gbc.gridx = 2; gbc.gridy = 1;
        painelInferior.add(txtEmail, gbc);

        // Telefone
        gbc.gridx = 3; gbc.gridy = 0; gbc.weightx = 0.2;
        painelInferior.add(new JLabel("Telefone:"), gbc);

        txtTelefone = new JTextField();
        gbc.gridx = 3; gbc.gridy = 1;
        painelInferior.add(txtTelefone, gbc);

        // Senha
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.1;
        painelInferior.add(new JLabel("Nova Senha:"), gbc);

        txtSenha = new JPasswordField();
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 0.3;
        painelInferior.add(txtSenha, gbc);

        // Botão Salvar
        JButton btnSalvar = new JButton("Salvar Alterações");
        btnSalvar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnSalvar.setBackground(new Color(50, 130, 200));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFocusPainted(false);
        btnSalvar.addActionListener(e -> atualizarUsuario());
        gbc.gridx = 2; gbc.gridy = 2; gbc.weightx = 0.3;
        painelInferior.add(btnSalvar, gbc);

        // Label de resposta
        lblResposta = new JLabel("");
        lblResposta.setFont(new Font("Segoe UI", Font.BOLD, 13));
        gbc.gridx = 3; gbc.gridy = 2; gbc.weightx = 0.3;
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

    private void atualizarUsuario() {
        int id;

        try {
            id = Integer.parseInt(txtId.getText().trim());
        } catch (NumberFormatException e) {
            lblResposta.setForeground(new Color(200, 50, 50));
            lblResposta.setText("Selecione um usuário na tabela!");
            return;
        }

        String nome     = txtNome.getText().trim();
        String email    = txtEmail.getText().trim();
        String telefone = txtTelefone.getText().trim();
        String senha    = new String(txtSenha.getPassword()).trim();

        boolean sucesso = MySQLRepository.getInstance()
                .atualizarUsuario(id, (ArrayList<Usuario>) usuarios, nome, email, senha, telefone);

        if (sucesso) {
            lblResposta.setForeground(new Color(50, 160, 50));
            lblResposta.setText("Usuário atualizado com sucesso!");
            carregarUsuarios();
        } else {
            lblResposta.setForeground(new Color(200, 50, 50));
            lblResposta.setText("Erro ao atualizar usuário.");
        }
    }
}