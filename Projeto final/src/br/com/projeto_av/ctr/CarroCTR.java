package br.com.projeto_av.ctr;

import java.sql.ResultSet;
import br.com.projeto_av.dto.FornecedorDTO;
import br.com.projeto_av.dto.CarroDTO;
import br.com.projeto_av.dao.CarroDAO;
import br.com.projeto_av.dao.ConexaoDAO;
import br.com.projeto_av.dao.CarroDAO;

public class CarroCTR {

    CarroDAO carroDAO = new CarroDAO();

    public CarroCTR() {

    }

    public String inserirCarro(CarroDTO carroDTO, FornecedorDTO fornecedorDTO) {
        try {
            if (carroDAO.inserirCarro(carroDTO, fornecedorDTO)) {
                return "Carro Cadastrado com Sucesso!";
            } else {
                return "Carro NÃO cadastrado!";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Carro NÃO cadastrado!";
        }
    }

    public String alterarCarro(CarroDTO carroDTO, FornecedorDTO fornecedorDTO) {
        try {
            if (carroDAO.alterarCarro(carroDTO, fornecedorDTO)) {
                return "Carro Alterado com Sucesso!";
            } else {
                return "Carro NÃO alterado!";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Carro NÃO alterado!";
        }
    }

    public String excluirCarro(CarroDTO carroDTO) {
        try {
            if (carroDAO.excluirCarro(carroDTO)) {
                return "Carro Excluido com Sucesso!";
            } else {
                return "Carro NÃO excluido!";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Carro NÃO excluido!";
        }
    }

    public ResultSet consultarCarro(CarroDTO carroDTO, int opcao) {

        ResultSet rs = null;

        rs = carroDAO.consultarCarro(carroDTO, opcao);
        return rs;
    }

    public void CloseDB() {
        ConexaoDAO.CloseDB();
    }
}
