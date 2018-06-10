package business;

import data.ContactDataAccess;
import data.UserDataAccess;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import models.Contact;
import models.User;

/**
 *
 * @author simon
 */
public class ContactBusinessLogic {

    /**
     *
     * @param userId
     * @return
     * @throws SQLDataException
     */
    public static List<User> getContactsAsUsers(long userId) 
            throws SQLDataException {
        
        final List<Contact> contacts;
        
        try {
            contacts = ContactDataAccess.getContacts(userId);
        } catch (Exception e) {
            throw new SQLDataException(e.getMessage());
        }
        
        List<Long> contactIds = new ArrayList<Long>();
        
        for (Contact contact : contacts) {
            contactIds.add(contact.contactid);
        }
        
        return UserDataAccess.getUsersByIdList(contactIds);
    }
    
    /**
     *
     * @param userId
     * @param contactEmailOrUsername
     * @throws SQLDataException
     * @throws SQLException
     */
    public static void addContactByEmailOrUsername(long userId, 
            String contactEmailOrUsername)  
            throws SQLDataException, SQLException {
        
        final User contactUser;
        
        try {
            contactUser =
                    UserDataAccess.getUserByEmailOrUsername(contactEmailOrUsername);
            ContactDataAccess.addContact(userId, contactUser.id);
        } catch (SQLDataException e) {
            throw e;
        } catch (SQLException e) {
            throw e;
        }       
    }
    
    /**
     *
     * @param userId
     * @param contactId
     */
    public static void deleteContact(long userId, long contactId) {
        ContactDataAccess.deleteContact(userId, contactId);
    }
}
