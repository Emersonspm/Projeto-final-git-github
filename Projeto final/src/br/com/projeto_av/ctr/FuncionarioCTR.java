package br.com.projeto_av.ctr;

import java.sql.ResultSet;
import br.com.projeto_av.dto.FuncionarioDTO;
import br.com.projeto_av.dao.FuncionarioDAO;
import br.com.projeto_av.dao.ConexaoDAO;

public class FuncionarioCTR {

    FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

    public FuncionarioCTR() {

    }

    public String inserirFuncionario(FuncionarioDTO funcionarioDTO) {
        try {

            if (funcionarioDAO.inserirFuncionario(funcionarioDTO)) {
                return "Funcionario Cadastrado com Sucesso";
            } else {
                return "Funcionario NÃO Cadastrado!";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Funcionario NÃO Cadastrado!";
        }
    }

    public String alterarFuncionario(FuncionarioDTO funcionarioDTO) {
        try {

            if (funcionarioDAO.alterarFuncionario(funcionarioDTO)) {
                return "Funcionario Alterado com Sucesso";
            } else {
                return "Funcionario NÃO Alterado!";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Funcionario NÃO Alterado!";
        }
    }

    public String excluirFuncionario(FuncionarioDTO funcionarioDTO) {
        try {

            if (funcionarioDAO.alterarFuncionario(funcionarioDTO)) {
                return "Funcionario Excluido com Sucesso";
            } else {
                return "Funcionario NÃO Excluido!";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Funcionario NÃO Excluido!";
        }
    }

    public ResultSet consultarFuncionario(FuncionarioDTO funcionarioDTO, int opcao) {

        ResultSet rs = null;

        rs = funcionarioDAO.consultarFuncionario(funcionarioDTO, opcao);
        return rs;
    }

    public String logarFuncionario(FuncionarioDTO funcionarioDTO) {

        return funcionarioDAO.logarFuncionario(funcionarioDTO);

    }

    public void CloseDB() {
        ConexaoDAO.CloseDB();
    }
}
