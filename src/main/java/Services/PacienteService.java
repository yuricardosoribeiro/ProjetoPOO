package Services;

import Database.Repository.ConsultaRepository;
import Database.Repository.PacienteRepository;
import Domain.Entities.ConsultaEntity;
import Domain.Entities.EnderecoEntity;
import Domain.Entities.PacienteEntity;
import Domain.Exceptions.ValidationException;
import Domain.Utils.Console;
import org.bson.types.ObjectId;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class PacienteService {
    private PacienteRepository pacienteRepository;
    private ConvenioService convenioService;
    private ConsultaRepository consultaRepository;
    private Scanner scanner = new Scanner(System.in);

    public PacienteService() {
        this.pacienteRepository = new PacienteRepository();
        this.convenioService = new ConvenioService();
        this.consultaRepository = new ConsultaRepository();
    }

    public void CadastrarPaciente() {
        Console.EmitTitle("CADASTRO DE PACIENTE");

        PacienteEntity paciente = new PacienteEntity();
        EnderecoEntity endereco = new EnderecoEntity();
        boolean isNotValid = true;

        while(isNotValid) {
            try {
                if(paciente.getNome() == null) {
                    System.out.print("Nome* = ");
                    String nome = scanner.nextLine();
                    paciente.setNome(nome);
                }

                if(paciente.getCpf() == null) {
                    System.out.print("CPF* = ");
                    String cpf = scanner.nextLine();
                    paciente.setCpf(cpf);
                }

                if(paciente.getDataNascimento() == null) {
                    System.out.print("Data de Nascimento (dd/mm/yyyy)* = ");
                    String dataNascimento = scanner.nextLine();
                    paciente.setDataNascimento(dataNascimento);
                }

                if(paciente.getTelefone() == null) {
                    System.out.print("Telefone* = ");
                    String telefone = scanner.nextLine();
                    paciente.setTelefone(telefone);
                }

                if(endereco.getCEP() == null) {
                    System.out.print("CEP* = ");
                    String cep = scanner.nextLine();
                    endereco.setCEP(cep);
                }

                if(endereco.getLogradouro() == null) {
                    System.out.print("Logradouro* = ");
                    String logradouro = scanner.nextLine();
                    endereco.setLogradouro(logradouro);
                }

                if(endereco.getBairro() == null) {
                    System.out.print("Bairro* = ");
                    String bairro = scanner.nextLine();
                    endereco.setBairro(bairro);
                }

                if(endereco.getCidade() == null) {
                    System.out.print("Cidade* = ");
                    String cidade = scanner.nextLine();
                    endereco.setCidade(cidade);
                }

                if(endereco.getNumero() == 0) {
                    System.out.print("Número* = ");
                    int numero = scanner.nextInt();
                    scanner.nextLine();
                    endereco.setNumero(numero);
                }

                System.out.print("Complemento = ");
                String complemento = scanner.nextLine();
                endereco.setComplemento(complemento);

                System.out.print("Deseja vincular o paciente a um convênio? (S/N) = ");
                String escolha = scanner.nextLine();

                if(!escolha.equals("S") && !escolha.equals("N"))
                    throw new ValidationException("Por favor escolha uma das opções fornecidas!");
                else
                    paciente.setConvenio(escolha.equals("S") ? this.convenioService.VincularConvenio() : null);

                isNotValid = false;
            }
            catch(InputMismatchException exception) {
                Console.EmitError("O valor inserido é inválido!");
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

        if(pacientes.size() == 0)
            System.out.println("Não há nenhum paciente cadastrado no sistema.");
        else
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

                if(paciente.getConvenio().getNome() != null)
                    System.out.println("Convênio: " + paciente.getConvenio().getNome());

                System.out.println();
            });
    }

    public void DeletarPaciente() {
        Console.EmitTitle("DELETAR PACIENTE");

        List<PacienteEntity> pacientes = this.pacienteRepository.GetAll();

        if(pacientes.size() == 0) {
            System.out.println("Não há nenhum paciente cadastrado no sistema.");
            return;
        }

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

    public void AtualizarPaciente() {
        Console.EmitTitle("ATUALIZAR DADOS DE UM PACIENTE");

        PacienteEntity paciente = this.EscolherPaciente();
        if(paciente == null)
            return;

        PacienteEntity newPaciente = new PacienteEntity();
        newPaciente.setEndereco(new EnderecoEntity());

        System.out.println("Dados atuais do paciente: ");
        System.out.println("Nome: " + paciente.getNome());
        System.out.println("CPF: " + paciente.getCpf());
        System.out.println("Telefone: " + paciente.getTelefone());
        System.out.println("Data de Nascimento: " + paciente.getStringDataNascimento());
        System.out.println("CEP: " + paciente.getEndereco().getCEP());
        System.out.println("Logradouro: " + paciente.getEndereco().getLogradouro());
        System.out.println("Bairro: " + paciente.getEndereco().getBairro());
        System.out.println("Cidade: " + paciente.getEndereco().getCidade());
        System.out.println("Número: " + paciente.getEndereco().getNumero());
        System.out.println("Complemento: " + paciente.getEndereco().getComplemento() + "\n");

        boolean isNotValid = true;

        while(isNotValid) {
            try {
                if(newPaciente.getNome() == null) {
                    System.out.print("Nome* = ");
                    String nome = scanner.nextLine();
                    newPaciente.setNome(nome);
                }

                if(newPaciente.getCpf() == null) {
                    System.out.print("CPF* = ");
                    String cpf = scanner.nextLine();
                    newPaciente.setCpf(cpf);
                }

                if(newPaciente.getDataNascimento() == null) {
                    System.out.print("Data de Nascimento (dd/mm/yyyy)* = ");
                    String dataNascimento = scanner.nextLine();
                    newPaciente.setDataNascimento(dataNascimento);
                }

                if(newPaciente.getTelefone() == null) {
                    System.out.print("Telefone* = ");
                    String telefone = scanner.nextLine();
                    newPaciente.setTelefone(telefone);
                }

                if(newPaciente.getEndereco().getCEP() == null) {
                    System.out.print("CEP* = ");
                    String cep = scanner.nextLine();
                    newPaciente.getEndereco().setCEP(cep);
                }

                if(newPaciente.getEndereco().getLogradouro() == null) {
                    System.out.print("Logradouro* = ");
                    String logradouro = scanner.nextLine();
                    newPaciente.getEndereco().setLogradouro(logradouro);
                }

                if(newPaciente.getEndereco().getBairro() == null) {
                    System.out.print("Bairro* = ");
                    String bairro = scanner.nextLine();
                    newPaciente.getEndereco().setBairro(bairro);
                }

                if(newPaciente.getEndereco().getCidade() == null) {
                    System.out.print("Cidade* = ");
                    String cidade = scanner.nextLine();
                    newPaciente.getEndereco().setCidade(cidade);
                }

                if(newPaciente.getEndereco().getNumero() == 0) {
                    System.out.print("Número* = ");
                    int numero = scanner.nextInt();
                    scanner.nextLine();
                    newPaciente.getEndereco().setNumero(numero);
                }

                System.out.print("Complemento: ");
                String complemento = scanner.nextLine();
                newPaciente.getEndereco().setComplemento(complemento);

                isNotValid = false;
            }
            catch(InputMismatchException exception) {
                Console.EmitError("O valor inserido é inválido!");
                scanner.nextLine();
            }
            catch(ValidationException exception) {
                Console.EmitError(exception.getMessage());
            }
        }

        newPaciente.setId(paciente.getId());

        try {
            pacienteRepository.Update(newPaciente);
            Console.EmitSuccess("Paciente atualizado com sucesso!");
        }
        catch (Exception exception) {
            Console.EmitError(exception.getMessage());
        }
    }

    public void HistoricoPaciente() {
        Console.EmitTitle("GERAR HISTÓRICO DE UM PACIENTE");

        PacienteEntity paciente = this.EscolherPaciente();
        if(paciente == null)
            return;

        boolean condition = true;
        Date dataInicio = null;
        Date dataFinal = null;
        Date dataComparacao = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat dateHourFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        while(condition) {
            try {
                if (dataInicio == null) {
                    System.out.print("Data inicial (dd/mm/yyyy): ");
                    String dtInicio = scanner.nextLine();
                    dataInicio = dateFormat.parse(dtInicio);
                    dataInicio.setHours(1);
                }

                if (dataFinal == null) {
                    System.out.print("Data final (dd/mm/yyyy): ");
                    String dtFinal = scanner.nextLine();
                    dataFinal = dateFormat.parse(dtFinal);
                    dataFinal.setHours(23);
                }

                condition = false;
            } catch (InputMismatchException exception) {
                Console.EmitError("Valor inválido!");
            } catch (ParseException exception) {
                Console.EmitError("Formato incorreto de data!");
            }
        }

        List<ConsultaEntity> consultas = this.consultaRepository.GetConsultaByPacienteAndData(paciente.getId(), dataInicio, dataFinal);

        System.out.println("\nNome completo: " + paciente.getNome());
        System.out.println("Idade: " + paciente.getIdade());
        System.out.println("-- Histórico de diagnósticos: ");

        for(ConsultaEntity consulta : consultas) {
            System.out.println("(" + dateHourFormat.format(consulta.getData()) + ") - " + consulta.getRegistros().getDiagnostico());
        }
    }

    private PacienteEntity EscolherPaciente() {
        List<PacienteEntity> pacientes = this.pacienteRepository.GetAll();

        if(pacientes.size() == 0) {
            System.out.println("Não há nenhum paciente cadastrado no sistema.");
            return null;
        }

        boolean condition = true;

        for(int i = 0; i < pacientes.size(); i++) {
            System.out.println(i + 1 + " - " + pacientes.get(i).getNome());
        }

        int index = 0;

        while(condition) {
            try {
                System.out.print("Número do paciente: ");
                index = scanner.nextInt() - 1;

                if(index > pacientes.size())
                    Console.EmitError("O paciente com esse número não existe!");

                scanner.nextLine();
                condition = false;
            }
            catch(InputMismatchException exception) {
                Console.EmitError("Valor inválido!");
            }
        }

        return pacientes.get(index);
    }
}
