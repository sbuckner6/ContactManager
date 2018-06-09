/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import data.UserDataAccess;
import java.util.Date;
import models.User;

public class UserBusinessLogic {
    
    public static User loginUser(String emailOrUsername, String password) {
        User user = UserDataAccess.getUserByEmailOrUsername(emailOrUsername);
        
        if (user == null) {
            return null;
        }
        
        if (!user.password.equals(password)) {
            return null;
        }
        
        return user;
    }
    
    public static boolean createUser(String email, String username, 
            String password, String firstname, String lastname, Date birthday, 
            boolean isAdmin) {
        
        User user = new User(email, username, password, firstname, lastname, 
                birthday, false);
        
        return UserDataAccess.createUser(email, username, password, firstname,
                lastname, birthday, false);
    }
}
