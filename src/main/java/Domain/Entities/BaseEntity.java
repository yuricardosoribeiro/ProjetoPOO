package Domain.Entities;

import org.bson.Document;

public abstract class BaseEntity {
    int Id;

    abstract Document toDocument();
}
