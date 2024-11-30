package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import br.com.biblioteca.modelos.Emprestimo;

public class EmprestimoDAO {
    private Connection connection;

    public EmprestimoDAO(Connection connection) {
        this.connection = connection;
    }

    // Registrar um novo empréstimo
    public void registrarEmprestimo(Emprestimo emprestimo) throws SQLException {
        String sql = "INSERT INTO Emprestimos (cliente_id, livro_id, data_emprestimo, data_prevista, status) VALUES (?, ?, ?, ?, 'ativo')";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, emprestimo.getClienteId());
            stmt.setInt(2, emprestimo.getLivroId());
            stmt.setDate(3, Date.valueOf(emprestimo.getDataEmprestimo()));
            stmt.setDate(4, Date.valueOf(emprestimo.getDataPrevista()));
            stmt.executeUpdate();
        }
    }

    // Atualizar status do empréstimo (para devolução)
    public void concluirEmprestimo(int emprestimoId, String dataDevolucao) throws SQLException {
        String sql = "UPDATE Emprestimos SET data_devolucao = ?, status = 'concluido' WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(dataDevolucao));
            stmt.setInt(2, emprestimoId);
            stmt.executeUpdate();
        }
    }

    // Listar empréstimos ativos
    public List<Emprestimo> listarEmprestimosAtivos() throws SQLException {
        List<Emprestimo> emprestimos = new ArrayList<>();
        String sql = "SELECT * FROM Emprestimos WHERE status = 'ativo'";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Emprestimo emprestimo = new Emprestimo(
                    rs.getInt("id"),
                    rs.getInt("cliente_id"),
                    rs.getInt("livro_id"),
                    rs.getDate("data_emprestimo").toString(),
                    rs.getDate("data_devolucao") != null ? rs.getDate("data_devolucao").toString() : null,
                    rs.getDate("data_prevista").toString(),
                    rs.getString("status")
                );
                emprestimos.add(emprestimo);
            }
        }
        return emprestimos;
    }
    
 // Consultar histórico de empréstimos de um cliente
    public List<Emprestimo> consultarHistoricoEmprestimosPorCliente(int clienteId) throws SQLException {
        List<Emprestimo> emprestimos = new ArrayList<>();
        String sql = "SELECT * FROM Emprestimos WHERE cliente_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, clienteId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    emprestimos.add(new Emprestimo(
                        rs.getInt("id"),
                        rs.getInt("cliente_id"),
                        rs.getInt("livro_id"),
                        rs.getDate("data_emprestimo").toString(),
                        rs.getDate("data_devolucao") != null ? rs.getDate("data_devolucao").toString() : null,
                        rs.getDate("data_prevista").toString(),
                        rs.getString("status")
                    ));
                }
            }
        }
        return emprestimos;
    }
}

