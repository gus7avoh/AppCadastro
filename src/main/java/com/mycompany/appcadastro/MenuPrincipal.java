/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.appcadastro;

/**
 *
 * @author Gustavo Henrique
 */
import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {
    
    public MenuPrincipal() {
        setTitle("Sistema de Gestão - Java + MySQL Docker");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 50));

        JButton btnCadastro = new JButton("Cadastrar Novo Usuário");
        btnCadastro.addActionListener(e -> new FormCadastro().setVisible(true));

        JButton btnListagem = new JButton("Listar Usuários");
        btnListagem.addActionListener(e -> new FormListagem().setVisible(true));

        add(btnCadastro);
        add(btnListagem);
    }

    public static void main(String[] args) {
        // Inicializa a interface gráfica na thread correta do Swing
        SwingUtilities.invokeLater(() -> {
            new MenuPrincipal().setVisible(true);
        });
    }
}