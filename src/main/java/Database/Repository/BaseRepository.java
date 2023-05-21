package Database.Repository;

import Database.Connection.DbConnection;
import Database.Connection.IDbConnection;
import Domain.Entities.BaseEntity;
import Domain.Entities.MedicoEntity;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public abstract class BaseRepository<TEntity> {
    protected IDbConnection DbConnection;
    protected MongoDatabase Db;
    protected MongoCollection<Document> Collection;

    public BaseRepository(String repository) {
        this.DbConnection = new DbConnection();
        this.Db = this.DbConnection.GetDatabase();
        this.Collection = this.Db.getCollection(repository);
    }
}
