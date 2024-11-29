package br.com.biblioteca.modelos;

public class Livro {
    private int id;
    private String codigoBarras;
    private String nome;
    private String autor;
    private String edicao;
    private String categoria;
    private int quantidade;
    private boolean disponibilidade;

    // Construtor
    public Livro(int id, String codigoBarras, String nome, String autor, String edicao, String categoria, int quantidade, boolean disponibilidade) {
        this.id = id;
        this.codigoBarras = codigoBarras;
        this.nome = nome;
        this.autor = autor;
        this.edicao = edicao;
        this.categoria = categoria;
        this.quantidade = quantidade;
        this.disponibilidade = disponibilidade;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEdicao() {
        return edicao;
    }

    public void setEdicao(String edicao) {
        this.edicao = edicao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public boolean isDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(boolean disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    // Método toString
    @Override
    public String toString() {
        return "Livro [id=" + id + ", codigoBarras=" + codigoBarras + ", nome=" + nome + ", autor=" + autor + 
               ", edicao=" + edicao + ", categoria=" + categoria + ", quantidade=" + quantidade + 
               ", disponibilidade=" + disponibilidade + "]";
    }
}
