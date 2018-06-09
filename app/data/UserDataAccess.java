package data;

import java.util.Date;
import models.User;
import play.db.jpa.GenericModel.JPAQuery;


public class UserDataAccess {
    
    public static User getUserById(int id) {
        User user = User.findById(id);
        return user;
    }
    
    public static User getUserByEmailOrUsername(String emailOrUsername) {
        User user = User.find("email = :email OR username = :username")
                .bind("email", emailOrUsername)
                .bind("username", emailOrUsername)
                .first();
        
        return user;
    }
    
    public static boolean createUser(String email, String username, 
            String password, String firstname, String lastname, Date birthday, 
            boolean isAdmin) {
        
        User user = new User(email, username, password, firstname, lastname, 
                birthday, false);
        
        return user.validateAndSave();
    }
}
