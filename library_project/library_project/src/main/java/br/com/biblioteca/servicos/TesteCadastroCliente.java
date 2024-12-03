package br.com.biblioteca.servicos;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;

import br.com.biblioteca.modelos.Cliente;

public class TesteCadastroCliente {
    public static void main(String[] args) {
        try {
            // Conexão com o banco de dados
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_data", "root", "9127");
            GerenciadorCliente clienteServico = new GerenciadorCliente(connection);

            // Leitura do arquivo de foto
            Path caminhoFoto = Paths.get("C:\\Users\\Emanu\\OneDrive\\Desktop\\ab67616d0000b273b379cff757b8e0ae072bc08f.jpg"); // Substitua pelo caminho real da sua foto
            byte[] fotoBytes = Files.readAllBytes(caminhoFoto);

            // Criando o cliente
            Cliente cliente = new Cliente(
                0, 
                "09483970040",          // CPF
                "999999999",            // Telefone
                "Rua Exemplo, 123",     // Endereço
                "Giorno Giovanna",     // Nome
                "2000-05-10",           // Data de nascimento
                "Ficção",               // Preferências
                fotoBytes               // Foto de perfil
            );

            // Tentando cadastrar
            try {
                clienteServico.cadastrarCliente(cliente);
                System.out.println("Cliente cadastrado com sucesso!");
            } catch (IllegalArgumentException e) {
                System.out.println("Erro ao cadastrar cliente: " + e.getMessage());
            }

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
