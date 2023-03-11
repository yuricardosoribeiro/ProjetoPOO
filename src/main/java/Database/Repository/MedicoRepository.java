package Database.Repository;

import Domain.Entities.MedicoEntity;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MedicoRepository extends BaseRepository {

    public MedicoRepository() {
        super("medico");
    }

    public void Insert(MedicoEntity entity) {
        this.Collection.insertOne(entity.toDocument());
    }

    public List<MedicoEntity> GetAll() {
        List<MedicoEntity> entityLst = new ArrayList<>();
        FindIterable<Document> iterable = this.Collection.find();

        Iterator iterator = iterable.iterator();

        while(iterator.hasNext()) {
            Document doc = (Document) iterator.next();
            entityLst.add(MedicoEntity.toClass(doc));
        }

        return entityLst;
    }

    public MedicoEntity GetById(ObjectId Id) {
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("_id", Id);

        Document doc = this.Collection.find(searchQuery).first();

        return MedicoEntity.toClass(doc);
    }

    public void Update(MedicoEntity entity) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", entity.getId());

        BasicDBObject updated = new BasicDBObject();
        updated.put("$set", entity.toDocument());

        this.Collection.updateOne(query, updated);
    }

    public void Delete(ObjectId Id) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", Id);

        this.Collection.deleteOne(query);
    }
}
