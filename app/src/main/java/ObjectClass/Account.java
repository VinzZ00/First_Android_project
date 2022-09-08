package ObjectClass;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

public class Account implements Comparable<Account> {
    private String Name, email, password;
    private String dateCreated;

    public Account() {}


    public Account(String name, String email, String password) {
        Name = name;
        this.email = email;
        this.password = password;
        this.dateCreated = String.valueOf(new Date());
    }

    public void setName(String name) {
        Name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateCreated() {
        return this.dateCreated;
    }

    @Override
    public int compareTo(Account account) {
        return this.getName().compareTo(account.getName());
    }
}
