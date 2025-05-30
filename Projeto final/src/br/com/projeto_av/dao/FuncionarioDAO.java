package br.com.projeto_av.dao;

import java.sql.*;
import br.com.projeto_av.dto.FuncionarioDTO;

public class FuncionarioDAO {

    public FuncionarioDAO() {

    }

    private ResultSet rs = null;

    private Statement stmt = null;

    public boolean inserirFuncionario(FuncionarioDTO funcionarioDTO) {
        try {
            ConexaoDAO.ConnectDB();

            stmt = ConexaoDAO.con.createStatement();

            String comando = "Insert into funcionario (nome_fun, cpf_fun, "
                    + "login_fun, senha_fun, tipo_fun) values ( "
                    + "'" + funcionarioDTO.getNome_fun().toUpperCase() + "', "
                    + "'" + funcionarioDTO.getCpf_fun() + "', "
                    + "'" + funcionarioDTO.getLogin_fun() + "', "
                    + "crypt('" + funcionarioDTO.getSenha_fun() + "',gen_salt('bf', 8)) , "
                    + "'" + funcionarioDTO.getTipo_fun() + "') ";

            stmt.execute(comando);

            ConexaoDAO.con.commit();

            stmt.close();

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            ConexaoDAO.CloseDB();
        }
    }

    public boolean alterarFuncionario(FuncionarioDTO funcionarioDTO) {
        try {
            ConexaoDAO.ConnectDB();

            stmt = ConexaoDAO.con.createStatement();

            String comando = "";
            comando = "Update funcionario set "
                    + "nome_fun = '" + funcionarioDTO.getNome_fun().toUpperCase() + "', "
                    + "cpf_fun = '" + funcionarioDTO.getCpf_fun().toUpperCase() + "', "
                    + "login_fun = '" + funcionarioDTO.getLogin_fun() + "', ";

            if (funcionarioDTO.getSenha_fun() != null) {
                comando += "senha_fun = crypt('" + funcionarioDTO.getSenha_fun() + "', gen_salt('bf', 8)), ";
            }

            comando += "tipo_fun = '" + funcionarioDTO.getTipo_fun().toUpperCase() + "' "
                    + "where id_fun = " + funcionarioDTO.getId_fun();

            stmt.execute(comando);

            ConexaoDAO.con.commit();

            stmt.close();

            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;

        } finally {
            ConexaoDAO.CloseDB();
        }
    }

    public boolean excluirFuncionario(FuncionarioDTO funcionarioDTO) {
        try {

            ConexaoDAO.ConnectDB();

            stmt = ConexaoDAO.con.createStatement();

            String comando = "Delete from funcionario where id_fun = "
                    + funcionarioDTO.getId_fun();

            stmt.execute(comando);

            ConexaoDAO.con.commit();

            stmt.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            ConexaoDAO.CloseDB();
        }
    }

    public ResultSet consultarFuncionario(FuncionarioDTO funcionarioDTO, int opcao) {
        try {

            ConexaoDAO.ConnectDB();

            stmt = ConexaoDAO.con.createStatement();

            String comando = "";

            switch (opcao) {
                case 1:
                    comando = "Select f.*"
                            + "from funcionario f "
                            + "where nome_fun ilike '" + funcionarioDTO.getNome_fun() + "%' "
                            + "order by f.nome_fun";
                    break;
                case 2:
                    comando = "Select f.*"
                            + "from funcionario f "
                            + "where f.id_fun = " + funcionarioDTO.getId_fun();
                    break;
            }

            rs = stmt.executeQuery(comando.toUpperCase());
            return rs;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return rs;

        }
    }

    public String logarFuncionario(FuncionarioDTO funcionarioDTO) {
        try {

            ConexaoDAO.ConnectDB();

            stmt = ConexaoDAO.con.createStatement();

            String comando = "Select f.tipo_fun "
                    + "from Funcionario f "
                    + "where f.login_fun = '" + funcionarioDTO.getLogin_fun() + "'"
                    + " and f.senha_fun = crypt('" + funcionarioDTO.getSenha_fun() + "', senha_fun)";

            rs = null;
            rs = stmt.executeQuery(comando);
            if (rs.next()) {
                return rs.getString("tipo_fun");

            } else {
                return "";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "";

        } finally {
            ConexaoDAO.CloseDB();

        }
    }
}
