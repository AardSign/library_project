package br.com.biblioteca.servicos;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.biblioteca.modelos.Livro;
import dao.LivroDAO;

public class GerenciadorLivro {
    private LivroDAO livroDAO;

    public GerenciadorLivro(Connection connection) {
        this.livroDAO = new LivroDAO(connection);
    }
    
    // Buscar livros por autor
    public List<Livro> buscarLivrosPorAutor(String autor) throws SQLException {
        if (autor == null || autor.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do autor não pode ser vazio.");
        }
        return livroDAO.buscarLivrosPorAutor(autor);
    }

    // Buscar livros por categoria
    public List<Livro> buscarLivrosPorCategoria(String categoria) throws SQLException {
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new IllegalArgumentException("A categoria não pode ser vazia.");
        }
        return livroDAO.buscarLivrosPorCategoria(categoria);
    }

    public void cadastrarLivro(Livro livro) throws SQLException {
        // Validação de código de barras
    	if (livro.getCodigoBarras() == null || livro.getCodigoBarras().trim().isEmpty()) {
    	    throw new IllegalArgumentException("Código de barras não pode ser vazio.");
    	}

    	if (!ValidacaoISBN.validarISBN13(livro.getCodigoBarras())) {
    	    throw new IllegalArgumentException("Código de barras inválido (ISBN-13).");
    	}

        // Verificar se o código de barras já existe
        Livro existente = livroDAO.buscarLivroPorCodigoBarras(livro.getCodigoBarras());
        if (existente != null) {
            throw new IllegalArgumentException("Código de barras já cadastrado.");
        }

        // Validação de título
        if (livro.getNome() == null || livro.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Título do livro não pode ser vazio.");
        }

        // Validação de quantidade
        if (livro.getQuantidade() <= 0) {
            throw new IllegalArgumentException("Quantidade não pode ser negativa.");
        }

        // Salvar livro no banco
        livroDAO.adicionarLivro(livro);
    }
}
