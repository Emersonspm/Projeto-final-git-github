package br.com.projeto_av.dao;

import br.com.projeto_av.dto.ClienteDTO;
import br.com.projeto_av.dto.AluguelDTO;
import java.sql.*;
import java.text.SimpleDateFormat;
import javax.swing.JTable;

public class AluguelDAO {

    public AluguelDAO() {

    }

    private ResultSet rs = null;

    Statement stmt = null;
    Statement stmt1 = null;
    SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");

    public boolean inserirAluguel(AluguelDTO aluguelDTO, ClienteDTO clienteDTO, JTable carro) {
        try {

            ConexaoDAO.ConnectDB();

            stmt = ConexaoDAO.con.createStatement();
            stmt1 = ConexaoDAO.con.createStatement();

            String comando1 = "Insert into Aluguel (dat_aluguel, val_aluguel, "
                    + "id_cli) values ( "
                    + "to_date('" + date.format(aluguelDTO.getDat_aluguel()) + "', 'DD/MM/YYYY'), "
                    + aluguelDTO.getVal_aluguel() + ", "
                    + clienteDTO.getId_cli() + ")";

            stmt.execute(comando1.toUpperCase(), Statement.RETURN_GENERATED_KEYS);
            rs = stmt.getGeneratedKeys();
            rs.next();

            for (int cont = 0; cont < carro.getRowCount(); cont++) {
                String comando2 = "Insert into carro_aluguel (id_aluguel, id_carro, "
                        + "val_aluguel, qtd_dias) values ("
                        + rs.getInt("id_aluguel") + ", "
                        + carro.getValueAt(cont, 0) + ", "
                        + carro.getValueAt(cont, 2) + ", "
                        + carro.getValueAt(cont, 3) + "); ";

                stmt1.execute(comando2);
            }

            ConexaoDAO.con.commit();

            stmt.close();
            stmt1.close();
            rs.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            ConexaoDAO.CloseDB();
        }
    }
}
