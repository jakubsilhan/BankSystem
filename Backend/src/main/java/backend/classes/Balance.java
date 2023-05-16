
package backend.classes;

public class Balance {
    
    long accountNumber;
    String currency;
    String abbreviation;
    double amount;

    public Balance(){};
    
    public Balance(long accountNumber, String currency, String abbreviation, double amount) {
        this.accountNumber = accountNumber;
        this.currency = currency;
        this.abbreviation = abbreviation;
        this.amount = amount;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public String getCurrency() {
        return currency;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public double getAmount() {
        return amount;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    
}
