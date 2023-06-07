package Services;

import Database.Repository.ConsultaRepository;
import Database.Repository.PacienteRepository;
import Domain.Entities.ConsultaEntity;
import Domain.Entities.MedicoEntity;
import Domain.Entities.RegistroConsultaEntity;
import Domain.Utils.Console;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AreaMedicoService {
    private ConsultaRepository consultaRepository;
    private PacienteRepository pacienteRepository;
    private Scanner scanner;
    private MedicoEntity medico;

    public AreaMedicoService(MedicoEntity medico) {
        this.scanner = new Scanner(System.in);
        this.consultaRepository = new ConsultaRepository();
        this.medico = medico;
        this.pacienteRepository = new PacienteRepository();
    }

    public void InserirRegistros() {
        ConsultaEntity consulta = this.consultaRepository.GetConsultaOcorrendoAgora(this.medico.getId());
        Console.EmitTitle("INSERIR REGISTROS NA CONSULTA");

        if(consulta == null) {
            Console.EmitError("Não tem nenhuma consulta ocorrendo agora!");
            return;
        }

        System.out.println("Nome: " + consulta.getPaciente().getNome());
        System.out.println("Idade: " + consulta.getPaciente().getIdade());

        boolean condition = true;
        RegistroConsultaEntity registros = new RegistroConsultaEntity();

        while(condition) {
            try {
                if(registros.getBatimentosCardiacos() == 0) {
                    System.out.print("Batimentos cardíacos: ");
                    int batimentosCardiacos = scanner.nextInt();
                    registros.setBatimentosCardiacos(batimentosCardiacos);
                    scanner.nextLine();
                }

                if(registros.getPressao() == null) {
                    System.out.print("Pressão: ");
                    String pressao = scanner.nextLine();
                    registros.setPressao(pressao);
                }

                if(registros.getTemperatura() == 0) {
                    System.out.print("Temperatura: ");
                    double temperatura = scanner.nextDouble();
                    registros.setTemperatura(temperatura);
                    scanner.nextLine();
                }

                if(registros.getDiagnostico() == null) {
                    System.out.println("Diagnóstico: ");
                    String diagnostico = scanner.nextLine();
                    registros.setDiagnostico(diagnostico);
                }

                if(registros.getObservacoes() == null) {
                    System.out.println("Observações: ");
                    String observacoes = scanner.nextLine();
                    registros.setObservacoes(observacoes);
                }

                condition = false;
            }
            catch(InputMismatchException exception) {
                Console.EmitError("Valor inválido!");
            }
        }

        consulta.setRegistros(registros);
        consulta.setConsultaAconteceu(true);

        System.out.println(consulta.isConsultaAconteceu());

        try {
            this.consultaRepository.Update(consulta);
        }
        catch(Exception exception) {
            Console.EmitError("Não foi possível salvar! " + exception.getMessage());
        }
    }

    public void MostrarAgendaDoDia() {
        List<ConsultaEntity> consultas = this.consultaRepository.GetAllConsultasMedicoDia(medico.getId());

        LocalDate dataAtual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = dataAtual.format(formatter);

        Console.EmitTitle("AGENDA DO DIA " + dataFormatada);

        if(consultas.size() == 0)
            System.out.println("Não há consultas agendadas para hoje!");
        else
            for (ConsultaEntity consulta: consultas)
                System.out.println(consulta.getData().getHours() + "h - Consulta com o paciente " + consulta.getPaciente().getNome());
    }

    public void MostrarAgendaDaSemana() {
        List<ConsultaEntity> consultas = this.consultaRepository.GetAllConsultasMedicoSemana(medico.getId());

        Console.EmitTitle("AGENDA DA SEMANA");

        if(consultas.size() == 0) {
            System.out.println("Não há consultas agendadas para esta semana!");
            return;
        }

        List<ConsultaEntity> consultasSegunda = new ArrayList<>();
        List<ConsultaEntity> consultasTerca = new ArrayList<>();
        List<ConsultaEntity> consultasQuarta = new ArrayList<>();
        List<ConsultaEntity> consultasQuinta = new ArrayList<>();
        List<ConsultaEntity> consultasSexta = new ArrayList<>();

        for(ConsultaEntity consulta : consultas) {
            LocalDate dataConsulta = consulta.getData().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if(dataConsulta.getDayOfWeek().equals(DayOfWeek.MONDAY))
                consultasSegunda.add(consulta);
            else if(dataConsulta.getDayOfWeek().equals(DayOfWeek.TUESDAY))
                consultasTerca.add(consulta);
            else if(dataConsulta.getDayOfWeek().equals(DayOfWeek.WEDNESDAY))
                consultasQuarta.add(consulta);
            else if(dataConsulta.getDayOfWeek().equals(DayOfWeek.THURSDAY))
                consultasQuinta.add(consulta);
            else if(dataConsulta.getDayOfWeek().equals(DayOfWeek.FRIDAY))
                consultasSexta.add(consulta);
        }

        Console.EmitSuccess("Consultas de Segunda-Feira");
        if(consultasSegunda.size() == 0)
            System.out.println("Não há consultas para esse dia!");
        else
            for (ConsultaEntity consulta: consultasSegunda)
                System.out.println(consulta.getData().getHours() + "h - Consulta com o paciente " + consulta.getPaciente().getNome());

        Console.EmitSuccess("Consultas de Terça-Feira");
        if(consultasTerca.size() == 0)
            System.out.println("Não há consultas para esse dia!");
        else
            for (ConsultaEntity consulta: consultasTerca)
                System.out.println(consulta.getData().getHours() + "h - Consulta com o paciente " + consulta.getPaciente().getNome());

        Console.EmitSuccess("Consultas de Quarta-Feira");
        if(consultasQuarta.size() == 0)
            System.out.println("Não há consultas para esse dia!");
        else
            for (ConsultaEntity consulta: consultasQuarta)
                System.out.println(consulta.getData().getHours() + "h - Consulta com o paciente " + consulta.getPaciente().getNome());

        Console.EmitSuccess("Consultas de Quinta-Feira");
        if(consultasQuinta.size() == 0)
            System.out.println("Não há consultas para esse dia!");
        else
            for (ConsultaEntity consulta: consultasQuinta)
                System.out.println(consulta.getData().getHours() + "h - Consulta com o paciente " + consulta.getPaciente().getNome());

        Console.EmitSuccess("Consultas de Sexta-Feira");
        if(consultasSexta.size() == 0)
            System.out.println("Não há consultas para esse dia!");
        else
            for (ConsultaEntity consulta: consultasSexta)
                System.out.println(consulta.getData().getHours() + "h - Consulta com o paciente " + consulta.getPaciente().getNome());

    }

    public void GerarRelatorio() {
        Console.EmitTitle("RELATÓRIO DE CONSULTAS");

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

        List<ConsultaEntity> consultas = this.consultaRepository.GetConsultaByMedicoAndData(this.medico.getId(), dataInicio, dataFinal);

        for(ConsultaEntity consulta: consultas) {
            System.out.println("Nome do paciente: " + consulta.getPaciente().getNome());
            System.out.println("Data: " + dateHourFormat.format(consulta.getData()));
        }
    }
}
