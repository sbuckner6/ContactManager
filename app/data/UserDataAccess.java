package data;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import models.User;
import play.Logger;

/**
 *
 * @author simon
 */
public class UserDataAccess {
    
    /**
     *
     * @param id
     * @return
     */
    public static User getUser(long id) {
        final User user = User.findById(id);
        return user;
    }
    
    /**
     *
     * @param ids
     * @return
     */
    public static List<User> getUsersByIdList(List<Long> ids) {
        
        final List<User> users = User.find("id in :ids")
                .bind("ids", ids).fetch();

        return users;
    }
    
    /**
     *
     * @param emailOrUsername
     * @return
     * @throws SQLDataException
     */
    public static User getUserByEmailOrUsername(String emailOrUsername) 
            throws SQLDataException {
        final User user = User.find("email = :email OR username = :username")
                .bind("email", emailOrUsername)
                .bind("username", emailOrUsername)
                .first();
        
        if (user == null) {
            throw new SQLDataException("User not found!");
        }
        
        return user;
    }
    
    /**
     *
     * @param email
     * @param username
     * @param password
     * @param firstname
     * @param lastname
     * @param birthday
     * @param isAdmin
     * @throws SQLException
     */
    public static void createUser(String email, String username, 
            String password, String firstname, String lastname, Date birthday, 
            boolean isAdmin) throws SQLException {
        
        final User user = new User(email, username, password, firstname, 
                lastname, birthday, false);
        
        try {
            if (!user.validateAndSave()) {
                throw new SQLException();
            }
        } catch (Exception e) {
            throw new SQLException("Failed to create user! " + e.getMessage());
        }
    }
}
