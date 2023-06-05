package Services;

import Database.Repository.ConvenioRepository;
import Database.Repository.MedicoRepository;
import Domain.Constants.EspecialidadeConstant;
import Domain.Entities.ConvenioEntity;
import Domain.Utils.Console;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ConvenioService {
    private ConvenioRepository convenioRepository;
    private Scanner scanner = new Scanner(System.in);

    public ConvenioService() {
        this.convenioRepository = new ConvenioRepository();
    }

    public ConvenioEntity VincularConvenio() {
        List<ConvenioEntity> lstConvenios = this.convenioRepository.GetAll();
        ConvenioEntity convenioEscolhido = new ConvenioEntity();
        boolean condition = true;

        for(int i = 0; i < lstConvenios.size(); i++) {
            System.out.println((i+1) + " - " + lstConvenios.get(i).getNome());
        }

        while(condition) {
            try {
                System.out.print("Convênio: ");
                int choice = scanner.nextInt();

                if(choice > lstConvenios.size() + 1)
                    Console.EmitError("Esse convênio não existe!");
                else {
                    convenioEscolhido = lstConvenios.get(choice - 1);
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

        return convenioEscolhido;
    }
}
