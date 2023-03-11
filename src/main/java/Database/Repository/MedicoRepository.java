package Database.Repository;

import Database.Connection.DbConnection;
import Database.Connection.IDbConnection;
import Domain.Entities.MedicoEntity;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MedicoRepository extends BaseRepository {

    public MedicoRepository() {
        super("medico");
    }

    public void Insert(MedicoEntity entity) {
        this.Collection.insertOne(entity.toDocument());
    }
}
