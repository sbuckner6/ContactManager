package data;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.List;
import models.Contact;
import models.User;
import play.Logger;

/**
 * Data access layer for Contact-related functionality.
 *
 * @author simon
 */
public class ContactDataAccess {
    
    /**
     * Returns list of Contact objects belonging to User with userId
     *
     * @param userId
     * @return list of contacts
     * @throws SQLDataException
     */
    public static List<Contact> getContacts(long userId) 
            throws SQLDataException {
           
        List<Contact> contacts = Contact.find("userid = :userid")
                .bind("userid", userId).fetch();     
        
        return contacts;
    }
    
    /**
     *
     * @param userId
     * @param contactId
     * @throws SQLException
     */
    public static void addContact(long userId, long contactId) 
            throws SQLException {
        
        final Contact contact = new Contact(userId, contactId);
        
        try {
            if (!contact.validateAndSave()) {
                throw new SQLException();
            }
        } catch (Exception e) {
            throw new SQLException("Failed to add contact! " + e.getMessage());
        } 
    }
    
    /**
     *
     * @param userId
     * @param contactId
     */
    public static void deleteContact(long userId, long contactId) {

        final Contact contact; 
        
        contact = Contact.find("userid = :userid and contactid = :contactid")
                .bind("userid", userId).bind("contactid", contactId).first();
        
        Logger.info("userid " + Long.toString(userId) + " contactid " + Long.toString(contactId));
        
        contact.delete();
    }
}
