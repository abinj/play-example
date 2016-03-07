package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import daos.UserDAO;
import models.LogIn;
import play.libs.Json;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import views.html.*;

import java.util.HashMap;
import java.util.Map;

public class UserApplication extends Controller {

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

    public static Result signin() throws JsonProcessingException {
        Map<String, Object> signInResponse = new HashMap<String, Object>();

        JsonNode json = request().body().asJson();
        signInResponse = UserDAO.signInUser(json);
        JsonNode responseJson = Json.toJson(signInResponse);
        return Results.ok(responseJson);
    }


}
