package controllers;

import business.UserBusinessLogic;

import java.util.*;

import models.*;

import play.Logger;
import play.mvc.*;
import play.mvc.Scope.Session;

public class Application extends Controller {
    
    private static Boolean isLoggedIn() {
        final Session session = Session.current();
        final String currentUserId = session.get("currentUserId");
        return currentUserId != null && !currentUserId.isEmpty();
    } 
    
    public static void index(String error) {
        if (!isLoggedIn()) {
            login(null);
            return;
        }
        
        render();
    }

    public static void login(String error) {
        if (isLoggedIn()) {
            index(null);
            return;
        }
        render(error);
    }
    
    public static void loginPOST(String emailOrUsername, String password) {
        User currentUser;
        
        try {
            currentUser = UserBusinessLogic.loginUser(emailOrUsername, password);       
            final Session session = Session.current();
            session.put("currentUserId", currentUser.id.toString());
            index(null);
        } catch (Exception e) {         
            Logger.error(e, e.getMessage());
            login("Invalid credentials!");
            return;
        }
    }  
    
    public static void register(String error) {
        if (isLoggedIn()) {
            index(null);
            return;
        }
        render(error);
    }
    
    public static void registerPOST(String email, String emailConfirm, 
            String username, String password, String passwordConfirm, 
            String firstName, String lastName, Date birthday) {
        
        try {
            UserBusinessLogic.createUser(email, emailConfirm, username, 
                    password, passwordConfirm, firstName, lastName, birthday, 
                    true); 
        } catch (Exception e) {   
            Logger.error(e, e.getMessage());
            register(e.getMessage());
            return;
        }
        
        login(null);
    }
    
    public static void logout() {
        final Session session = Session.current();
        session.clear();
        login(null);
    }
     
}
    
