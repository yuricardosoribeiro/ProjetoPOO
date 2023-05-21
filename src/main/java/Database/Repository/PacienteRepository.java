package Database.Repository;

import Domain.Entities.MedicoEntity;
import Domain.Entities.PacienteEntity;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PacienteRepository extends BaseRepository<PacienteEntity> {
    public PacienteRepository() {
        super("paciente");
    }

    public void Insert(PacienteEntity entity) {
        this.Collection.insertOne(entity.ToDocument());
    }

    public List<PacienteEntity> GetAll() {
        List<PacienteEntity> entityLst = new ArrayList<PacienteEntity>();
        FindIterable<Document> iterable = this.Collection.find();

        Iterator iterator = iterable.iterator();

        while(iterator.hasNext()) {
            Document doc = (Document) iterator.next();
            entityLst.add(PacienteEntity.ToClass(doc));
        }

        return entityLst;
    }

    public void Delete(ObjectId Id) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", Id);

        this.Collection.deleteOne(query);
    }
}
