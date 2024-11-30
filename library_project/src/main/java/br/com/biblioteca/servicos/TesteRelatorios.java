package br.com.biblioteca.servicos;

import java.sql.Connection;
import java.sql.DriverManager;

public class TesteRelatorios {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_data", "root", "9127");
            Relatorios relatorios = new Relatorios(connection);

            // Relatório de livros disponíveis
            relatorios.relatorioLivrosDisponiveis();

            // Relatório de empréstimos ativos
            relatorios.relatorioEmprestimosAtivos();

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
