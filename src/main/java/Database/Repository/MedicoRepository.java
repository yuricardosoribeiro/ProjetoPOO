package Database.Repository;

import Domain.Entities.MedicoEntity;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MedicoRepository extends BaseRepository<MedicoEntity> {

    public MedicoRepository() {
        super("medico", new MedicoEntity());
    }
}
