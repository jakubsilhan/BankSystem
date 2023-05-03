
package backend.controller;

import backend.classes.User;
import backend.dto.AuthRequest;
import backend.services.JsonUserService;
import backend.services.JwtService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.AccessDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    @Autowired
    JsonUserService userService;
        
    @Autowired
    private JwtService jwtService;
    
    @RequestMapping("/")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String hello() throws JsonProcessingException {
//        User user = userService.getUserByEmail(email);
//        ObjectMapper objectMapper = new ObjectMapper();
//        return objectMapper.writeValueAsString(user);      
            return "pomoc";
    }    
    
    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) throws AccessDeniedException{
        User user = userService.getUserByEmail(authRequest.getUsername());
        if (user.getPassword().equals(authRequest.getPassword())){
            return jwtService.generateToken(authRequest.getUsername());
        }
        return user.getPassword()+" != " + authRequest.getPassword();
    }
}
