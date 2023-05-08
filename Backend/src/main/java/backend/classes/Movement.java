
package backend.classes;

public class Movement {
    private String amount;
    private String day;
    
    public Movement(){};
            
    public Movement(String amount, String day) {
        this.amount = amount;
        this.day = day;
    }

    public String getAmount() {
        return amount;
    }

    public String getDay() {
        return day;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setDay(String day) {
        this.day = day;
    }
    
    
}
