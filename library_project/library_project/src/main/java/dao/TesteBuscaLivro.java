package dao;

import br.com.biblioteca.modelos.Livro;
import br.com.biblioteca.servicos.GerenciadorLivro;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

public class TesteBuscaLivro {
    public static void main(String[] args) {
        try {
            // Conexão com o banco de dados
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_data", "root", "9127");
            GerenciadorLivro gerenciadorLivro = new GerenciadorLivro(connection);

            // Buscar livros por autor
            System.out.println("Livros do autor 'Adolf Hitler':");
            List<Livro> livrosPorAutor = gerenciadorLivro.buscarLivrosPorAutor("Adolf Hitler");
            for (Livro livro : livrosPorAutor) {
                System.out.println(livro);
            }

            // Buscar livros por categoria
            System.out.println("\nLivros da categoria 'Clássico':");
            List<Livro> livrosPorCategoria = gerenciadorLivro.buscarLivrosPorCategoria("Clássico");
            for (Livro livro : livrosPorCategoria) {
                System.out.println(livro);
            }

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

