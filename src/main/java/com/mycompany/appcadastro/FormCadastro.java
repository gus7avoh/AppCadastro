package com.mycompany.appcadastro;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Gustavo Henrique
 */
import javax.swing.*;
import java.awt.*;

public class FormCadastro extends JFrame {
    private JTextField txtNome, txtEmail, txtTelefone;
    private JPasswordField txtSenha;

    public FormCadastro() {
        setTitle("Cadastro de Usuário");
        setSize(300, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 10, 10));

        add(new JLabel(" Nome:"));
        txtNome = new JTextField(); add(txtNome);

        add(new JLabel(" Email:"));
        txtEmail = new JTextField(); add(txtEmail);

        add(new JLabel(" Telefone:"));
        txtTelefone = new JTextField(); add(txtTelefone);

        add(new JLabel(" Senha:"));
        txtSenha = new JPasswordField(); add(txtSenha);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvarUsuario());
        add(btnSalvar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        add(btnCancelar);
    }

    private void salvarUsuario() {
        Usuario u = new Usuario();
        u.setNome(txtNome.getText());
        u.setEmail(txtEmail.getText());
        u.setTelefone(txtTelefone.getText());
        u.setSenha(new String(txtSenha.getPassword()));

        // Usando o Singleton para acessar a base de dados!
        MySQLRepository.getInstance().adicionarUsuario(u);
        
        JOptionPane.showMessageDialog(this, "Usuário Salvo com Sucesso!");
        dispose(); // Fecha a janela
    }
}