
package backend.services;

import backend.classes.Account;
import backend.classes.Balance;
import backend.classes.Movement;
import backend.dto.PaymentDto;
import backend.repositories.AccountRepository;
import backend.repositories.JsonAccountRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class JsonAccountService implements AccountService{
    
    CurrencyExchangeService exchangeService = new CurrencyExchangeService();
    private List<Account> accounts;
    ObjectMapper objectMapper = new ObjectMapper();
    AccountRepository repository = new JsonAccountRepository();
    
    public JsonAccountService() throws IOException {
        accounts = repository.loadAccounts();
    }
    
    @Override
    public Account findAccountByNumber(long accountNumber){
        return accounts.stream()
                .filter(a -> a.getAccountNumber() == accountNumber)
                .findFirst()
                .orElse(null);
    }
    
    @Override
    public String withdraw(long accountNumber, PaymentDto payment){
        Account account = findAccountByNumber(accountNumber);
        Balance balance = account.getBalances().stream()
                .filter(b -> b.getAbbreviation().equals(payment.getCurrencyAbbreviation()))
                .findFirst()
                .orElse(null);
        if(balance==null || !(balance.getAbbreviation().equals(payment.getCurrencyAbbreviation())) && balance.getAmount()<payment.getAmmount()){            
            try {
                double exchanged = exchangeService.ConvertToCZK(payment.getCurrencyAbbreviation(), payment.getAmmount());
                balance = account.getBalances().stream()
                    .filter(b -> b.getAbbreviation().equals("CZK"))
                    .findFirst()
                    .orElse(null);
                if(balance.getAmount()<exchanged){
                    return "Nedostatek prostředků";
                }
                payment.setCurrencyAbbreviation("CZK");
                payment.setAmmount(exchanged);
            } catch (IOException ex) {
                return "Nepovedený přístup k databázi měn";
            }
        }
        if(balance.getAmount()<payment.getAmmount()){
            return "Nedostatek prostředků";
        }
        payment.setAmmount(roundDouble(payment.getAmmount()));
        balance.setAmount(balance.getAmount()-payment.getAmmount());
        // Add a new movement for the deposit
        LocalDate date = LocalDate.now();        
        account.addMovement(new Movement("- "+payment.getAmmount()+" "+payment.getCurrencyAbbreviation(),date.format(DateTimeFormatter.ISO_DATE)));
        try{
            repository.saveAccounts(accounts);
            return "Platba úspěšná";
        }catch (IOException e){
            return "Nepovedený přístup k databázi účtů";
        }
    }
    
    @Override
    public String deposit(long accountNumber, PaymentDto payment){
        Account account = findAccountByNumber(accountNumber);
        Balance balance = account.getBalances().stream()
                .filter(b -> b.getAbbreviation().equals(payment.getCurrencyAbbreviation()))
                .findFirst()
                .orElse(null);
        if(balance == null){
            try {
                double exchanged = exchangeService.ConvertToCZK(payment.getCurrencyAbbreviation(), payment.getAmmount());
                balance = account.getBalances().stream()
                    .filter(b -> b.getAbbreviation().equals("CZK"))
                    .findFirst()
                    .orElse(null);
                payment.setCurrencyAbbreviation("CZK");
                payment.setAmmount(exchanged);
            } catch (IOException ex) {
                return "Nepovedený přístup k databázi měn";
            }
        }
        payment.setAmmount(roundDouble(payment.getAmmount()));
        balance.setAmount(balance.getAmount()+payment.getAmmount());
        // Add a new movement for the deposit
        LocalDate date = LocalDate.now();        
        account.addMovement(new Movement("+ "+payment.getAmmount()+" "+payment.getCurrencyAbbreviation(),date.format(DateTimeFormatter.ISO_DATE)));
        try{
            repository.saveAccounts(accounts);
            return "Vklad úspěšný";
        }catch (IOException e){
            return "Nepovedený přístup k databázi účtů";
        }    
    }
    
    @Override
    public String balancesToString(long accountNumber){
        Account account = findAccountByNumber(accountNumber);
        try{
            String balance = objectMapper.writeValueAsString(account.getBalances());
            return balance;
        }catch (JsonProcessingException e){
            return "Cannot load current balances";
        }
    }
    
    @Override
    public String movementsToString(long accountNumber){
        Account account = findAccountByNumber(accountNumber);
        try{
            String movement = objectMapper.writeValueAsString(account.getMovements());
            return movement;
        }catch(JsonProcessingException e){
            return "Cannot load current movements";
        }
    }
    
    private double roundDouble(double number){
        return Math.round(number * 100.0) / 100.0;
    }
}
