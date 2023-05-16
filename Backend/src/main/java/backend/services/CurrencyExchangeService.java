
package backend.services;

import backend.classes.CurrencyExchangeRate;
import backend.repositories.ExchangeRateRepository;
import backend.repositories.TextExchangeRateRepository;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CurrencyExchangeService {
    
    @Autowired
    TextExchangeRateRepository exchangeRateRepository;
    
    public String getRateDate() throws IOException{
        return exchangeRateRepository.getRateDate();        
    }
    
    public List<CurrencyExchangeRate> getExchangeRates() throws IOException {
        return exchangeRateRepository.getExchangeRates();
    }
            
    public double ConvertToCZK(String from, double ammount) throws IOException{
        List<CurrencyExchangeRate> rates = getExchangeRates();
        CurrencyExchangeRate rateTo = rates.stream()
                .filter(r -> r.getAbbreviation().equals(from))
                .findFirst()
                .orElse(null);
        return rateTo.getRate()*ammount;       
        
    }
    
    
}
