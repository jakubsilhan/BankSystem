
package backend.controller;

import backend.dto.PaymentDto;
import backend.services.AccountService;
import backend.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {
    @Autowired
    AccountService accountService;
    @Autowired
    JwtService jwtService;
    
    @PostMapping("/payment/withdraw")
    @PreAuthorize("hasRole('ROLE_USER')")
    public void Withdraw(@RequestHeader(name="Authorization") String token, @RequestBody PaymentDto payment){
        token = token.substring(7);
        long accountNumber = jwtService.getAccountNumber(token);
        accountService.withdraw(accountNumber, payment);
    }
    
    @PostMapping("/payment/deposit")
    @PreAuthorize("hasRole('ROLE_USER')")
    public void Deposit(@RequestHeader(name="Authorization") String token, @RequestBody PaymentDto payment){
        token = token.substring(7);
        long accountNumber = jwtService.getAccountNumber(token);
        accountService.deposit(accountNumber, payment);
    }
    
}
