
package backend.classes;

public class CurrencyExchangeRate {
    private String country;
    private String currency;
    private int amount;
    private String abbreviation;
    private double rate;

    public CurrencyExchangeRate() {}

    public CurrencyExchangeRate(String country, String currency, int amount, String abbreviation, double rate) {
        this.country = country;
        this.currency = currency;
        this.amount = amount;
        this.abbreviation = abbreviation;
        this.rate = rate;
    }

    public String getCountry() {
        return country;
    }

    public String getCurrency() {
        return currency;
    }

    public int getAmount() {
        return amount;
    }

    public String getCode() {
        return abbreviation;
    }

    public double getRate() {
        return rate;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setCode(String code) {
        this.abbreviation = code;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
    
}
