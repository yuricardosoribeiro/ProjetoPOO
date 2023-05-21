package Services;

import Database.Repository.MedicoRepository;
import Domain.Entities.MedicoEntity;
import Domain.Exceptions.ValidationException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MedicoService {
    private MedicoRepository medicoRepository;
    private Scanner scanner = new Scanner(System.in);

    public MedicoService() {
        this.medicoRepository = new MedicoRepository();
    }

    public void CadastrarMedico() {
        System.out.println("========== CADASTRO DE MÉDICO ==========");
        MedicoEntity medico = new MedicoEntity();
        boolean isNotValid = true;

        while(isNotValid) {
            try {
                if(medico.getNome() == null) {
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();

                    String[] nomeArray = nome.split(" ");

                    if(nomeArray.length > 1)
                        medico.setNome(nome);
                    else
                        throw new ValidationException("O nome tem que ter mais de uma palavra!");
                }


            }
            catch(InputMismatchException exception) {
                System.out.println("- O valor inserido é inválido!");
            }
            catch(ValidationException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }
}
