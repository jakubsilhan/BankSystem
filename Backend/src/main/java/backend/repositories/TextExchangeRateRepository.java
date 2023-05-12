
package backend.repositories;

import backend.classes.CurrencyExchangeRate;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class TextExchangeRateRepository implements ExchangeRateRepository{

    private static File dataFile;
    
    public TextExchangeRateRepository() {
        this.dataFile = new File(System.getProperty("user.dir") + File.separator + "data" + File.separator + "currencies.txt");
    };
    
    public TextExchangeRateRepository(File file) {
        this.dataFile = file;
    };
    
    @Override
    public String getRateDate() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
            return reader.readLine().split(" ")[0];
        }
    }

    @Override
    public List<CurrencyExchangeRate> getExchangeRates() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
            String date = reader.readLine();
            String header = reader.readLine();
            List<CurrencyExchangeRate> exchangeRates = new ArrayList<>();
            String line;
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
            return exchangeRates;
        }
    }
    
}
