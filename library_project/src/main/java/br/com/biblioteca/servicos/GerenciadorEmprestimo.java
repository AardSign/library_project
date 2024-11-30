package br.com.biblioteca.servicos;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

import br.com.biblioteca.modelos.Emprestimo;
import dao.EmprestimoDAO;

public class GerenciadorEmprestimo {
    private EmprestimoDAO emprestimoDAO;

    public GerenciadorEmprestimo(Connection connection) {
        this.emprestimoDAO = new EmprestimoDAO(connection);
    }

    public void registrarEmprestimo(int clienteId, int livroId) throws SQLException {
        LocalDate hoje = LocalDate.now();
        LocalDate dataPrevista = hoje.plusDays(14); // 14 dias para devolução

        Emprestimo emprestimo = new Emprestimo(0, clienteId, livroId, hoje.toString(), null, dataPrevista.toString(), "ativo");
        emprestimoDAO.registrarEmprestimo(emprestimo);
    }

    public void devolverLivro(int emprestimoId) throws SQLException {
        LocalDate hoje = LocalDate.now();
        emprestimoDAO.concluirEmprestimo(emprestimoId, hoje.toString());
    }
}
