package br.com.biblioteca.servicos;



import java.sql.Connection;
import java.sql.SQLException;

import br.com.biblioteca.modelos.Cliente;
import dao.ClienteDAO;

public class GerenciadorCliente {
    private ClienteDAO clienteDAO;

    public GerenciadorCliente(Connection connection) {
        this.clienteDAO = new ClienteDAO(connection);
    }
    
    public void salvarFotoPerfil(String cpf, byte[] fotoPerfil) throws SQLException {
        clienteDAO.atualizarFotoPerfil(cpf, fotoPerfil);
    }

    public byte[] buscarFotoPerfil(String cpf) throws SQLException {
        return clienteDAO.buscarFotoPerfil(cpf);
    }

    public void cadastrarCliente(Cliente cliente) throws SQLException {
        // Validação de CPF
    	if (!ValidacaoCPF.validarCPF(cliente.getCpf())) {
            throw new IllegalArgumentException("CPF inválido.");
        }
    	
    	if (cliente.getCpf() == null || cliente.getCpf().trim().isEmpty()) {
    	    throw new IllegalArgumentException("Código de barras não pode ser vazio.");
    	}

        // Nome não pode ser vazio
        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }

        // Verificar se o CPF já existe
        Cliente existente = clienteDAO.buscarClientePorCpf(cliente.getCpf());
        if (existente != null) {
            throw new IllegalArgumentException("CPF já cadastrado.");
        }

        // Salvar cliente no banco
        clienteDAO.adicionarCliente(cliente);
    }
}

