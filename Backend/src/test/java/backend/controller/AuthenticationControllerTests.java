
package backend.controller;

import backend.classes.User;
import backend.dto.AuthRequest;
import backend.dto.VerifyRequest;
import backend.services.JsonUserService;
import backend.services.JwtService;
import backend.services.TwoFaAuth;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.BDDMockito.given;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class AuthenticationControllerTests {
            
    VerifyRequest request;
        
    @Mock
    private TwoFaAuth twoFaAuth;    

    @Mock
    JsonUserService userService;
    
    @Mock
    JwtService jwtService;
    
    @Mock
    private User mockUser;
    
    @InjectMocks
    AuthenticationController authController;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
        
    @Test
    public void testValidateValid() throws Exception {
        String email = "test@example.com";
        String code = "123456";
        String token = "validToken";

        request = new VerifyRequest(email, code);

        when(userService.getUserByEmail(email)).thenReturn(mockUser);
        when(twoFaAuth.validateCode(email, code)).thenReturn(true);
        when(jwtService.generateToken(mockUser.getEmail(), mockUser.getAccountNumber())).thenReturn(token);

        String result = authController.validate(request);

        assertEquals(token, result);
    }
    
    @Test
    public void testValidateInvalid() throws Exception {
        String email = "test@example.com";
        String code = "123456";
        String token = "validToken";

        request = new VerifyRequest(email, code);

        when(userService.getUserByEmail(email)).thenReturn(mockUser);
        when(twoFaAuth.validateCode(email, code)).thenReturn(false);
        when(jwtService.generateToken(mockUser.getEmail(), mockUser.getAccountNumber())).thenReturn(token);

        String result = authController.validate(request);

        assertEquals("Invalid code", result);
    }
    
    
    @Test
    public void testCheckDetailsValid() throws IOException {
        // Arrange
        String email = "test@example.com";
        String password = "password";
        
        User user = new User(1, "John Doe", email, password, 12345);
        
        given(userService.getUserByEmail(email)).willReturn(user);
        given(twoFaAuth.saveCode(email)).willReturn("1234");
        
        AuthRequest authRequest = new AuthRequest(email, password);
        
        // Act
        boolean result = authController.checkDetails(authRequest);
        
        // Assert
        assertTrue(result);
    }
    
    @Test
    public void testCheckDetailsInvalid() throws IOException {
        // Arrange
        String email = "test@example.com";
        String password = "password";
        
        User user = new User(1, "John Doe", email, "differentPassword", 12345);
        
        given(userService.getUserByEmail(email)).willReturn(user);
        given(twoFaAuth.saveCode(email)).willReturn("1234");
        
        AuthRequest authRequest = new AuthRequest(email, password);
        
        // Act
        boolean result = authController.checkDetails(authRequest);
        
        // Assert
        assertFalse(result);
    }
    
    @Test
    public void testCheckDetailsNonexistant() throws IOException {
        // Arrange
        String email = "test@example.com";
        String password = "password";
        
        User user = new User(1, "John Doe", email, password, 12345);
        
        given(userService.getUserByEmail(email)).willReturn(null);
        given(twoFaAuth.saveCode(email)).willReturn("1234");
        
        AuthRequest authRequest = new AuthRequest(email, password);
        
        // Act
        boolean result = authController.checkDetails(authRequest);
        
        // Assert
        assertFalse(result);
    }
}
