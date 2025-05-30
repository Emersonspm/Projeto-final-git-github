package br.com.projeto_av.ctr;

import br.com.projeto_av.dao.ConexaoDAO;
import br.com.projeto_av.dao.AluguelDAO;
import br.com.projeto_av.dto.AluguelDTO;
import br.com.projeto_av.dto.ClienteDTO;
import javax.swing.JTable;

public class AluguelCTR {

    AluguelDAO aluguelDAO = new AluguelDAO();

    public AluguelCTR() {

    }

    public String inserirVenda(AluguelDTO aluguelDTO, ClienteDTO clienteDTO, JTable carro) {
        try {

            if (aluguelDAO.inserirAluguel(aluguelDTO, clienteDTO, carro)) {
                return "Aluguel Cadastrada com Sucesso";
            } else {
                return "Aluguel NÃO cadastrada!";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Aluguel NÃO cadastrada!";
        }
    }

    public void CloseDB() {
        ConexaoDAO.CloseDB();
    }
}
