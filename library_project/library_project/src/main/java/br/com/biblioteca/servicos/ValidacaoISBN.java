package br.com.biblioteca.servicos;

public class ValidacaoISBN {

    public static boolean validarISBN13(String isbn) {
        // Remove qualquer caractere não numérico
        isbn = isbn.replaceAll("\\D", "");

        // Verifica se o ISBN tem 13 dígitos
        if (isbn.length() != 13) {
            return false;
        }

        // Cálculo do dígito verificador
        int soma = 0;
        for (int i = 0; i < 12; i++) {
            int digito = Character.getNumericValue(isbn.charAt(i));
            soma += (i % 2 == 0) ? digito : digito * 3;
        }

        int resto = soma % 10;
        int digitoVerificador = (resto == 0) ? 0 : 10 - resto;

        // Verifica o último dígito
        return digitoVerificador == Character.getNumericValue(isbn.charAt(12));
    }
}

