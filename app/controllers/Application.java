package controllers;

import business.ContactBusinessLogic;
import business.UserBusinessLogic;
import java.sql.SQLDataException;

import java.util.*;

import models.*;

import play.Logger;
import play.mvc.*;
import play.mvc.Scope.Session;

/**
 *
 * @author simon
 */
public class Application extends Controller {
    
    /**
     *
     * @param error
     */
    public static void index(String error) {      
        final String currentUserIdString = session.get("id");
        
        if (currentUserIdString == null || currentUserIdString.isEmpty()) {
            login(null);
            return;            
        }
        
        final long currentUserId = Long.parseLong(session.get("id"));
        final List<User> contactUsers;
        
        try {
            contactUsers = ContactBusinessLogic.getContactsAsUsers(currentUserId);
        } catch (SQLDataException e) {
            render(error, null);
            return;
        }
            
        render(error, contactUsers);
    }

    /**
     *
     * @param contactId
     */
    public static void indexDELETE(long contactId) {       
        final long currentUserId = Long.parseLong(session.get("id"));
        
        ContactBusinessLogic.deleteContact(currentUserId, contactId);
        
        index(null);
    }
    
    /**
     *
     * @param error
     */
    public static void login(String error) {       
        final String currentUserIdString = session.get("id");
        
        if (currentUserIdString != null) {
            index(null);
            return;            
        }
        
        render(error);
    }
    
    /**
     *
     * @param emailOrUsername
     * @param password
     */
    public static void loginPOST(String emailOrUsername, String password) {      
        User currentUser;
        
        try {
            currentUser = UserBusinessLogic.loginUser(emailOrUsername, password);
            
            session.put("id", Long.toString(currentUser.id));
            session.put("email", currentUser.email);
            session.put("username", currentUser.username);
            session.put("firstName", currentUser.firstname);
            session.put("lastName", currentUser.lastname);
            session.put("birthday", currentUser.birthday.toString());
            session.put("alertDays", Integer.toString(currentUser.alertdays));
            
            index(null);
        } catch (Exception e) {         
            Logger.error(e, e.getMessage());
            login("Invalid credentials!");
            return;
        }
    }  
    
    /**
     *
     * @param error
     */
    public static void register(String error) {       
        final String currentUserIdString = session.get("id");
        
        if (currentUserIdString != null) {
            index(null);
            return;            
        }
        
        render(error);
    }
    
    /**
     *
     * @param email
     * @param emailConfirm
     * @param username
     * @param password
     * @param passwordConfirm
     * @param firstName
     * @param lastName
     * @param birthday
     */
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
    
    /**
     *
     */
    public static void logout() {
        session.clear();
        login(null);
    }
    
    /**
     *
     * @param error
     */
    public static void addContact(String error) {
        render(error);
    }
    
    /**
     *
     * @param emailOrUsername
     */
    public static void addContactPOST(String emailOrUsername) {        
        try {
            final long currentUserId = Long.parseLong(session.get("id"));
            ContactBusinessLogic.addContactByEmailOrUsername(currentUserId,
                    emailOrUsername);
        } catch (Exception e) {   
            Logger.error(e, e.getMessage());
            addContact("Could not find user!");
            return;
        }
        index(null);
    }
    
    /**
     *
     * @param error
     * @param error
     */
    public static void settings(String error) {
        final int alertDays = Integer.parseInt(session.get("alertDays"));
        render(error, alertDays);
    }
    
    /**
     *
     * @param alertDays
     */
    public static void settingsPOST(int alertDays) {
        final long currentUserId = Long.parseLong(session.get("id"));
        
        try {
            UserBusinessLogic.updateAlertDaysForUser(currentUserId, alertDays);
            session.put("alertDays", alertDays);
        } catch (Exception e) { 
            Logger.error(e, e.getMessage());
            settings(e.getMessage());
            return;
        }
        
        settings(null);
    }
     
}
    
