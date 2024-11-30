package br.com.biblioteca.servicos;

import java.sql.Connection;
import java.sql.DriverManager;

import br.com.biblioteca.modelos.Livro;

public class TesteCadastroLivro {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_data", "root", "9127");
            GerenciadorLivro gerenciadorLivro = new GerenciadorLivro(connection);

            // Testando com um livro 
            Livro livro = new Livro(0, "9780134685991", "O Alquimista", "Paulo Coelho", "1ª", "Ficção", 1, true);
            try {
                gerenciadorLivro.cadastrarLivro(livro);
                System.out.println("Livro cadastrado com sucesso!");
            } catch (IllegalArgumentException e) {
                System.out.println("Erro ao cadastrar livro: " + e.getMessage());
            }


            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
