import Database.Repository.PacienteRepository;
import Domain.Entities.MedicoEntity;
import Domain.Entities.PacienteEntity;
import Domain.Utils.Console;
import Domain.Utils.Validators;
import Services.MedicoService;
import Services.PacienteService;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws InterruptedException {

        int choice = -1;
        boolean condition = true;

        while(condition) {
            Console.EmitTitle("GESTÃO DE CLÍNICA MÉDICA");
            System.out.println("1 - Cadastros e relatórios");
            System.out.println("2 - Área do Médico");
            System.out.println("3 - Área de Consultas");
            System.out.print("Sua escolha: ");

            try {
                choice = scanner.nextInt();

                switch (choice) {
                    case 1: {
                        CadastrosRelatorios();
                        break;
                    }
                }
            }
            catch(InputMismatchException exception) {
                Console.EmitError("Por favor, digite uma opção válida!");
                choice = -1;
                scanner.nextLine();
            }
        }
    }

    public static void CadastrosRelatorios() {
        int choice = -1;
        boolean condition = true;
        MedicoService medicoService = new MedicoService();
        PacienteService pacienteService = new PacienteService();

        while(condition) {
            Console.EmitTitle("CADASTROS E RELATÓRIOS");
            System.out.println("1 - Cadastrar paciente");
            System.out.println("2 - Mostrar pacientes cadastrados");
            System.out.println("3 - Deletar paciente");
            System.out.println("0 - Voltar a tela inicial");
            System.out.print("Sua escolha: ");

            try {
                choice = scanner.nextInt();

                switch (choice) {
                    case 0:
                        condition = false;
                        break;
                    case 1:
                        pacienteService.CadastrarPaciente();
                        break;
                    case 2:
                        pacienteService.MostrarPacientes();
                        break;
                    case 3:
                        pacienteService.DeletarPaciente();
                        break;
                }
            }
            catch(InputMismatchException exception) {
                Console.EmitError("Por favor, digite uma opção válida!");
                scanner.nextLine();
            }
            finally {
                choice = -1;
            }
        }
    }
}