package business;

import data.UserDataAccess;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.Date;

import javax.xml.bind.ValidationException;

import models.User;

/**
 *
 * @author simon
 */
public class UserBusinessLogic {
    
    /**
     *
     * @param emailOrUsername
     * @param password
     * @return
     * @throws SQLDataException
     * @throws ValidationException
     */
    public static User loginUser(String emailOrUsername, String password) 
            throws SQLDataException, ValidationException {
        
        final User user;
        
        try {
            user = UserDataAccess.getUserByEmailOrUsername(emailOrUsername);
        } catch (Exception e) {
            throw new SQLDataException(e.getMessage());
        }
          
        if (user == null) {
            throw new SQLDataException("Username or email not found!");
        }
        
        if (!user.password.equals(password)) {
            throw new ValidationException("Incorrect password!");
        }
        
        return user;
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
     * @param isAdmin
     * @throws SQLException
     * @throws ValidationException
     */
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
