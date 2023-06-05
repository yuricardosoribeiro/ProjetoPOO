import Database.Repository.MedicoRepository;
import Database.Repository.PacienteRepository;
import Domain.Entities.MedicoEntity;
import Domain.Entities.PacienteEntity;
import Domain.Utils.Console;
import Domain.Utils.Validators;
import Services.AreaMedicoService;
import Services.ConvenioService;
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
            System.out.println("1 - Gerenciamento de Pacientes");
            System.out.println("2 - Gerenciamento de Médicos");
            System.out.println("3 - Gerenciamento de Convênios");
            System.out.println("4 - Área do Médico");
            System.out.print("Sua escolha: ");

            try {
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        GerenciamentoPacientes();
                        break;
                    case 2:
                        GerenciamentoMedicos();
                        break;
                    case 3:
                        GerenciamentoConvenios();
                        break;
                    case 4:
                        AreaMedico();
                        break;
                }
            }
            catch(InputMismatchException exception) {
                Console.EmitError("Por favor, digite uma opção válida!");
                choice = -1;
                scanner.nextLine();
            }
        }
    }

    public static void GerenciamentoPacientes() {
        int choice = -1;
        boolean condition = true;
        PacienteService pacienteService = new PacienteService();

        while(condition) {
            Console.EmitTitle("GERENCIAMENTO DE PACIENTES ");
            System.out.println("1 - Cadastrar paciente");
            System.out.println("2 - Mostrar pacientes cadastrados");
            System.out.println("3 - Deletar paciente");
            System.out.println("4 - Atualizar dados de um paciente");
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
                    case 4:
                        pacienteService.AtualizarPaciente();
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

    public static void GerenciamentoMedicos() {
        int choice = -1;
        boolean condition = true;
        MedicoService medicoService = new MedicoService();

        while(condition) {
            Console.EmitTitle("GERENCIAMENTO DE MÉDICOS");
            System.out.println("1 - Cadastro de médico");
            System.out.println("2 - Mostrar médicos cadastrados");
            System.out.println("0 - Voltar a tela inicial");
            System.out.print("Sua escolha: ");

            try {
                choice = scanner.nextInt();

                switch (choice) {
                    case 0:
                        condition = false;
                        break;
                    case 1:
                        medicoService.CadastrarMedico();
                        break;
                    case 2:
                        medicoService.MostrarMedicos();
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

    public static void GerenciamentoConvenios() {
        int choice = -1;
        boolean condition = true;
        ConvenioService convenioServiceService = new ConvenioService();

        while(condition) {
            Console.EmitTitle("GERENCIAMENTO DE CONVÊNIOS");
            System.out.println("1 - Cadastro de médico");
            System.out.println("0 - Voltar a tela inicial");
            System.out.print("Sua escolha: ");

            try {
                choice = scanner.nextInt();

                switch (choice) {
                    case 0:
                        condition = false;
                        break;
                    case 1:
                        break;
                    case 2:
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

    public static void AreaMedico() {
        Console.EmitTitle("ÁREA DE MÉDICOS");

        MedicoRepository medicoRepository = new MedicoRepository();
        List<MedicoEntity> medicos = medicoRepository.GetAll();

        for(int i = 0; i < medicos.size(); i++) {
            System.out.println(i + " - " + medicos.get(i).getNome());
        }

        int choice = -1;
        boolean condition = true;
        MedicoEntity medico = new MedicoEntity();

        while(condition) {
            try {
                System.out.print("Qual médico você é? ");
                int id = scanner.nextInt();

                if(id > medicos.size() || id < 0)
                    Console.EmitError("Esse médico não existe!");
                else {
                    medico = medicos.get(id);
                    condition = false;
                }
            }
            catch(InputMismatchException exception) {
                Console.EmitError("Por favor, digite uma opção válida!");
                scanner.nextLine();
            }
        }

        AreaMedicoService areaMedicoService = new AreaMedicoService(medico);
        choice = -1;
        condition = true;

        while(condition) {
            Console.EmitTitle("OLÁ, " + medico.getNome().toUpperCase());
            System.out.println("1 - Ver a agenda de hoje");
            System.out.println("0 - Voltar a tela inicial");
            System.out.print("Sua escolha: ");

            try {
                choice = scanner.nextInt();

                switch (choice) {
                    case 0:
                        condition = false;
                        break;
                    case 1:
                        areaMedicoService.MostrarAgendaDoDia();
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