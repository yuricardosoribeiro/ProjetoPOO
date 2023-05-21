package Services;

import Database.Repository.PacienteRepository;
import Domain.Entities.EnderecoEntity;
import Domain.Entities.PacienteEntity;
import Domain.Exceptions.ValidationException;
import Domain.Utils.Console;
import Domain.Utils.Validators;
import org.bson.types.ObjectId;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class PacienteService {
    private PacienteRepository pacienteRepository;
    private Scanner scanner = new Scanner(System.in);

    public PacienteService() {
        this.pacienteRepository = new PacienteRepository();
    }

    public void CadastrarPaciente() {
        Console.EmitTitle("CADASTRO DE PACIENTE");

        PacienteEntity paciente = new PacienteEntity();
        EnderecoEntity endereco = new EnderecoEntity();
        boolean isNotValid = true;

        while(isNotValid) {
            try {
                if(paciente.getNome() == null) {
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    paciente.setNome(nome);
                }

                if(paciente.getCpf() == null) {
                    System.out.print("CPF: ");
                    String cpf = scanner.nextLine();
                    paciente.setCpf(cpf);
                }

                if(paciente.getDataNascimento() == null) {
                    System.out.print("Data de Nascimento (dd/mm/yyyy): ");
                    String dataNascimento = scanner.nextLine();
                    paciente.setDataNascimento(dataNascimento);
                }

                if(paciente.getTelefone() == null) {
                    System.out.print("Telefone: ");
                    String telefone = scanner.nextLine();
                    paciente.setTelefone(telefone);
                }

                if(endereco.getCEP() == null) {
                    System.out.print("CEP: ");
                    String cep = scanner.nextLine();
                    endereco.setCEP(cep);
                }

                if(endereco.getLogradouro() == null) {
                    System.out.print("Logradouro: ");
                    String logradouro = scanner.nextLine();
                    endereco.setLogradouro(logradouro);
                }

                if(endereco.getBairro() == null) {
                    System.out.print("Bairro: ");
                    String bairro = scanner.nextLine();
                    endereco.setBairro(bairro);
                }

                if(endereco.getCidade() == null) {
                    System.out.print("Cidade: ");
                    String cidade = scanner.nextLine();
                    endereco.setCidade(cidade);
                }

                if(endereco.getNumero() == 0) {
                    System.out.print("Número: ");
                    int numero = scanner.nextInt();
                    scanner.nextLine();
                    endereco.setNumero(numero);
                }

                System.out.print("Complemento: ");
                String complemento = scanner.nextLine();
                endereco.setComplemento(complemento);

                isNotValid = false;
            }
            catch(InputMismatchException exception) {
                Console.EmitError("- O valor inserido é inválido!");
                scanner.nextLine();
            }
            catch(ValidationException exception) {
                Console.EmitError(exception.getMessage());
            }
        }

        try {
            paciente.setEndereco(endereco);
            pacienteRepository.Insert(paciente);
            Console.EmitSuccess("Paciente inserido com sucesso!");
        } catch(Exception exception) {
            Console.EmitError(exception.getMessage());
        }
    }

    public void MostrarPacientes() {
        Console.EmitTitle("PACIENTES CADASTRADOS");

        List<PacienteEntity> pacientes = this.pacienteRepository.GetAll();

        pacientes.forEach(paciente -> {
            System.out.println("Nome: " + paciente.getNome());
            System.out.println("CPF: " + paciente.getCpf());
            System.out.println("Telefone: " + paciente.getTelefone());
            System.out.println("Data de Nascimento: " + paciente.getStringDataNascimento());
            System.out.println("CEP: " + paciente.getEndereco().getCEP());
            System.out.println("Logradouro: " + paciente.getEndereco().getLogradouro());
            System.out.println("Bairro: " + paciente.getEndereco().getBairro());
            System.out.println("Cidade: " + paciente.getEndereco().getCidade());
            System.out.println("Número: " + paciente.getEndereco().getNumero());
            System.out.println("Complemento: " + paciente.getEndereco().getComplemento());
            System.out.println("----------");
        });
    }

    public void DeletarPaciente() {
        Console.EmitTitle("DELETAR PACIENTE");

        List<PacienteEntity> pacientes = this.pacienteRepository.GetAll();

        for(int i = 0; i < pacientes.size(); i++) {
            System.out.println(i + " - " + pacientes.get(i).getNome());
        }

        boolean condition = true;
        ObjectId id = new ObjectId();

        while(condition) {
            try {
                System.out.print("Número do paciente que você deseja deletar: ");
                int index = scanner.nextInt();

                if(index > pacientes.size())
                    Console.EmitError("O paciente com esse número não existe!");

                id = pacientes.get(index).getId();
                condition = false;
            }
            catch(InputMismatchException exception) {
                Console.EmitError("Valor inválido!");
            }
        }

        try {
            pacienteRepository.Delete(id);
            Console.EmitSuccess("Paciente deletado com sucesso!");
        }
        catch (Exception exception) {
            Console.EmitError(exception.getMessage());
        }
    }
}
