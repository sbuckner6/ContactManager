package models;

import java.util.Date;

import javax.persistence.*;

import play.db.jpa.*;

/**
 *
 * @author simon
 */
@Entity
@Table(name="\"User\"")
public class User extends Model {
    
    public String email;
    public String username;
    public String password;
    public String firstname;
    public String lastname;
    public Date birthday;
    public int alertdays;
    public boolean isadmin;
    
    public User(String email, String username, String password, 
            String firstname, String lastname, Date birthday, boolean isadmin) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.alertdays = 1;
        this.isadmin = isadmin;
    }
}

