package Services;

import Database.Repository.ConsultaRepository;
import Database.Repository.MedicoRepository;
import Database.Repository.PacienteRepository;
import Domain.Entities.ConsultaEntity;
import Domain.Entities.ConvenioEntity;
import Domain.Entities.MedicoEntity;
import Domain.Entities.PacienteEntity;
import Domain.Exceptions.ValidationException;
import Domain.Utils.Console;
import org.bson.types.ObjectId;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AreaPacienteService {
    private ConsultaRepository consultaRepository;
    private PacienteRepository pacienteRepository;
    private MedicoRepository medicoRepository;

    private Scanner scanner;

    private PacienteEntity paciente;

    private UtilsService utilsService;


    public AreaPacienteService(PacienteEntity paciente) {
        this.scanner = new Scanner(System.in);
        this.consultaRepository = new ConsultaRepository();
        this.paciente = paciente;
        this.pacienteRepository = new PacienteRepository();
        this.medicoRepository = new MedicoRepository();
        this.utilsService = new UtilsService();
    }

    public void AgendarConsulta() {
        Console.EmitTitle("AGENDAMENTO DE CONSULTAS");

        boolean condition = true;
        List<MedicoEntity> lstMedicos = new ArrayList<>();

        while(condition) {
            String especialidade = this.utilsService.SelecionarEspecialidade();
            lstMedicos = this.medicoRepository.GetMedicosByEspecialidade(especialidade);

            if(lstMedicos.size() == 0)
                Console.EmitError("Não temos nenhum médico com essa especialidade!");
            else
                condition = false;
        }

        MedicoEntity medico = this.SelecionarMedico(lstMedicos);
        ConsultaEntity consulta = new ConsultaEntity();
        condition = true;

        while(condition) {
            try {
                System.out.print("Que dia você gostaria de agendar sua consulta? (dd/mm/yyyy): ");
                String dataNascimento = scanner.nextLine();

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date dataConsulta = dateFormat.parse(dataNascimento);

                if(dataConsulta.before(new Date()))
                    throw new ValidationException("A data não pode ser menor que a data atual!");

                Date dataFinalConsulta = this.SelecionarHorario(dataConsulta, medico.getId());

                if(dataFinalConsulta != null) {
                    consulta.setPaciente(this.paciente);
                    consulta.setMedico(medico);
                    consulta.setData(dataFinalConsulta);

                    condition = false;
                }
            }
            catch(ParseException exception) {
                Console.EmitError("Formato incorreto de data!");
            }
            catch(InputMismatchException exception) {
                Console.EmitError("O valor inserido é inválido!");
            }
            catch(ValidationException exception) {
                Console.EmitError(exception.getMessage());
            }
        }

        this.consultaRepository.Insert(consulta);
        Console.EmitSuccess("Consulta agendada com sucesso!");
    }

    public void MostrarConsultasAgendadas() {
        Console.EmitTitle("CONSULTAS AGENDADAS");
        List<ConsultaEntity> consultas = this.consultaRepository.GetAllConsultasPaciente(this.paciente.getId());
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        for(ConsultaEntity consulta : consultas) {
            String dataFormatada = format.format(consulta.getData());
            System.out.println(String.format("%d:00 até %d:00 (%s) - Médico: %s",
                    consulta.getData().getHours(), consulta.getData().getHours() + 1,
                    dataFormatada, consulta.getMedico().getNome()));
        }
    }

    private Date SelecionarHorario(Date data, ObjectId medicoId) {
        List<ConsultaEntity> consultas = this.consultaRepository.GetByDiaMedico(medicoId, data);
        List<Integer> horariosPreenchidos = consultas.stream().map(consulta -> consulta.getData().getHours()).toList();
        List<Integer> lstHorarios = new ArrayList<>(Arrays.asList(8, 9, 10, 13, 14, 15, 16, 17))
                .stream().filter(numero -> !horariosPreenchidos.contains(numero)).toList();

        if(lstHorarios.size() == 0) {
            Console.EmitError("Esse médico não tem horários disponíveis nesse dia!");
            return null;
        }

        for(int i = 0; i < lstHorarios.size(); i++) {
            System.out.println(String.format("%d - %d:00 até %d:00", i+1, lstHorarios.get(i), lstHorarios.get(i) + 1));
        }

        boolean condition = true;
        int horarioEscolhido = 0;

        while(condition) {
            try {
                System.out.print("Qual horário você gostaria de agendar a consulta? ");
                int choice = scanner.nextInt();

                if(choice > lstHorarios.size() || choice < 0)
                    Console.EmitError("Esse horário não existe!");
                else {
                    horarioEscolhido = lstHorarios.get(choice - 1);
                    condition = false;
                }
            }
            catch(InputMismatchException exception) {
                Console.EmitError("O valor inserido é inválido!");
            }
        }

        data.setHours(horarioEscolhido);

        return data;
    }

    private MedicoEntity SelecionarMedico(List<MedicoEntity> lstMedicos) {
        for(int i = 0; i < lstMedicos.size(); i++) {
            ConvenioEntity convenio = lstMedicos.get(i).getConvenio();
            String hasConvenio = "";
            if(convenio != null && this.paciente.getConvenio() != null && convenio.getId().equals(paciente.getConvenio().getId()))
                hasConvenio = " (Vinculado ao Convênio " + paciente.getConvenio().getNome() + ")";

            System.out.println((i+1) + " - " + lstMedicos.get(i).getNome() + hasConvenio);
        }

        boolean condition = true;
        MedicoEntity medicoEscolhido = new MedicoEntity();

        while(condition) {
            try {
                System.out.print("Médico: ");
                int choice = scanner.nextInt();

                if(choice > lstMedicos.size() + 1)
                    Console.EmitError("Esse médico não existe!");
                else {
                    medicoEscolhido = lstMedicos.get(choice - 1);
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

        return medicoEscolhido;
    }
}
