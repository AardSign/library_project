package br.com.biblioteca.servicos;

import java.sql.Connection;
import java.sql.DriverManager;

import br.com.biblioteca.modelos.Funcionario;

public class TesteCadastroFuncionario {
    public static void main(String[] args) {
        try {
            // Conexão com o banco de dados
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_data", "root", "9127");
            GerenciadorFuncionario gerenciadorFuncionario = new GerenciadorFuncionario(connection);

            // Autenticar o usuário logado (Administrador)
            Funcionario usuarioLogado = gerenciadorFuncionario.autenticarFuncionario("00000000000", "senha123");
            if (usuarioLogado == null) {
                System.out.println("Erro: Usuário ou senha inválidos.");
                return;
            }

            // Criando o funcionário a ser cadastrado
            Funcionario novoFuncionario = new Funcionario(
                0,
                "40325166005",
                "999999999",
                "Rua Exemplo, 456",
                "Suzie Q",
                "1980-12-25",
                "Assistente",
                "funcionario",
                "senha456" // Senha do novo funcionário
            );

            // Cadastrando o funcionário
            try {
                gerenciadorFuncionario.cadastrarFuncionario(novoFuncionario, usuarioLogado);
                System.out.println("Funcionário cadastrado com sucesso!");
            } catch (IllegalArgumentException e) {
                System.out.println("Erro ao cadastrar funcionário válido: " + e.getMessage());
            }

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
