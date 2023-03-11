public class DbConnection {

    public MongoDatabase getDatabase() {
        ConnectionString connectionString = new ConnectionString("mongodb+srv://yuricardoso:projetopoo@testejs.n6fcx.mongodb.net/?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .serverApi(ServerApi.builder()
                        .version(ServerApiVersion.V1)
                        .build())
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("ProjetoPOO");

        return database;
    }


}