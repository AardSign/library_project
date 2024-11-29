package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import br.com.biblioteca.modelos.Cliente;

public class ClienteDAO {
    private Connection connection;

    // Construtor que inicializa a conexão com o banco
    public ClienteDAO(Connection connection) {
        this.connection = connection;
    }

    // Create
    public void adicionarCliente(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO Clientes (CPF, telefone, endereco, nome, data_nascimento, preferencias) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cliente.getCpf());
            stmt.setString(2, cliente.getTelefone());
            stmt.setString(3, cliente.getEndereco());
            stmt.setString(4, cliente.getNome());
            stmt.setDate(5, Date.valueOf(cliente.getDataNascimento()));
            stmt.setString(6, cliente.getPreferencias());
            stmt.executeUpdate();
        }
    }

    // Read (listar todos)
    public List<Cliente> listarClientes() throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM Clientes";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Cliente cliente = new Cliente(
                    rs.getInt("id"),
                    rs.getString("CPF"),
                    rs.getString("telefone"),
                    rs.getString("endereco"),
                    rs.getString("nome"),
                    rs.getDate("data_nascimento").toString(),
                    rs.getString("preferencias")
                );
                clientes.add(cliente);
            }
        }
        return clientes;
    }

    // Read (buscar por ID)
    public Cliente buscarClientePorId(int id) throws SQLException {
        String sql = "SELECT * FROM Clientes WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Cliente(
                        rs.getInt("id"),
                        rs.getString("CPF"),
                        rs.getString("telefone"),
                        rs.getString("endereco"),
                        rs.getString("nome"),
                        rs.getDate("data_nascimento").toString(),
                        rs.getString("preferencias")
                    );
                }
            }
        }
        return null;
    }

    // Update
    public void atualizarCliente(Cliente cliente) throws SQLException {
        String sql = "UPDATE Clientes SET CPF = ?, telefone = ?, endereco = ?, nome = ?, data_nascimento = ?, preferencias = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cliente.getCpf());
            stmt.setString(2, cliente.getTelefone());
            stmt.setString(3, cliente.getEndereco());
            stmt.setString(4, cliente.getNome());
            stmt.setDate(5, Date.valueOf(cliente.getDataNascimento()));
            stmt.setString(6, cliente.getPreferencias());
            stmt.setInt(7, cliente.getId());
            stmt.executeUpdate();
        }
    }

    // Delete
    public void excluirCliente(int id) throws SQLException {
        String sql = "DELETE FROM Clientes WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}

