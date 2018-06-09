package models;

import java.util.Date;
import javax.persistence.*;

import play.db.jpa.*;

@Entity
public class User extends Model {
    
    public String email;
    public String username;
    public String password;
    public String firstName;
    public String lastName;
    public Date birthday;
    public boolean isAdmin;
    
    public User(String email, String username, String password, String firstname, String lastname, Date birthday, boolean isAdmin) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.firstName = firstname;
        this.lastName = lastname;
        this.birthday = birthday;
        this.isAdmin = isAdmin;
    }
}

