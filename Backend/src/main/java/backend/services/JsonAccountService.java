
package backend.services;

import backend.classes.Account;
import backend.classes.Balance;
import backend.classes.Movement;
import backend.dto.PaymentDto;
import backend.repositories.AccountRepository;
import backend.repositories.JsonAccountRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void withdraw(long accountNumber, PaymentDto payment){
        Account account = findAccountByNumber(accountNumber);
        Balance balance = account.getBalances().stream()
                .filter(b -> b.getAbbreviation().equals(payment.getCurrencyAbbreviation()))
                .findFirst()
                .orElse(null);
        if(balance==null || balance.getAmount()<payment.getAmmount()){            
            try {
                int exchanged = exchangeService.ConvertToCZK(payment.getCurrencyAbbreviation(), payment.getAmmount());
                balance = account.getBalances().stream()
                    .filter(b -> b.getAbbreviation().equals("CZK"))
                    .findFirst()
                    .orElse(null);
                if(balance.getAmount()<exchanged){
                    throw new IllegalArgumentException("Nedostatek prostredku");
                }
                payment.setCurrencyAbbreviation("CZK");
                payment.setAmmount(exchanged);
            } catch (IOException ex) {
                Logger.getLogger(JsonAccountService.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
        }
        balance.setAmount(balance.getAmount()-payment.getAmmount());
        // Add a new movement for the deposit
        LocalDate date = LocalDate.now();        
        account.addMovement(new Movement("- "+payment.getAmmount()+" "+payment.getCurrencyAbbreviation(),date.format(DateTimeFormatter.ISO_DATE)));
        try{
            repository.saveAccounts(accounts);
        }catch (Exception e){
            
        }
    }
    
    @Override
    public void deposit(long accountNumber, PaymentDto payment){
        Account account = findAccountByNumber(accountNumber);
        Balance balance = account.getBalances().stream()
                .filter(b -> b.getAbbreviation().equals(payment.getCurrencyAbbreviation()))
                .findFirst()
                .orElse(null);
        if(balance == null){
            try {
                int exchanged = exchangeService.ConvertToCZK(payment.getCurrencyAbbreviation(), payment.getAmmount());
                balance = account.getBalances().stream()
                    .filter(b -> b.getAbbreviation().equals("CZK"))
                    .findFirst()
                    .orElse(null);
                payment.setCurrencyAbbreviation("CZK");
                payment.setAmmount(exchanged);
            } catch (IOException ex) {
                Logger.getLogger(JsonAccountService.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
        }
        balance.setAmount(balance.getAmount()+payment.getAmmount());
        // Add a new movement for the deposit
        LocalDate date = LocalDate.now();        
        account.addMovement(new Movement("+ "+payment.getAmmount()+" "+payment.getCurrencyAbbreviation(),date.format(DateTimeFormatter.ISO_DATE)));
        try{
            repository.saveAccounts(accounts);
        }catch (Exception e){
            
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
    
    public static void main(String[] args) throws IOException {
        JsonAccountService jsonService = new JsonAccountService();
        PaymentDto payment = new PaymentDto("BRL", 1);
        jsonService.withdraw(12345, payment);
    }
}
