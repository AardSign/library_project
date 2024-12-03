package dao;

import java.sql.Connection;
import java.sql.DriverManager;

import br.com.biblioteca.modelos.Cliente;
import br.com.biblioteca.modelos.Funcionario;
import br.com.biblioteca.modelos.Livro;

public class TesteCRUD {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_data", "root", "9127");
            ClienteDAO clienteDAO = new ClienteDAO(connection);

            // Criar um novo cliente
//            Cliente cliente = new Cliente(0, "17125678901", "123456789", "Rua Exemplo, 123", "Hirose Koichi", "1984-03-28", "Ficção");
//            clienteDAO.adicionarCliente(cliente);

            // Listar todos os clientes
//            for (Cliente c : clienteDAO.listarClientes()) {
//                System.out.println(c);
//            }

            // Buscar cliente por ID
            Cliente cliente = clienteDAO.buscarClientePorId(1); // Substitua 1 pelo ID existente no banco
//            if (cliente != null) {
//                // Atualizar o nome do cliente
//                cliente.setNome("João Atualizado");
//                clienteDAO.atualizarCliente(cliente);
//                System.out.println("Cliente atualizado com sucesso.");
//            } else {
//                System.out.println("Cliente não encontrado para atualização.");
//            }
           
             //Excluir cliente
  //         clienteDAO.excluirCliente(1);

            FuncionarioDAO funcionarioDAO = new FuncionarioDAO(connection);

        // Adicionar um funcionário
//            Funcionario funcionario = new Funcionario(0, "12345678901", "999999999", "Rua A, 123", "Kishibe Rohan", "1979-10-20", "Bibliotecário");
//            funcionarioDAO.adicionarFuncionario(funcionario);

           //  Listar todos os funcionários
//            for (Funcionario f : funcionarioDAO.listarFuncionarios()) {
//                System.out.println(f);
//            }

             //Buscar funcionário por ID
//            Funcionario funcionarioEncontrado = funcionarioDAO.buscarFuncionarioPorId(1);
//            if (funcionarioEncontrado != null) {
//                System.out.println("Funcionário encontrado: " + funcionarioEncontrado);
//            }

             //Atualizar um funcionário
         //   Funcionario funcionario= funcionarioDAO.buscarFuncionarioPorId(1);
//            funcionario.setNome("Rohan Atualizado");
//            funcionarioDAO.atualizarFuncionario(funcionario);

            // Excluir um funcionário
//            funcionarioDAO.excluirFuncionario(1);
//            
            LivroDAO livroDAO = new LivroDAO(connection);

           //Adicionar livro
//            Livro livro = new Livro(0, "9780000000001", "Mein Kampf", "Adolf Hitler", "Edição Original", "História", 3, true);
//            livroDAO.adicionarLivro(livro);

            // Listar todos os livros
//            for (Livro l : livroDAO.listarLivros()) {
//                System.out.println(l);
//            }

            // Buscar livro por ID
//            Livro livroEncontrado = livroDAO.buscarLivroPorId(1);
//            if (livroEncontrado != null) {
//                System.out.println("Livro encontrado: " + livroEncontrado);
//            }

            // Atualizar um livro
//            Livro livro = livroDAO.buscarLivroPorId(1);
//            livro.setNome("O Alquimista (Edição Revisada)");
//            livroDAO.atualizarLivro(livro);

//            // Excluir um livro
         //   livroDAO.excluirLivro(1);
            
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        
    }
}

