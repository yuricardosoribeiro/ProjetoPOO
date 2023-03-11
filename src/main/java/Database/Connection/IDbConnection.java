package Database.Connection;

import com.mongodb.client.MongoDatabase;

public interface IDbConnection {
    MongoDatabase GetDatabase();
}
