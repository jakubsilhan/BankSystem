
package backend.classes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    private long id;
    private String username;
    private String password;
    private String email;
    private long accountNumber;
    
    @JsonCreator
    public User(@JsonProperty("id") long id,
                @JsonProperty("userName") String username,
                @JsonProperty("email") String email,
                @JsonProperty("password") String password,
                @JsonProperty("accountNumber") long accountNumber) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.accountNumber = accountNumber;
    }

    public long getId(){
        return id;
    }
    
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public long getAccountNumber() {
        return accountNumber;
    }
       
    
}
