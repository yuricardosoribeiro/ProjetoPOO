package Domain.Entities;

import org.bson.Document;
import org.bson.types.ObjectId;

public abstract class BaseEntity<T> {
    protected ObjectId Id;

    abstract Document toDocument();
}
