package br.com.biblioteca.servicos;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.biblioteca.modelos.Emprestimo;
import br.com.biblioteca.modelos.Livro;
import dao.EmprestimoDAO;
import dao.LivroDAO;

public class Relatorios {
    private EmprestimoDAO emprestimoDAO;
    private LivroDAO livroDAO;

    public Relatorios(Connection connection) {
        this.emprestimoDAO = new EmprestimoDAO(connection);
        this.livroDAO = new LivroDAO(connection);
    }

    public void relatorioLivrosDisponiveis() throws SQLException {
        List<Livro> livros = livroDAO.listarLivrosDisponiveis();
        System.out.println("Livros disponíveis:");
        for (Livro livro : livros) {
            System.out.println(livro);
        }
    }

    public void relatorioEmprestimosAtivos() throws SQLException {
        List<Emprestimo> emprestimos = emprestimoDAO.listarEmprestimosAtivos();
        System.out.println("Empréstimos ativos:");
        for (Emprestimo emprestimo : emprestimos) {
            System.out.println(emprestimo);
        }
    }
}
