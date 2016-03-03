package daos;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
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

    public static Map<String, Object> signUpUser(JsonNode json) {
        Gson gson = new Gson();
        Map<String, Object> signUpResponse = new HashMap<String, Object>();

        try {
            String jsonString = json.toString();
            Document document = gson.fromJson(jsonString, Document.class);
            MongoDatabase db = MongoUtility.initDb();
            MongoCollection<Document> userCollection = db.getCollection(DbConstants.COLLECTION_USERS);
            userCollection.insertOne(document);
            signUpResponse.put(StatusConstants.STATUS, StatusConstants.SUCCESS);
            signUpResponse.put(StatusConstants.MESSAGE, StatusConstants.SIGN_UP_SUCCESS);
        } catch (MongoException e) {
            e.printStackTrace();
            signUpResponse.put(StatusConstants.STATUS, StatusConstants.FAILURE);
            signUpResponse.put(StatusConstants.MESSAGE, StatusConstants.SIGN_UP_FAILED);
        }
        return signUpResponse;
    }
}
