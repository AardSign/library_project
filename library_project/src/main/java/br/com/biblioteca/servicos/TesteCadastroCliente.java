package br.com.biblioteca.servicos;



import java.sql.Connection;
import java.sql.DriverManager;

import br.com.biblioteca.modelos.Cliente;

public class TesteCadastroCliente {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_data", "root", "9127");
            GerenciadorCliente clienteServico = new GerenciadorCliente(connection);

            // Criando um cliente
            Cliente cliente = new Cliente(0, "34151872094", "999999999", "Rua Exemplo, 123", "Yukako Yamagishi", "2000-05-10", "Ficção");

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

