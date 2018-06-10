package business;

import data.UserDataAccess;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.Date;

import javax.xml.bind.ValidationException;

import models.User;

public class UserBusinessLogic {
    
    public static User loginUser(String emailOrUsername, String password) 
            throws SQLDataException, ValidationException {
        
        User user;
        
        try {
            user = UserDataAccess.getUserByEmailOrUsername(emailOrUsername);
        } catch (SQLDataException e) {
            throw e;
        }
          
        if (user == null) {
            throw new SQLDataException("Username or email not found!");
        }
        
        if (!user.password.equals(password)) {
            throw new ValidationException("Incorrect password!");
        }
        
        return user;
    }
    
    public static void createUser(String email, String emailConfirm, 
            String username, String password, String passwordConfirm, 
            String firstName, String lastName, Date birthday, 
            boolean isAdmin) throws SQLException, ValidationException {      
              
        if (!email.equals(emailConfirm)) {
            throw new ValidationException("Emails do not match!");
        }
        
        if (!password.equals(passwordConfirm)) {
            throw new ValidationException("Passwords do not match!");
        }
        
        if (email.isEmpty() || password.isEmpty() || firstName.isEmpty() || 
                lastName.isEmpty() || birthday == null) {
            throw new ValidationException("All fields required!");
        }
        
        try {
            UserDataAccess.createUser(email, username, password, firstName,
                lastName, birthday, false);
        } catch (SQLException e) {
            throw e;
        }
    }
}
