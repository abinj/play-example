package utility;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoDatabase;
import play.Logger;
import play.Play;
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
                String localHostName = Play.application().configuration().getString("mongo.local.hostname");
                Integer localPortNum = Play.application().configuration().getInt("mongo.local.port");
                mongoClient = new MongoClient(localHostName, localPortNum);
            } catch (MongoException e) {
                e.printStackTrace();
                Logger.error("Exception while initiating Local MongoDB", e);
                if (mongoClient != null) {
                    mongoClient.close();
                }
            }
        }
        if(mongoClient != null) {
            db = mongoClient.getDatabase("play");
        }
        return db;
    }

}
