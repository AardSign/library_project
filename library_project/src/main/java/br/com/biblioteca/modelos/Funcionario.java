package br.com.biblioteca.modelos;

public class Funcionario {
    private int id;
    private String cpf;
    private String telefone;
    private String endereco;
    private String nome;
    private String dataNascimento;
    private String funcao;

    // Construtor
    public Funcionario(int id, String cpf, String telefone, String endereco, String nome, String dataNascimento, String funcao) {
        this.id = id;
        this.cpf = cpf;
        this.telefone = telefone;
        this.endereco = endereco;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.funcao = funcao;
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

    // Método toString
    @Override
    public String toString() {
        return "Funcionario [id=" + id + ", cpf=" + cpf + ", telefone=" + telefone + ", endereco=" + endereco + 
               ", nome=" + nome + ", dataNascimento=" + dataNascimento + ", funcao=" + funcao + "]";
    }
}

