package br.com.biblioteca.servicos;

import java.sql.Connection;
import java.sql.SQLException;

import br.com.biblioteca.modelos.Funcionario;
import dao.FuncionarioDAO;

public class GerenciadorFuncionario {
    private FuncionarioDAO funcionarioDAO;

    public GerenciadorFuncionario(Connection connection) {
        this.funcionarioDAO = new FuncionarioDAO(connection);
    }
    
    public boolean permitirPrimeiroAdministrador() throws SQLException {
        // Verifica se existe algum administrador no banco
        return !funcionarioDAO.existeAdministrador();
    }
    
    public Funcionario login(String cpf, String senha) throws SQLException {
        Funcionario funcionario = funcionarioDAO.autenticarFuncionario(cpf, senha);
        if (funcionario == null) {
            throw new IllegalArgumentException("CPF ou senha inválidos.");
        }
        return funcionario;
    }
    
    public Funcionario autenticarFuncionario(String cpf, String senha) throws SQLException {
        return funcionarioDAO.autenticarFuncionario(cpf, senha);
    }
    
    public void salvarFotoPerfil(String cpf, byte[] fotoPerfil) throws SQLException {
        funcionarioDAO.atualizarFotoPerfil(cpf, fotoPerfil);
    }

    public byte[] buscarFotoPerfil(String cpf) throws SQLException {
        return funcionarioDAO.buscarFotoPerfil(cpf);
    }

    public void cadastrarFuncionario(Funcionario funcionario, Funcionario usuarioLogado) throws SQLException {
        // Verifica se existe ao menos um administrador no sistema
        boolean existeAdministrador = funcionarioDAO.existeAdministrador();

        // Permite o cadastro do primeiro administrador sem validação de permissões
        if (!existeAdministrador && "administrador".equals(funcionario.getTipo())) {
            funcionarioDAO.adicionarFuncionario(funcionario);
            return;
        }

        // Valida permissões para os demais cadastros
        if (usuarioLogado == null || !"administrador".equals(usuarioLogado.getTipo())) {
            throw new SecurityException("Acesso negado: Apenas administradores podem cadastrar funcionários.");
        }

        // Validação de CPF
        if (!ValidacaoCPF.validarCPF(funcionario.getCpf())) {
            throw new IllegalArgumentException("CPF de funcionário inválido.");
        }

        // Verifica se o CPF está vazio
        if (funcionario.getCpf() == null || funcionario.getCpf().trim().isEmpty()) {
            throw new IllegalArgumentException("CPF não pode ser vazio.");
        }

        // Verifica se o nome está vazio
        if (funcionario.getNome() == null || funcionario.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }

        // Verifica se a função está vazia
        if (funcionario.getFuncao() == null || funcionario.getFuncao().trim().isEmpty()) {
            throw new IllegalArgumentException("Função não pode ser vazia.");
        }

        // Verifica se o CPF já está cadastrado
        Funcionario existente = funcionarioDAO.buscarFuncionarioPorCpf(funcionario.getCpf());
        if (existente != null) {
            throw new IllegalArgumentException("CPF já cadastrado.");
        }

        // Salvar funcionário no banco
        funcionarioDAO.adicionarFuncionario(funcionario);
    }
}
