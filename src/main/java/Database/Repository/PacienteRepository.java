package Database.Repository;

import Domain.Entities.MedicoEntity;
import Domain.Entities.PacienteEntity;
import Services.PacienteService;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PacienteRepository extends BaseRepository<PacienteEntity> {
    public PacienteRepository() {
        super("paciente", new PacienteEntity());
    }
}
