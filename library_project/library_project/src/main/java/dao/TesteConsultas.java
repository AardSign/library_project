package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import br.com.biblioteca.modelos.Cliente;
import br.com.biblioteca.modelos.Emprestimo;
import br.com.biblioteca.modelos.Livro;

public class TesteConsultas {
    public static void main(String[] args) {
        try {
            // Conexão com o banco de dados
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_data", "root", "9127");

            // Testando consultas de clientes
            ClienteDAO clienteDAO = new ClienteDAO(connection);
            System.out.println("Clientes cadastrados:");
            List<Cliente> clientes = clienteDAO.listarClientes();
            for (Cliente cliente : clientes) {
                System.out.println(cliente);
            }

            // Buscar cliente por nome
            System.out.println("\nClientes com nome 'Josuke':");
            List<Cliente> clientesPorNome = clienteDAO.buscarClientesPorNome("Josuke");
            for (Cliente cliente : clientesPorNome) {
                System.out.println(cliente);
            }

            // Testando consultas de livros
            LivroDAO livroDAO = new LivroDAO(connection);
            System.out.println("\nLivros disponíveis:");
            List<Livro> livrosDisponiveis = livroDAO.listarLivrosDisponiveis();
            for (Livro livro : livrosDisponiveis) {
                System.out.println(livro);
            }

            // Buscar livro por título
            System.out.println("\nLivros com título 'O alquimista':");
            List<Livro> livrosPorTitulo = livroDAO.buscarLivrosPorTitulo("O Alquimista");
            for (Livro livro : livrosPorTitulo) {
                System.out.println(livro);
            }

            // Testando consultas de empréstimos
            EmprestimoDAO emprestimoDAO = new EmprestimoDAO(connection);
            System.out.println("\nEmpréstimos ativos:");
            List<Emprestimo> emprestimosAtivos = emprestimoDAO.listarEmprestimosAtivos();
            for (Emprestimo emprestimo : emprestimosAtivos) {
                System.out.println(emprestimo);
            }

            // Histórico de empréstimos de um cliente
            System.out.println("\nHistórico de empréstimos do cliente com ID 3:");
            List<Emprestimo> historicoEmprestimos = emprestimoDAO.consultarHistoricoEmprestimosPorCliente(3);
            for (Emprestimo emprestimo : historicoEmprestimos) {
                System.out.println(emprestimo);
            }

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
