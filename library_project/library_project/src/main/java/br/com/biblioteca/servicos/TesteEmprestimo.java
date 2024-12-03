package br.com.biblioteca.servicos;

import java.sql.Connection;
import java.sql.DriverManager;

public class TesteEmprestimo {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_data", "root", "9127");
            GerenciadorEmprestimo gerenciadorEmprestimo = new GerenciadorEmprestimo(connection);

            // Registrar um empréstimo
            try {
                gerenciadorEmprestimo.registrarEmprestimo(3, 3); // Cliente ID 1 pega Livro ID 2
                System.out.println("Empréstimo registrado com sucesso!");
            } catch (Exception e) {
                System.out.println("Erro ao registrar empréstimo: " + e.getMessage());
            }

            // Devolver um livro
//            try {
//                gerenciadorEmprestimo.devolverLivro(3); // Empréstimo ID 1
//                System.out.println("Devolução registrada com sucesso!");
//            } catch (Exception e) {
//                System.out.println("Erro ao registrar devolução: " + e.getMessage());
//            }

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
