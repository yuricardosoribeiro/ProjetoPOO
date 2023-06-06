package Database.Repository;

import Domain.Entities.MedicoEntity;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class MedicoRepository extends BaseRepository<MedicoEntity> {

    public MedicoRepository() {
        super("medico", new MedicoEntity());
    }

    public List<MedicoEntity> GetMedicosByEspecialidade(String especialidade) {
        List<MedicoEntity> medicos = this.GetAll();

        return medicos.stream().filter(medico ->
                medico.getEspecialidade().equals(especialidade)).collect(Collectors.toList());
    }
}
