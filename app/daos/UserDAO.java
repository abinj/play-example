package daos;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import constants.DbConstants;
import constants.StatusConstants;
import org.bson.Document;
import utility.MongoUtility;

import java.util.HashMap;
import java.util.Map;

/**
 * DAO class for user operations.
 */
public class UserDAO {
    static MongoDatabase db;
    static {
        db = MongoUtility.initDb();
    }


    public static Map<String, Object> signUpUser(JsonNode json) {
        Gson gson = new Gson();
        Map<String, Object> signUpResponse = new HashMap<String, Object>();
        String collectionName = DbConstants.COLLECTION_USERS;

        try {
            String jsonString = json.toString();
            Document document = gson.fromJson(jsonString, Document.class);
            BasicDBObject query = new BasicDBObject(DbConstants.EMAIL_KEY, document.get(DbConstants.EMAIL_KEY));
            boolean isUserExist = checkUserCollection(collectionName, query);
            if (!isUserExist) {
                MongoCollection<Document> userCollection = db.getCollection(collectionName);
                userCollection.insertOne(document);
                signUpResponse.put(StatusConstants.STATUS, StatusConstants.SUCCESS);
                signUpResponse.put(StatusConstants.MESSAGE, StatusConstants.SIGN_UP_SUCCESS);
            } else {
                signUpResponse.put(StatusConstants.STATUS, StatusConstants.FAILURE);
                signUpResponse.put(StatusConstants.MESSAGE, StatusConstants.USER_ALREADY_EXIST);
            }
        } catch (MongoException e) {
            e.printStackTrace();
            signUpResponse.put(StatusConstants.STATUS, StatusConstants.FAILURE);
            signUpResponse.put(StatusConstants.MESSAGE, StatusConstants.SIGN_UP_FAILED);
        }
        return signUpResponse;
    }

    public static boolean checkUserCollection(String collectionName, BasicDBObject query) {
        MongoCollection<Document> userCollection = db.getCollection(collectionName);
        FindIterable<Document> documents = userCollection.find(query);
        MongoCursor<Document> mongoCursor = documents.iterator();
        if (mongoCursor.hasNext()) {
            mongoCursor.close();
            return true;
        }
        return false;
    }
}
