
package backend.controller;

import backend.classes.User;
import backend.dto.AuthRequest;
import backend.dto.VerifyRequest;
import backend.services.JsonUserService;
import backend.services.JwtService;
import backend.services.TwoFaAuth;
import com.fasterxml.jackson.core.JsonProcessingException;
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
public class AuthenticationController {
    @Autowired
    JsonUserService userService;
        
    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private TwoFaAuth twoFaAuth;
    
        
    @PostMapping("/authentication/validate")
    public String validate(@RequestBody VerifyRequest verifyRequest) throws IOException{
        User user = userService.getUserByEmail(verifyRequest.getEmail());
        if(twoFaAuth.validateCode(verifyRequest.getEmail(), verifyRequest.getCode())){
            return jwtService.generateToken(user.getEmail(), user.getAccountNumber());
        }
        return "Invalid code";
    }
    
    
    @PostMapping("/authentication/check")
    public boolean checkDetails(@RequestBody AuthRequest authRequest) throws IOException{
        User user = userService.getUserByEmail(authRequest.getEmail());
        if (user.getPassword().equals(authRequest.getPassword())){
            twoFaAuth.saveCode(authRequest.getEmail());
            //send mail
            return true;
        }
        return false;
    }
}
