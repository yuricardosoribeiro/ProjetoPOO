package Services;

import Database.Repository.MedicoRepository;
import Domain.Constants.EspecialidadeConstant;
import Domain.Entities.MedicoEntity;
import Domain.Exceptions.ValidationException;
import Domain.Utils.Console;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MedicoService {
    private MedicoRepository medicoRepository;
    private UtilsService utilsService;
    private Scanner scanner = new Scanner(System.in);

    public MedicoService() {
        this.medicoRepository = new MedicoRepository();
        this.utilsService = new UtilsService();
    }

    public void CadastrarMedico() {
        Console.EmitTitle("CADASTRO DE MÉDICO");
        MedicoEntity medico = new MedicoEntity();
        boolean isNotValid = true;

        while(isNotValid) {
            try {
                if(medico.getNome() == null) {
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    medico.setNome(nome);
                }

                if(medico.getCRM() == null) {
                    System.out.print("CRM: ");
                    String crm = scanner.nextLine();
                    medico.setCRM(crm);
                }

                if(medico.getEspecialidade() == null) {
                    medico.setEspecialidade(utilsService.SelecionarEspecialidade());
                }

                isNotValid = false;
            }
            catch(InputMismatchException exception) {
                Console.EmitError("O valor inserido é inválido!");
            }
            catch(ValidationException exception) {
                Console.EmitError(exception.getMessage());
            }
        }

        try {
            medicoRepository.Insert(medico);
            Console.EmitSuccess("Médico inserido com sucesso!");
        } catch(Exception exception) {
            Console.EmitError(exception.getMessage());
        }
    }

    public void MostrarMedicos() {
        Console.EmitTitle("MÉDICOS CADASTRADOS");

        List<MedicoEntity> lstMedicos = this.medicoRepository.GetAll();

        if(lstMedicos.size() == 0)
            System.out.println("Não há nenhum paciente cadastrado no sistema.");
        else
            lstMedicos.forEach(medico -> {
                System.out.println("Nome: " + medico.getNome());
                System.out.println("CRM: " + medico.getCRM());
                System.out.println("Especialidade: " + medico.getEspecialidade() + "\n");
            });
    }
}
