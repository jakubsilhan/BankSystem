
package backend.controller;

import backend.dto.PaymentDto;
import backend.services.AccountService;
import backend.services.CurrencyExchangeService;
import backend.services.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class PaymentController {
    @Autowired
    AccountService accountService;
    @Autowired
    JwtService jwtService;
    @Autowired
    CurrencyExchangeService exchangeService;
    
    @PostMapping("/payment/withdraw")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String Withdraw(@RequestHeader(name="Authorization") String token, @RequestBody PaymentDto payment){
        token = token.substring(7);
        long accountNumber = jwtService.getAccountNumber(token);
        return accountService.withdraw(accountNumber, payment);
    }
    
    @PostMapping("/payment/deposit")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String Deposit(@RequestHeader(name="Authorization") String token, @RequestBody PaymentDto payment){
        token = token.substring(7);
        long accountNumber = jwtService.getAccountNumber(token);
        return accountService.deposit(accountNumber, payment);
    }
//    
//    @PostMapping("/payment/currencies")
//    @PreAuthorize("hasRole('ROLE_USER')")
//    public String getCurrencies(){
//        ObjectMapper objectMapper = new ObjectMapper();
//        try{
//            return objectMapper.writeValueAsString(exchangeService.getExistingCurrencies());
//        }catch (IOException ex){
//            return "Nepodařilo se načíst měny";
//        }
//    }
    
}
