import Database.Connection.DbConnection;
import Database.Repository.MedicoRepository;
import Domain.Entities.MedicoEntity;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

public class Main{
    public static void main(String[] args){
        MedicoEntity entity = new MedicoEntity(12, "Doutor Yuri", "yuri@gmail.com", "yurinhobembonitinho");

        MedicoRepository repository = new MedicoRepository();
        repository.Insert(entity);

        System.out.println();
    }
}