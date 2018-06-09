package controllers;

import business.UserBusinessLogic;
import play.mvc.*;
import play.mvc.Scope.Session;

import java.util.*;

import models.*;
import play.db.jpa.GenericModel.JPAQuery;

public class Application extends Controller {

    private static Boolean isLoggedIn() {
        final Session session = Session.current();
        final String currentUserId = session.get("currentUserId");
        return currentUserId != null && !currentUserId.isEmpty();
    } 
    
    public static void index() {
        if (!isLoggedIn()) {
            login();
            return;
        }
        
        Session session = Session.current();
        String currentUserId = session.get("currentUserId");
        
        render();
    }

    public static void login() {
        if (isLoggedIn()) {
            index();
            return;
        }
        render();
    }
    
    public static void register() {
        if (isLoggedIn()) {
            index();
            return;
        }
        render();
    }
    
    public static void registerPOST(String email, String username, 
            String password, String firstName, String lastName, Date birthday) {
        
        if (UserBusinessLogic.createUser(email, username, password, firstName, 
                lastName, birthday, true)) {
            login();
            return;
        }
        
        register();
    }
    
    public static void logout() {
        final Session session = Session.current();
        session.clear();
        login();
    }
    
    public static void loginPOST(String emailOrUsername, String password) {
        User currentUser = UserBusinessLogic.loginUser(emailOrUsername, password);
        
        if (currentUser != null) {
            final Session session = Session.current();
            session.put("currentUser", currentUser.id);
            index();
            return;
        }
         
        login();
    }   
}
    
