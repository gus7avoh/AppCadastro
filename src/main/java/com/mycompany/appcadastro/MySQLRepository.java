/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.appcadastro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Gustavo Henrique
 */
public class MySQLRepository {
    private static MySQLRepository instance;
    private Connection connection;

    private MySQLRepository() {
        conectarEInicializarBanco();
    }

    public static MySQLRepository getInstance() {
        if (instance == null) {
            instance = new MySQLRepository();
        }
        return instance;
    }

    private boolean conectarEInicializarBanco() {
        try {
            String urlRaiz = "jdbc:mysql://localhost:3306/?allowPublicKeyRetrieval=true&useSSL=false";
            Connection connTemp = DriverManager.getConnection(urlRaiz, "root", "root");
            Statement stmtTemp = connTemp.createStatement();
            stmtTemp.executeUpdate("CREATE DATABASE IF NOT EXISTS app_db");
            connTemp.close();

            String urlDB = "jdbc:mysql://localhost:3306/app_db?allowPublicKeyRetrieval=true&useSSL=false";
            this.connection = DriverManager.getConnection(urlDB, "root", "root");

            String createTable = "CREATE TABLE IF NOT EXISTS usuarios ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY," 
                    + "nome VARCHAR(100) NOT NULL," 
                    + "email VARCHAR(100) UNIQUE NOT NULL," 
                    + "telefone VARCHAR(20)," 
                    + "senha VARCHAR(100) NOT NULL)";
            Statement stmt = connection.createStatement();
            stmt.execute(createTable);
            System.out.println("[INFO] - Conexão estabelecida");
            return true;
        } catch (SQLException e) {
            System.err.println("Erro Crítico de Conexão: " + e.getMessage());
        }
        return false;
    }

    public boolean adicionarUsuario(Usuario u) {
        String sql = "INSERT INTO usuarios (nome, email, telefone, senha) VALUES (?, ?, ?, ?)";
        try (
            PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, u.getNome());
            pst.setString(2, u.getEmail());
            pst.setString(3, u.getTelefone());
            pst.setString(4, u.getSenha());
            pst.executeUpdate();
            System.out.println("[INFO] - Usuario inserido");
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao adicionar: " + e.getMessage());
        }
        return false;
    }

    public List<Usuario> listarUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        try (
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Usuario u = new Usuario(
                    rs.getInt("id"), rs.getString("nome"),
                    rs.getString("email"), rs.getString("telefone"), rs.getString("senha")
                );
                lista.add(u);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar: " + e.getMessage());
        }
        return lista;
    }
    
    public boolean deletarUsuario(Usuario u){
        String queryDelete =  "DELETE FROM app_db WHERE id = ?";
        
        try{
            PreparedStatement smt = connection.prepareStatement(queryDelete);
            smt.setInt(1, u.getId());
            smt.executeUpdate();
            System.out.println("[INFO] - Usuario deletado");
            return true;
        }catch(SQLException e){
            System.out.println("[ERRO] - Não foi possivel deletar: " + e);
        }
        return false;
    }
    
    public boolean atualizarUsuario(Usuario u, String nome, String email, String senha, String telefone){
        String queryAtualizar = "UPDATE app_db SET"
                + "nome  = ?,"
                + "email = ?,"
                + "telefone = ?,"
                + "senha = ?"
                + "WHERE id = ?";
        
        try{
            PreparedStatement stm = connection.prepareStatement(queryAtualizar);
            stm.setString(1, nome);
            stm.setString(2, email);
            stm.setString(3, telefone);
            stm.setString(4, senha);
            stm.setInt(5, u.getId());
            stm.executeUpdate();
            System.out.println("[INFO] - Usuario atualizado");
            return true;
        }catch(SQLException e){
            System.out.println("[ERRO] - Não foi possivel atualizar o usuario: " + e);
        }
        return false;
    }
        
    public boolean atualizarParametro(Usuario u, String parametro, String valor){
        String queryAtualizar = "UPDATE app_db SET"
                + "? = ?"
                + "WHERE id = ?";
        try{
            PreparedStatement stm = connection.prepareStatement(queryAtualizar);
            stm.setString(1, parametro);
            stm.setString(2, valor);
            stm.setInt(5, u.getId());
            stm.executeUpdate();
            System.out.println("[INFO] - Parametro "+ parametro +" atualizado");
            return true;
        }catch(SQLException e){
            System.out.println("[ERRO] - Não foi possivel atualizar "+ parametro +" do usuario: " + e);
        }
        return false;
    }
    
}
