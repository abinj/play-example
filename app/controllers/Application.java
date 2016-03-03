package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mongodb.MongoException;
import daos.UserDAO;
import models.LogIn;
import models.User;
import org.bson.Document;
import play.*;
import play.api.db.DB;
import play.api.mvc.*;
import play.libs.Json;
import play.mvc.*;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import sun.rmi.runtime.Log;
import views.html.*;

import java.util.HashMap;
import java.util.Map;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public static Result login() throws JsonProcessingException{
        JsonNode json = request().body().asJson();
        ObjectMapper jsonObjectMapper = new ObjectMapper();
        LogIn logIn = jsonObjectMapper.treeToValue(json, LogIn.class);

        return Results.ok();
    }

    public static Result signUp() throws JsonProcessingException{
        Map<String, Object> signUpResponse = new HashMap<String, Object>();

        JsonNode json = request().body().asJson();
        signUpResponse = UserDAO.signUpUser(json);
        JsonNode responseJson = Json.toJson(signUpResponse);
        return Results.ok(responseJson);
    }


}
