package br.com.projeto_av.ctr;

import java.sql.ResultSet;
import br.com.projeto_av.dto.ClienteDTO;
import br.com.projeto_av.dao.ClienteDAO;
import br.com.projeto_av.dao.ConexaoDAO;

public class ClienteCTR {

    ClienteDAO clienteDAO = new ClienteDAO();

    public ClienteCTR() {

    }

    public String inserirCliente(ClienteDTO clienteDTO) {
        try {
            if (clienteDAO.inserirCliente(clienteDTO)) {
                return "Cliente cadastrado com sucesso ";
            } else {
                return "Cliente NÂO cadastrado! ";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Cliente NÃO cadastrado ";
        }
    }

    public ResultSet consultarCliente(ClienteDTO clienteDTO, int opcao) {

        ResultSet rs = null;

        rs = clienteDAO.consultarCliente(clienteDTO, opcao);

        return rs;
    }

    public void CloseDB() {
        ConexaoDAO.CloseDB();
    }
    
    public String alterarCliente(ClienteDTO clienteDTO) {
        try {
            if (clienteDAO.alterarCliente(clienteDTO)) {
                return "Cliente alterado com sucesso ";
            } else {
                return "Cliente NÂO alterado! ";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Cliente NÃO alterado ";
        }
    }
    
    public String excluirCliente(ClienteDTO clienteDTO) {
        try {
            if (clienteDAO.excluirCliente(clienteDTO)) {
                return "Cliente excluido com sucesso ";
            } else {
                return "Cliente NÂO foi excluido! ";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Cliente NÃO foi excluido ";
        }
    }
    
}
