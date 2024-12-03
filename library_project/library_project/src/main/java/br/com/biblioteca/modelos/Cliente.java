package br.com.biblioteca.modelos;

public class Cliente {
    private int id;
    private String cpf;
    private String telefone;
    private String endereco;
    private String nome;
    private String dataNascimento;
    private String preferencias;
    private byte[] fotoPerfil;
    

    // Construtor
    public Cliente(int id, String cpf, String telefone, String endereco, String nome, String dataNascimento, String preferencias, byte[] fotoPerfil) {
        this.id = id;
        this.cpf = cpf;
        this.telefone = telefone;
        this.endereco = endereco;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.preferencias = preferencias;
        this.fotoPerfil = fotoPerfil;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getPreferencias() {
        return preferencias;
    }

    public void setPreferencias(String preferencias) {
        this.preferencias = preferencias;
    }
    
    public byte[] getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(byte[] fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    // Método toString
    @Override
    public String toString() {
        return "Cliente [id=" + id + ", cpf=" + cpf + ", telefone=" + telefone + ", endereco=" + endereco + 
               ", nome=" + nome + ", dataNascimento=" + dataNascimento + ", preferencias=" + preferencias + "]";
    }
}
