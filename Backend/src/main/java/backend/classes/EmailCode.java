
package backend.classes;

public class EmailCode {
    
    private String email;
    private String code;
    
    public EmailCode() {}
    
    public EmailCode(String email, String code) {
        this.email = email;
        this.code = code;
    }

    // getters and setters

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
