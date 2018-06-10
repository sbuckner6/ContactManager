package models;

import javax.persistence.*;

import play.db.jpa.*;

/**
 *
 * @author simon
 */
@Entity
@Table(name="\"Contact\"")
public class Contact extends Model {
    
    public long userid;
    public long contactid;
    
    public Contact(long userid, long contactid) {
        this.userid = userid;
        this.contactid = contactid;
    }
}

