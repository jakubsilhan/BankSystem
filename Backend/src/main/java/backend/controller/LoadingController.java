
package backend.controller;

import backend.services.AccountService;
import backend.services.JsonAccountService;
import backend.services.JsonUserService;
import backend.services.JwtService;
import backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoadingController {
    @Autowired
    JsonAccountService accountService;
    @Autowired
    JwtService jwtService;
    @Autowired
    JsonUserService userService;
    
    @PostMapping("/loading/balances")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String getBalances(@RequestHeader(name="Authorization") String token){
        token = token.substring(7);
        long accountNumber = jwtService.getAccountNumber(token);
        return accountService.balancesToString(accountNumber);
    }
    
    @PostMapping("/loading/movements")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String getMovements(@RequestHeader(name="Authorization") String token){
        token = token.substring(7);
        long accountNumber = jwtService.getAccountNumber(token);
        return accountService.movementsToString(accountNumber);
    }
    
    @PostMapping("/loading/user")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String getUserInfo(@RequestHeader(name="Authorization") String token){
        token = token.substring(7);
        String email = jwtService.getSubject(token);
        return userService.userToString(email);
    }
    
    
}
