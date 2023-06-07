package Services;

import Database.Repository.ConsultaRepository;
import Database.Repository.ConvenioRepository;
import Database.Repository.PacienteRepository;
import Domain.Entities.ConsultaEntity;
import Domain.Entities.MedicoEntity;
import Domain.Entities.PacienteEntity;
import Domain.Utils.Console;
import org.bson.types.ObjectId;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

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

        if(consulta != null) {
            System.out.println(consulta.getId());
        }
        else {
            Console.EmitError("Não tem nenhuma consulta ocorrendo agora!");
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
}
