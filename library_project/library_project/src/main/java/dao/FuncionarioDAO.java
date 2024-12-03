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
        String sql = "INSERT INTO Funcionarios (CPF, telefone, endereco, nome, data_nascimento, funcao, tipo, senha, foto_perfil) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, funcionario.getCpf());
            stmt.setString(2, funcionario.getTelefone());
            stmt.setString(3, funcionario.getEndereco());
            stmt.setString(4, funcionario.getNome());
            stmt.setDate(5, Date.valueOf(funcionario.getDataNascimento()));
            stmt.setString(6, funcionario.getFuncao());
            stmt.setString(7, funcionario.getTipo());
            stmt.setString(8, funcionario.getSenha());
            stmt.setBytes(9, funcionario.getFotoPerfil()); // Define os bytes da foto
            stmt.executeUpdate();
        }
    }
    
    public boolean existeAdministrador() throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM Funcionarios WHERE tipo = 'administrador'";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total") > 0;
                }
            }
        }
        return false;
    }


    public Funcionario autenticarFuncionario(String cpf, String senha) throws SQLException {
        String sql = "SELECT * FROM Funcionarios WHERE CPF = ? AND senha = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            stmt.setString(2, senha);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Funcionario(
                        rs.getInt("id"),
                        rs.getString("CPF"),
                        rs.getString("telefone"),
                        rs.getString("endereco"),
                        rs.getString("nome"),
                        rs.getDate("data_nascimento").toString(),
                        rs.getString("funcao"),
                        rs.getString("tipo"),
                        rs.getString("senha"),
                        rs.getBytes("foto_perfil")
                        
                    );
                }
            }
        }
        return null; // Usuário ou senha inválidos
    }
    
    public void atualizarFotoPerfil(String cpf, byte[] fotoPerfil) throws SQLException {
        String sql = "UPDATE Funcionarios SET foto_perfil = ? WHERE cpf = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setBytes(1, fotoPerfil);
            stmt.setString(2, cpf);
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
                    rs.getString("funcao"),
                    rs.getString("tipo"),
                    rs.getString("senha"),
                    rs.getBytes("foto_perfil")
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
                        rs.getString("funcao"),
                        rs.getString("tipo"),
                        rs.getString("senha"),
                        rs.getBytes("foto_perfil")
                    );
                }
            }
        }
        return null;
    }
    
    public byte[] buscarFotoPerfil(String cpf) throws SQLException {
        String sql = "SELECT foto_perfil FROM Funcionarios WHERE cpf = ?";
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
            stmt.setString(8, funcionario.getSenha());
            stmt.setBytes(9, funcionario.getFotoPerfil());
            stmt.executeUpdate();
        }
    }

    // Buscar funcionário por CPF
    public Funcionario buscarFuncionarioPorCpf(String cpf) throws SQLException {
        String sql = "SELECT * FROM Funcionarios WHERE CPF = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Funcionario(
                        rs.getInt("id"),
                        rs.getString("CPF"),
                        rs.getString("telefone"),
                        rs.getString("endereco"),
                        rs.getString("nome"),
                        rs.getDate("data_nascimento").toString(),
                        rs.getString("funcao"),
                        rs.getString("tipo"), // Adicionando o campo 'tipo'
                        rs.getString("senha"),
                        rs.getBytes("foto_perfil")
                    );
                }
            }
        }
        return null;
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

