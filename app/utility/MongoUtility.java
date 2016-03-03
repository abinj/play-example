package utility;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoDatabase;
import play.Logger;
import play.api.db.DB;

/**
 * Utility class for create a mongodb client
 */
public class MongoUtility {
    private static MongoDatabase db = null;
    private static MongoClient mongoClient;

    public static MongoDatabase initDb() {
        if (mongoClient == null) {
            try {
                String localHostName = play.Configuration.root().getString("mongo.local.hostname");
                Integer localPortNum = play.Configuration.root().getInt("mongo.local.port");
                mongoClient = new MongoClient(localHostName, localPortNum);
            } catch (MongoException e) {
                e.printStackTrace();
                Logger.error("Exception while initiating Local MongoDB", e);
                if (mongoClient != null) {
                    mongoClient.close();
                }
            }
        } else {
            db = mongoClient.getDatabase("playexample");
        }
        return db;
    }

}
