package data;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.Date;

import models.User;

public class UserDataAccess {
    
    public static User getUserById(int id) {
        User user = User.findById(id);
        return user;
    }
    
    public static User getUserByEmailOrUsername(String emailOrUsername) 
            throws SQLDataException {
        User user = User.find("email = :email OR username = :username")
                .bind("email", emailOrUsername)
                .bind("username", emailOrUsername)
                .first();
        
        if (user == null) {
            throw new SQLDataException("User not found!");
        }
        
        return user;
    }
    
    public static void createUser(String email, String username, 
            String password, String firstname, String lastname, Date birthday, 
            boolean isAdmin) throws SQLException {
        
        User user = new User(email, username, password, firstname, lastname, 
                birthday, false);
        
        try {
            if (!user.validateAndSave()) {
                throw new SQLException();
            }
        } catch (Exception e) {
            throw new SQLException("Failed to create user! " + e.getMessage());
        }
    }
}
