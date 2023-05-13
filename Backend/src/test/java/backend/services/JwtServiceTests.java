
package backend.services;

import org.junit.Before;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class JwtServiceTests {

    private String key = "70337336763979244226452948404D635166546A576E5A7234743777217A2543";
    
    @Mock
    Environment env;
    
    @InjectMocks
    private JwtService jwtService;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(env.getProperty("token.secret.key")).thenReturn(key);
        //jwtService.SECRET = env.getProperty("token.secret.key");
        jwtService.setSecret(env.getProperty("token.secret.key"));
        //jwtService = new JwtService();
    }
    
    @Test
    public void testGenerateToken() {
        String userName = "testUser";
        long accountNumber = 1234567890L;
        String token = jwtService.generateToken(userName, accountNumber);
        assertNotNull(token);
        assertTrue(token.length() > 0);
    }
    
    @Test
    public void testIsTokenValid() {
        String userName = "testUser";
        long accountNumber = 1234567890L;
        String token = jwtService.generateToken(userName, accountNumber);
        assertTrue(jwtService.isTokenValid(token));
        assertFalse(jwtService.isTokenValid("invalidToken"));
    }
    
    @Test
    public void testGetSubject() {
        String userName = "testUser";
        long accountNumber = 1234567890L;
        String token = jwtService.generateToken(userName, accountNumber);
        assertEquals(userName, jwtService.getSubject(token));
    }
    
    @Test
    public void testGetAccountNumber() {
        // Arrange
        String userName = "testUser";
        long accountNumber = 1234567890L;
        String token = jwtService.generateToken(userName, accountNumber);

        // Act
        long retrievedAccountNumber = jwtService.getAccountNumber(token);

        // Assert
        assertEquals(accountNumber, retrievedAccountNumber);
    }
}
