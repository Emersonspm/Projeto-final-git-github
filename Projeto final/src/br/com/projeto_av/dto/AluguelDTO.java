package br.com.projeto_av.dto;
import java.util.Date;

public class AluguelDTO {
    
    private int id_aluguel;
    private double val_aluguel;
    private Date dat_aluguel;

    public int getId_aluguel() {
        return id_aluguel;
    }

    public void setId_aluguel(int id_aluguel) {
        this.id_aluguel = id_aluguel;
    }

    public double getVal_aluguel() {
        return val_aluguel;
    }

    public void setVal_aluguel(double val_aluguel) {
        this.val_aluguel = val_aluguel;
    }

    public Date getDat_aluguel() {
        return dat_aluguel;
    }

    public void setDat_aluguel(Date dat_aluguel) {
        this.dat_aluguel = dat_aluguel;
    }
    
}
