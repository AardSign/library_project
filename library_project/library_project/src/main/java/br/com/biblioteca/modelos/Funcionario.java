package br.com.biblioteca.modelos;

public class Funcionario {
    private int id;
    private String cpf;
    private String telefone;
    private String endereco;
    private String nome;
    private String dataNascimento;
    private String funcao;
    private String tipo; // "administrador" ou "funcionario"
    private String senha; // Novo campo para armazenar a senha
    private byte[] fotoPerfil;
    // Construtor
    public Funcionario(int id, String cpf, String telefone, String endereco, String nome, String dataNascimento, String funcao, String tipo, String senha, byte[] fotoPerfil) {
        this.id = id;
        this.cpf = cpf;
        this.telefone = telefone;
        this.endereco = endereco;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.funcao = funcao;
        this.tipo = tipo;
        this.senha = senha;
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

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }
    
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    public byte[] getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(byte[] fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    @Override
    public String toString() {
        return "Funcionario{" +
                "id=" + id +
                ", cpf='" + cpf + '\'' +
                ", telefone='" + telefone + '\'' +
                ", endereco='" + endereco + '\'' +
                ", nome='" + nome + '\'' +
                ", dataNascimento='" + dataNascimento + '\'' +
                ", funcao='" + funcao + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}

