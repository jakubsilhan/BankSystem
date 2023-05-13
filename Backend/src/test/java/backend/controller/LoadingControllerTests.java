
package backend.controller;

import backend.services.CurrencyExchangeService;
import backend.services.JsonAccountService;
import backend.services.JsonUserService;
import backend.services.JwtService;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;

public class LoadingControllerTests {



    @Mock
    private JwtService jwtService;

    @Mock
    private JsonAccountService accountService;

    @Mock
    private JsonUserService userService;
    
    @Mock
    private CurrencyExchangeService exchangeService;

    @InjectMocks
    private LoadingController loadingController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetBalances() throws Exception {
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGV4YW1wbGUuY29tIiwiaWF0IjoxNTE2MjM5MDIyLCJleHAiOjE1MTYyNDA2MjJ9.-U6xqO3dq_XYDoYv7LrrbpW8OeZcAzbrf_6wvnp6UW8";
        String expected = "Testing";
        when(jwtService.getAccountNumber(token.substring(7))).thenReturn(123456L);
        when(accountService.balancesToString(123456L)).thenReturn(expected);
        
        String result = loadingController.getBalances(token);
        assertEquals(expected, result);
    }

    @Test
    void testGetMovements() throws Exception {
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGV4YW1wbGUuY29tIiwiaWF0IjoxNTE2MjM5MDIyLCJleHAiOjE1MTYyNDA2MjJ9.-U6xqO3dq_XYDoYv7LrrbpW8OeZcAzbrf_6wvnp6UW8";
        String expected = "Testing";
        when(jwtService.getAccountNumber(token.substring(7))).thenReturn(123456L);
        when(accountService.movementsToString(123456L)).thenReturn(expected);
        
        String result = loadingController.getMovements(token);
        assertEquals(expected, result);
    }

    @Test
    void testGetUserInfo() throws Exception {
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGV4YW1wbGUuY29tIiwiaWF0IjoxNTE2MjM5MDIyLCJleHAiOjE1MTYyNDA2MjJ9.-U6xqO3dq_XYDoYv7LrrbpW8OeZcAzbrf_6wvnp6UW8";
        String expected = "Testing";
        when(jwtService.getSubject(token.substring(7))).thenReturn("test@example.com");
        when(userService.userToString("test@example.com")).thenReturn(expected);
        
        String result = loadingController.getUserInfo(token);
        assertEquals(expected, result);
    }    
    
    @Test
    void testGetRateDate() throws Exception {
        String date = "21.2.2023";
        when(exchangeService.getRateDate()).thenReturn(date);        
        String result = loadingController.getRateDate();
        assertEquals(date, result);
        when(exchangeService.getRateDate()).thenThrow(IOException.class);
        result = loadingController.getRateDate();
        assertEquals("Nepodařilo se načíst datum", result);
    } 
}
