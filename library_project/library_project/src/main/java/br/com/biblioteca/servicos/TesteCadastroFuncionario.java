package br.com.biblioteca.servicos;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

            // Leitura do arquivo de foto
            Path caminhoFoto = Paths.get("C:\\Users\\Emanu\\OneDrive\\Desktop\\fb272ae9cfd037331dca2230ea551cfe.jpg"); // Substitua pelo caminho real da foto
            byte[] fotoBytes = Files.readAllBytes(caminhoFoto);
            System.out.println("Bytes carregados: " + fotoBytes.length);
            
            
            // Criando o funcionário a ser cadastrado
            Funcionario novoFuncionario = new Funcionario(
                0,
                "99443875098",
                "999999999",
                "Rua Exemplo, 456",
                "Oingo",
                "1980-12-25",
                "Assistente",
                "funcionario",
                "912736", // Senha do novo funcionário
                fotoBytes   // Foto de perfil
            );
            System.out.println("Foto atribuída ao funcionário: " + novoFuncionario.getFotoPerfil().length);

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
