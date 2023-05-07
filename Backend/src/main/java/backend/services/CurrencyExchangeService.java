
package backend.services;

import backend.classes.CurrencyExchangeRate;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CurrencyExchangeService {
    
    private static final String DATAFILE = System.getProperty("user.dir") + File.separator + "data"+ File.separator + "currencies.txt";
    
    public List<CurrencyExchangeRate> getExchangeRates() throws IOException {
        File file = new File(DATAFILE);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        String date = reader.readLine();
        String header = reader.readLine();
        List<CurrencyExchangeRate> exchangeRates = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("\\|");
            String country = parts[0];
            String currency = parts[1];
            int amount = Integer.parseInt(parts[2]);
            String abbreviation = parts[3];
            double rate = Double.parseDouble(parts[4].replace(",", "."));
            CurrencyExchangeRate exchangeRate = new CurrencyExchangeRate(country, currency, amount, abbreviation, rate);
            exchangeRates.add(exchangeRate);
        }
        reader.close();
        return exchangeRates;
    }

    public int ConvertToCZK(String to, int ammount) throws IOException{
        List<CurrencyExchangeRate> rates = getExchangeRates();
        CurrencyExchangeRate rateTo = rates.stream()
                .filter(r -> r.getCode().equals(to))
                .findFirst()
                .orElse(null);
        return (int)rateTo.getRate()*ammount;       
        
    }
}
