
package backend.controller;

import backend.classes.User;
import backend.dto.AuthRequest;
import backend.services.JsonUserService;
import backend.services.JwtService;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.nio.file.AccessDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    @Autowired
    JsonUserService userService;
        
    @Autowired
    private JwtService jwtService;
    
    
    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_USER')")
    public long hello(@RequestHeader(name="Authorization") String token) throws JsonProcessingException {
        
        token = token.substring(7);
        return jwtService.getAccountNumber(token);
//        User user = userService.getUserByEmail(email);
//        ObjectMapper objectMapper = new ObjectMapper();
//        return objectMapper.writeValueAsString(user);   
    }    
    
    
    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) throws AccessDeniedException{
        User user = userService.getUserByEmail(authRequest.getUsername());
        if (user.getPassword().equals(authRequest.getPassword())){
            return jwtService.generateToken(user.getUsername(), user.getAccountNumber());
        }
        return user.getPassword()+" != " + authRequest.getPassword();
    }
}
