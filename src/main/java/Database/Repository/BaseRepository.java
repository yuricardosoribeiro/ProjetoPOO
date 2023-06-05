package Database.Repository;

import Database.Connection.DbConnection;
import Database.Connection.IDbConnection;
import Domain.Entities.BaseEntity;
import Domain.Entities.MedicoEntity;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class BaseRepository<TEntity extends BaseEntity> {
    protected IDbConnection DbConnection;
    protected MongoDatabase Db;
    protected MongoCollection<Document> Collection;
    protected TEntity Entity;

    public BaseRepository(String repository, TEntity entity) {
        this.DbConnection = new DbConnection();
        this.Db = this.DbConnection.GetDatabase();
        this.Collection = this.Db.getCollection(repository);
        this.Entity = entity;
    }

    public void Insert(TEntity entity) {
        this.Collection.insertOne(entity.ToDocument());
    }

    public List<TEntity> GetAll() {
        List<TEntity> entityLst = new ArrayList<>();
        FindIterable<Document> iterable = this.Collection.find();

        Iterator iterator = iterable.iterator();

        while(iterator.hasNext()) {
            Document doc = (Document) iterator.next();
            this.Entity.ToClass(doc);

            // Fazemos um clone de Entity para perdermos a referência e o TEntity poder ser modificado sem modificar todos os itens da lista
            TEntity clone = (TEntity) this.Entity.clone();

            entityLst.add(clone);
        }

        return entityLst;
    }

    public TEntity GetById(ObjectId Id) {
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("_id", Id);

        Document doc = this.Collection.find(searchQuery).first();
        this.Entity.ToClass(doc);

        return this.Entity;
    }

    public void Update(TEntity entity) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", entity.getId());

        BasicDBObject updated = new BasicDBObject();
        updated.put("$set", entity.ToDocument());

        this.Collection.updateOne(query, updated);
    }

    public void Delete(ObjectId Id) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", Id);

        this.Collection.deleteOne(query);
    }
}
