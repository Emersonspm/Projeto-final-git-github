package br.com.projeto_av.dto;

public class ClienteDTO {

    private String nome_cli, rua_cli, bairro_cli, cidade_cli, estado_cli, cep_cli, cpf_cli;
    private int id_cli, numero_cli;

    public String getNome_cli() {
        return nome_cli;
    }

    public void setNome_cli(String nome_cli) {
        this.nome_cli = nome_cli;
    }

    public String getRua_cli() {
        return rua_cli;
    }

    public void setRua_cli(String rua_cli) {
        this.rua_cli = rua_cli;
    }

    public String getBairro_cli() {
        return bairro_cli;
    }

    public void setBairro_cli(String bairro_cli) {
        this.bairro_cli = bairro_cli;
    }

    public String getCidade_cli() {
        return cidade_cli;
    }

    public void setCidade_cli(String cidade_cli) {
        this.cidade_cli = cidade_cli;
    }

    public String getEstado_cli() {
        return estado_cli;
    }

    public void setEstado_cli(String estado_cli) {
        this.estado_cli = estado_cli;
    }

    public String getCep_cli() {
        return cep_cli;
    }

    public void setCep_cli(String cep_cli) {
        this.cep_cli = cep_cli;
    }

    public String getCpf_cli() {
        return cpf_cli;
    }

    public void setCpf_cli(String cpf_cli) {
        this.cpf_cli = cpf_cli;
    }

    public int getId_cli() {
        return id_cli;
    }

    public void setId_cli(int id_cli) {
        this.id_cli = id_cli;
    }

    public int getNumero_cli() {
        return numero_cli;
    }

    public void setNumero_cli(int numero_cli) {
        this.numero_cli = numero_cli;
    }
    
}