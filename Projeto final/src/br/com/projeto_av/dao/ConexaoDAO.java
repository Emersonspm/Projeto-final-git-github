package br.com.projeto_av.dao;

import java.sql.*;

public class ConexaoDAO {

    public static Connection con = null;

    public ConexaoDAO() {

    }

    public static void ConnectDB() {
        try {

            String dsn = "Projeto_av";
            String user = "postgres";
            String senha = "postdba";

            DriverManager.registerDriver(new org.postgresql.Driver());

            String url = "jdbc:postgresql://localhost:5432/" + dsn;

            con = DriverManager.getConnection(url, user, senha);

            con.setAutoCommit(false);
            if (con == null) {
                System.out.println("Erro ao abrir banco");
            }

        } catch (Exception e) {
            System.out.println("Problema ao abrir base de dados! " + e.getMessage());
        }
    }
    
    public static void CloseDB(){
        try{
            con.close();
        }
        catch(Exception e){
            System.out.println("Problema ao fechar a base de dados! " + e.getMessage());
        }
    }
    
    
}