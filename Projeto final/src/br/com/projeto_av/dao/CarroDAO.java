package br.com.projeto_av.dao;

import java.sql.*;
import br.com.projeto_av.dto.CarroDTO;
import br.com.projeto_av.dto.FornecedorDTO;

public class CarroDAO {

    public CarroDAO() {

    }
    private ResultSet rs = null;

    private Statement stmt = null;

    public boolean inserirCarro(CarroDTO carroDTO, FornecedorDTO fornecedorDTO) {
        try {

            ConexaoDAO.ConnectDB();

            stmt = ConexaoDAO.con.createStatement();

            String comando = "Insert into carro (placa_carro, marca_carro, modelo_carro, "
                    + "chassi_carro, ano_carro, cor_carro, valor_carro, id_carro, id_for) values ( "
                    + "'" + carroDTO.getPlaca_carro() + "', "
                    + "'" + carroDTO.getMarca_carro() + "', "
                    + "'" + carroDTO.getModelo_carro() + "', "
                    + "'" + carroDTO.getChassi_carro() + "', "
                    + "'" + carroDTO.getAno_carro() + "', "
                    + "'" + carroDTO.getCor_carro() + "', "
                    + carroDTO.getValor_carro() + ", "
                    + carroDTO.getId_carro() + ", "
                    + fornecedorDTO.getId_for() + ") ";

            stmt.execute(comando.toUpperCase());
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

    public boolean alterarCarro(CarroDTO carroDTO, FornecedorDTO fornecedorDTO) {

        try {

            ConexaoDAO.ConnectDB();

            stmt = ConexaoDAO.con.createStatement();

            String comando = "Update carro set "
                    + "placa_carro = '" + carroDTO.getPlaca_carro() + "', "
                    + "marca_carro = '" + carroDTO.getMarca_carro() + "', "
                    + "modelo_carro = '" + carroDTO.getModelo_carro() + "', "
                    + "chassi_carro = '" + carroDTO.getChassi_carro() + "', "
                    + "ano_carro = '" + carroDTO.getAno_carro() + "', "
                    + "cor_carro = '" + carroDTO.getCor_carro() + "', "
                    + "valor_carro = " + carroDTO.getValor_carro() + ", "
                    + "id_carro = " + carroDTO.getId_carro() + ", "
                    + "id_for = " + fornecedorDTO.getId_for() + " "
                    + "where id_carro = " + carroDTO.getId_carro();

            stmt.execute(comando.toUpperCase());
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

    public boolean excluirCarro(CarroDTO carroDTO) {

        try {

            ConexaoDAO.ConnectDB();

            stmt = ConexaoDAO.con.createStatement();

            String comando = "Delete from carro where id_carro = "
                    + carroDTO.getId_carro();

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

    public ResultSet consultarCarro(CarroDTO carroDTO, int opcao) {

        try {

            ConexaoDAO.ConnectDB();

            stmt = ConexaoDAO.con.createStatement();

            String comando = "";
            switch (opcao) {
                case 1:
                    comando = "Select p.* "
                            + "from carro p "
                            + "where p.placa_carro ilike '" + carroDTO.getPlaca_carro() + "%' "
                            + "order by p.placa_carro";
                    break;
                case 2:
                    comando = "Select p.*, f.nome_for, f.id_for "
                            + "from carro p, fornecedor f "
                            + " where p.id_for = f.id_for and "
                            + "p.id_carro = " + carroDTO.getId_carro();
                    break;

            }

            rs = stmt.executeQuery(comando.toUpperCase());
            return rs;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return rs;

        }
    }

}
