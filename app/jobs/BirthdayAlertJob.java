package jobs;

import business.ContactBusinessLogic;
import business.UserBusinessLogic;
import java.sql.SQLDataException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import models.User;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import play.Logger;
import play.jobs.*;

/**
 * Daily job that checks to see if users have any contacts with upcoming 
 * birthdays and notifies them via email.
 *
 * @author simon
 */
@On("0 0 12 * * ?")
public class BirthdayAlertJob extends Job {
    
    /**
     * Calculate how many calendar days date2 is ahead of date1.
     *
     * @param date1
     * @param date2
     * @return
     */
    public long compareDates(Date date1, Date date2) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        int year = calendar.get(Calendar.YEAR);

        calendar.setTime(date2);
        calendar.set(Calendar.YEAR, year);
        date2 = calendar.getTime();
        
        long diffMillis = date2.getTime() - date1.getTime();
        long diffDays = TimeUnit.DAYS.convert(diffMillis, TimeUnit.MILLISECONDS);
        
        return diffDays;
    }
    
    /**
     * Email recipient a reminder of their contact's upcoming birthday.
     *
     * @param recipient
     * @param contact
     */
    public void sendBirthdayReminder(User recipient, User contact) {
        final SimpleEmail email;
        try {
            email = new SimpleEmail();
            email.setFrom("noreply@github.com");
            email.addTo(recipient.email);
            email.setSubject("Only " + Integer.toString(recipient.alertdays) + " days "
                    + "until " + contact.firstname + "'s birthday!");
            email.setMsg("Hi " + recipient.firstname + ",\n\n" + contact.firstname 
                    + " " + contact.lastname + " is having a birthday soon. Let "
                    + "them know you're thinking of them!");
        } catch (EmailException e) {
            Logger.error(e, e.getMessage());
        }
    } 
    
    public void doJob() {
        Date now = new Date();
        
        final List<User> users = UserBusinessLogic.getAllUsers();
        
        for (User user : users) {
            final List<User> contactUsers;
            
            try {
                contactUsers = ContactBusinessLogic
                    .getContactsAsUsers(user.id);
                
                for (User contactUser : contactUsers) {
                    if (compareDates(now, contactUser.birthday) == user.alertdays) {
                        sendBirthdayReminder(user, contactUser);
                    }
                }
            } catch (SQLDataException e) {
                Logger.error(e, e.getMessage());
            }
        }
    }
}