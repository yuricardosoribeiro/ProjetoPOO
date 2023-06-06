package Services;

import Domain.Constants.EspecialidadeConstant;
import Domain.Utils.Console;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class UtilsService {
    private Scanner scanner;

    public UtilsService() {
        this.scanner = new Scanner(System.in);
    }

    public String SelecionarEspecialidade() {
        List<String> lstEspecialidades = new EspecialidadeConstant().ObterTodasEspecialidades();

        for(int i = 0; i < lstEspecialidades.size(); i++) {
            System.out.println((i+1) + " - " + lstEspecialidades.get(i));
        }

        boolean condition = true;
        String especialidadeEscolhida = "";

        while(condition) {
            try {
                System.out.print("Especialidade: ");
                int choice = scanner.nextInt();

                if(choice > lstEspecialidades.size() + 1)
                    Console.EmitError("Essa especialidade não existe!");
                else {
                    especialidadeEscolhida = lstEspecialidades.get(choice - 1);
                    condition = false;
                }
            }
            catch (InputMismatchException exception) {
                Console.EmitError("Valor inválido!");
            }
            finally {
                scanner.nextLine();
            }
        }

        return especialidadeEscolhida;
    }

}
