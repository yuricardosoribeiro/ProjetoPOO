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
        super("medico");
    }

    public void Insert(MedicoEntity entity) {
        this.Collection.insertOne(entity.ToDocument());
    }

    public List<MedicoEntity> GetAll() {
        List<MedicoEntity> entityLst = new ArrayList<>();
        FindIterable<Document> iterable = this.Collection.find();

        Iterator iterator = iterable.iterator();

        while(iterator.hasNext()) {
            Document doc = (Document) iterator.next();
            entityLst.add(new MedicoEntity().ToClass(doc));
        }

        return entityLst;
    }

    public MedicoEntity GetById(ObjectId Id) {
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("_id", Id);

        Document doc = this.Collection.find(searchQuery).first();

        return new MedicoEntity().ToClass(doc);
    }

    public void Update(MedicoEntity entity) {
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
