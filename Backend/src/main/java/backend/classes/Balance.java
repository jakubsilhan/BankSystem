
package backend.classes;

public class Balance {
    
    long accountNumber;
    String currency;
    String abbreviation;
    int amount;

    public Balance(){};
    
    public Balance(long accountNumber, String currency, String abbreviation, int amount) {
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

    public int getAmount() {
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

    public void setAmount(int amount) {
        this.amount = amount;
    }
    
    
}
