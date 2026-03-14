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
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FormListagem extends JFrame {
    public FormListagem() {
        setTitle("Listagem de Usuários");
        setSize(500, 300);
        setLocationRelativeTo(null);

        // Definir colunas da tabela (SEM A SENHA!)
        String[] colunas = {"ID", "Nome", "Email", "Telefone"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);
        JTable tabela = new JTable(modelo);

        // Buscar dados usando nosso Singleton
        List<Usuario> usuarios = MySQLRepository.getInstance().listarUsuarios();
        
        for (Usuario u : usuarios) {
            // Adiciona as linhas à tabela (omitindo u.getSenha())
            modelo.addRow(new Object[]{
                u.getId(), u.getNome(), u.getEmail(), u.getTelefone()
            });
        }

        add(new JScrollPane(tabela), BorderLayout.CENTER);
    }
}
