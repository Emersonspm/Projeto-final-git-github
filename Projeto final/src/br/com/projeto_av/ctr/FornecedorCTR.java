package br.com.projeto_av.ctr;

import java.sql.ResultSet;
import br.com.projeto_av.dto.FornecedorDTO;
import br.com.projeto_av.dao.FornecedorDAO;
import br.com.projeto_av.dao.ConexaoDAO;


public class FornecedorCTR {

    FornecedorDAO fornecedorDAO = new FornecedorDAO();

    public FornecedorCTR() {

    }

    public String inserirFornecedor(FornecedorDTO fornecedorDTO) {
        try {
            
            if (fornecedorDAO.inserirFornecedor(fornecedorDTO)) {
                return "Fornecedor Cadastro com Sucesso!";
            } else {
                return "Fornecedor NÃO cadastrado";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Fornecedor NÃO cadastrado!";
        }
    }

    public ResultSet consultarFornecedor(FornecedorDTO fornecedorDTO, int opcao) {

        ResultSet rs = null;

        rs = fornecedorDAO.consultarFornecedor(fornecedorDTO, opcao);

        return rs;
    }

    public void CloseDB() {
        ConexaoDAO.CloseDB();
    }

    public String alterarFornecedor(FornecedorDTO fornecedorDTO) {
        try {
            
            if (fornecedorDAO.alterarFornecedor(fornecedorDTO)) {
                return "Fornecedor Alterado com Sucesso!";
            } else {
                return "Fornecedor NÃO Alterado";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Fornecedor NÃO alterado!";
        }
    }

    public String excluirFornecedor(FornecedorDTO fornecedorDTO) {
        try {
            
            if (fornecedorDAO.excluirFornecedor(fornecedorDTO)) {
                return "Fornecedor Excluído com Sucesso!";
            } else {
                return "Fornecedor NÃO Excluído";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Fornecedor NÃO deletado!";
        }
    }
}
