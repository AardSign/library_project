package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import br.com.biblioteca.modelos.Funcionario;

public class FuncionarioDAO {
    private Connection connection;

    // Construtor que inicializa a conexão
    public FuncionarioDAO(Connection connection) {
        this.connection = connection;
    }

    // Create
    public void adicionarFuncionario(Funcionario funcionario) throws SQLException {
        String sql = "INSERT INTO Funcionarios (CPF, telefone, endereco, nome, data_nascimento, funcao) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, funcionario.getCpf());
            stmt.setString(2, funcionario.getTelefone());
            stmt.setString(3, funcionario.getEndereco());
            stmt.setString(4, funcionario.getNome());
            stmt.setDate(5, Date.valueOf(funcionario.getDataNascimento()));
            stmt.setString(6, funcionario.getFuncao());
            stmt.executeUpdate();
        }
    }

    // Read (listar todos)
    public List<Funcionario> listarFuncionarios() throws SQLException {
        List<Funcionario> funcionarios = new ArrayList<>();
        String sql = "SELECT * FROM Funcionarios";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Funcionario funcionario = new Funcionario(
                    rs.getInt("id"),
                    rs.getString("CPF"),
                    rs.getString("telefone"),
                    rs.getString("endereco"),
                    rs.getString("nome"),
                    rs.getDate("data_nascimento").toString(),
                    rs.getString("funcao")
                );
                funcionarios.add(funcionario);
            }
        }
        return funcionarios;
    }

    // Read (buscar por ID)
    public Funcionario buscarFuncionarioPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Funcionarios WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Funcionario(
                        rs.getInt("id"),
                        rs.getString("CPF"),
                        rs.getString("telefone"),
                        rs.getString("endereco"),
                        rs.getString("nome"),
                        rs.getDate("data_nascimento").toString(),
                        rs.getString("funcao")
                    );
                }
            }
        }
        return null;
    }

    // Update
    public void atualizarFuncionario(Funcionario funcionario) throws SQLException {
        String sql = "UPDATE Funcionarios SET CPF = ?, telefone = ?, endereco = ?, nome = ?, data_nascimento = ?, funcao = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, funcionario.getCpf());
            stmt.setString(2, funcionario.getTelefone());
            stmt.setString(3, funcionario.getEndereco());
            stmt.setString(4, funcionario.getNome());
            stmt.setDate(5, Date.valueOf(funcionario.getDataNascimento()));
            stmt.setString(6, funcionario.getFuncao());
            stmt.setInt(7, funcionario.getId());
            stmt.executeUpdate();
        }
    }

    // Delete
    public void excluirFuncionario(int id) throws SQLException {
        String sql = "DELETE FROM Funcionarios WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}

