package Database.Repository;

import Database.Connection.DbConnection;
import Database.Connection.IDbConnection;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public abstract class BaseRepository {
    protected IDbConnection DbConnection;
    protected MongoDatabase Db;
    protected MongoCollection<Document> Collection;

    public BaseRepository(String repository) {
        this.DbConnection = new DbConnection();
        this.Db = this.DbConnection.GetDatabase();
        this.Collection = this.Db.getCollection(repository);
    }
}
