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
        String sql = "INSERT INTO Clientes (CPF, telefone, endereco, nome, data_nascimento, preferencias, foto_perfil) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cliente.getCpf());
            stmt.setString(2, cliente.getTelefone());
            stmt.setString(3, cliente.getEndereco());
            stmt.setString(4, cliente.getNome());
            stmt.setDate(5, Date.valueOf(cliente.getDataNascimento()));
            stmt.setString(6, cliente.getPreferencias());
            stmt.setBytes(7, cliente.getFotoPerfil()); // Define os bytes da foto
            stmt.executeUpdate();
        }
    }
    
    public void atualizarFotoPerfil(String cpf, byte[] fotoPerfil) throws SQLException {
        String sql = "UPDATE Clientes SET foto_perfil = ? WHERE cpf = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setBytes(1, fotoPerfil);
            stmt.setString(2, cpf);
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
                    rs.getString("preferencias"),
                    rs.getBytes("foto_perfil")
                );
                clientes.add(cliente);
            }
        }
        return clientes;
    }
    
    public byte[] buscarFotoPerfil(String cpf) throws SQLException {
        String sql = "SELECT foto_perfil FROM Clientes WHERE cpf = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBytes("foto_perfil");
                }
            }
        }
        return null;
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
                        rs.getString("preferencias"),
                        rs.getBytes("foto_perfil")
                    );
                }
            }
        }
        return null;
    }
    
    // Buscar clientes por nome
    public List<Cliente> buscarClientesPorNome(String nome) throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM Clientes WHERE nome LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + nome + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    clientes.add(new Cliente(
                        rs.getInt("id"),
                        rs.getString("CPF"),
                        rs.getString("telefone"),
                        rs.getString("endereco"),
                        rs.getString("nome"),
                        rs.getDate("data_nascimento").toString(),
                        rs.getString("preferencias"),
                        rs.getBytes("foto_perfil")
                    ));
                }
            }
        }
        return clientes;
    }
    
    // Buscar cliente por CPF
    public Cliente buscarClientePorCpf(String cpf) throws SQLException {
        String sql = "SELECT * FROM Clientes WHERE CPF = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Cliente(
                        rs.getInt("id"),
                        rs.getString("CPF"),
                        rs.getString("telefone"),
                        rs.getString("endereco"),
                        rs.getString("nome"),
                        rs.getDate("data_nascimento").toString(),
                        rs.getString("preferencias"),
                        rs.getBytes("foto_perfil")
                    );
                }
            }
        }
        return null;
    }
    
    public int buscarClienteIdPorCpf(String cpf) throws SQLException {
        String sql = "SELECT id FROM Clientes WHERE cpf = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                } else {
                    throw new IllegalArgumentException("Cliente não encontrado com o CPF: " + cpf);
                }
            }
        }
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
            stmt.setBytes(8, cliente.getFotoPerfil());
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

