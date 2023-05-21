package Domain.Entities;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.List;

public abstract class BaseEntity<TEntity> {
    protected ObjectId Id;
    public List<String> ValidationErrors;

    public boolean IsValid() {
        return ValidationErrors.size() == 0;
    }

    public abstract Document ToDocument();
}
