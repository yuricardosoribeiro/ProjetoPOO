package Domain.Entities;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.List;

public abstract class BaseEntity<TEntity> implements Cloneable {
    protected ObjectId Id;
    public List<String> ValidationErrors;

    public boolean IsValid() {
        return ValidationErrors.size() == 0;
    }

    public abstract Document ToDocument();
    public abstract void ToClass(Document document);

    public ObjectId getId() {
        return Id;
    }

    public void setId(ObjectId id) {
        Id = id;
    }

    @Override
    public TEntity clone() {
        try {
            return (TEntity) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
