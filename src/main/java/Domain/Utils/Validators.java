package Domain.Utils;

public class Validators {
    public static boolean ValidaSoTemNumero(String texto) {
        // Verifica se cada caractere da string é um número
        for (int i = 0; i < texto.length(); i++) {
            if (!Character.isDigit(texto.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean ValidarCPF(String cpf) {
        // Remove caracteres especiais
        cpf = cpf.replaceAll("[^0-9]", "");

        // Verifica se possui 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }

        // Verifica se todos os dígitos são iguais
        boolean todosDigitosIguais = cpf.matches("(\\d)\\1{10}");
        if (todosDigitosIguais) {
            return false;
        }

        // Calcula o primeiro dígito verificador
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }
        int primeiroDigitoVerificador = 11 - (soma % 11);
        if (primeiroDigitoVerificador >= 10) {
            primeiroDigitoVerificador = 0;
        }

        // Verifica o primeiro dígito verificador
        if (Character.getNumericValue(cpf.charAt(9)) != primeiroDigitoVerificador) {
            return false;
        }

        // Calcula o segundo dígito verificador
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }
        int segundoDigitoVerificador = 11 - (soma % 11);
        if (segundoDigitoVerificador >= 10) {
            segundoDigitoVerificador = 0;
        }

        // Verifica o segundo dígito verificador
        if (Character.getNumericValue(cpf.charAt(10)) != segundoDigitoVerificador) {
            return false;
        }

        return true;
    }
}
