package Services;

import Database.Repository.ConsultaRepository;
import Database.Repository.ConvenioRepository;
import Domain.Entities.MedicoEntity;
import org.bson.types.ObjectId;

import java.util.Scanner;

public class AreaMedicoService {
    private ConsultaRepository consultaRepository;
    private Scanner scanner;
    private MedicoEntity medico;

    public AreaMedicoService(MedicoEntity medico) {
        this.scanner = new Scanner(System.in);
        this.consultaRepository = new ConsultaRepository();
        this.medico = medico;
    }

    public void MostrarAgendaDoDia() {

    }
}
