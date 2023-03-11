import Database.Connection.DbConnection;
import Domain.Entities.MedicoEntity;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

public class Main{
    public static void main(String[] args){
        MongoDatabase db = DbConnection.getDatabase();

        MongoCollection<Document> collection = db.getCollection("medico");
        MedicoEntity entity = new MedicoEntity(12, "Doutor Roberto", "roberto@gmail.com", "roberto123");

        collection.insertOne(entity.toDocument());

        System.out.println(db.getName());
    }
}