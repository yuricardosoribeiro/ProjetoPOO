package Database.Repository;

import Database.Connection.DbConnection;
import Database.Connection.IDbConnection;
import Domain.Entities.MedicoEntity;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MedicoRepository {
    private IDbConnection DbConnection;
    private MongoDatabase Db;
    private MongoCollection<Document> Collection;

    public MedicoRepository() {
        this.DbConnection = new DbConnection();
        this.Db = this.DbConnection.GetDatabase();
        this.Collection = this.Db.getCollection("medico");
    }

    public void Insert(MedicoEntity entity) {
        this.Collection.insertOne(entity.toDocument());
    }
}
