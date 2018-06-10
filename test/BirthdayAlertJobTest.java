
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import jobs.BirthdayAlertJob;
import models.User;
import org.apache.commons.mail.EmailException;
import org.junit.Test;
import play.Logger;
import play.test.UnitTest;

/**
 *
 * @author simon
 */
public class BirthdayAlertJobTest extends UnitTest {
 
    
    @Test
    public void testCompareDates() {
        Calendar calendar1 = new GregorianCalendar(2000, 4, 10);
        Date date1 = calendar1.getTime();
        
        Calendar calendar2 = new GregorianCalendar(1990, 4, 13);
        Date date2 = calendar2.getTime();
        
        BirthdayAlertJob job = new BirthdayAlertJob();
        
        assertEquals(3, job.compareDates(date1, date2));
    }
    
    @Test
    public void testSendBirthdayReminder() {
        BirthdayAlertJob job = new BirthdayAlertJob();
        
        User recipient = new User("sbbucknerv@gmail.com", "sbuckner", "password", "Simon", "Buckner", null, false);
        recipient.alertdays = 3;
        User contact = new User("someone@gmail.com", "someone", "password", "John", "Doe", null, false);
        
        try {
            job.sendBirthdayReminder(recipient, contact);
        } catch (Exception e) {
            Logger.error(e, e.getMessage());
            fail();
        }
    }
    
}
