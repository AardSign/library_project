package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import br.com.biblioteca.modelos.Livro;

public class LivroDAO {
    private Connection connection;

    // Construtor que inicializa a conexão
    public LivroDAO(Connection connection) {
        this.connection = connection;
    }

    // Create
    public void adicionarLivro(Livro livro) throws SQLException {
        String sql = "INSERT INTO Livros (codigo_barras, nome, autor, edicao, categoria, quantidade, disponibilidade) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, livro.getCodigoBarras());
            stmt.setString(2, livro.getNome());
            stmt.setString(3, livro.getAutor());
            stmt.setString(4, livro.getEdicao());
            stmt.setString(5, livro.getCategoria());
            stmt.setInt(6, livro.getQuantidade());
            stmt.setBoolean(7, livro.isDisponibilidade());
            stmt.executeUpdate();
        }
    }

    // Read (listar todos)
    public List<Livro> listarLivros() throws SQLException {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM Livros";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Livro livro = new Livro(
                    rs.getInt("id"),
                    rs.getString("codigo_barras"),
                    rs.getString("nome"),
                    rs.getString("autor"),
                    rs.getString("edicao"),
                    rs.getString("categoria"),
                    rs.getInt("quantidade"),
                    rs.getBoolean("disponibilidade")
                );
                livros.add(livro);
            }
        }
        return livros;
    }

    // Read (buscar por ID)
    public Livro buscarLivroPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Livros WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Livro(
                        rs.getInt("id"),
                        rs.getString("codigo_barras"),
                        rs.getString("nome"),
                        rs.getString("autor"),
                        rs.getString("edicao"),
                        rs.getString("categoria"),
                        rs.getInt("quantidade"),
                        rs.getBoolean("disponibilidade")
                    );
                }
            }
        }
        return null;
    }

    // Update
    public void atualizarLivro(Livro livro) throws SQLException {
        String sql = "UPDATE Livros SET codigo_barras = ?, nome = ?, autor = ?, edicao = ?, categoria = ?, quantidade = ?, disponibilidade = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, livro.getCodigoBarras());
            stmt.setString(2, livro.getNome());
            stmt.setString(3, livro.getAutor());
            stmt.setString(4, livro.getEdicao());
            stmt.setString(5, livro.getCategoria());
            stmt.setInt(6, livro.getQuantidade());
            stmt.setBoolean(7, livro.isDisponibilidade());
            stmt.setInt(8, livro.getId());
            stmt.executeUpdate();
        }
    }

    // Delete
    public void excluirLivro(int id) throws SQLException {
        String sql = "DELETE FROM Livros WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}

