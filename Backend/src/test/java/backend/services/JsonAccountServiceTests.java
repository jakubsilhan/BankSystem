
package backend.services;

import backend.classes.Account;
import backend.classes.Balance;
import backend.classes.Movement;
import backend.dto.PaymentDto;
import backend.repositories.AccountRepository;
import backend.repositories.JsonAccountRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyList;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JsonAccountServiceTests {
    
    @Mock
    private JsonAccountRepository mockRepository;
    @Mock
    private CurrencyExchangeService exchangeService;
    @Mock
    private ObjectMapper objectMapper;
    @InjectMocks
    private JsonAccountService accountService;
    
    private List<Account> testAccounts;
    
    @BeforeEach
    public void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        //List<Account> testAccounts;
        testAccounts = new ArrayList<Account>();
        ArrayList<Balance> balances = new ArrayList<Balance>();
        ArrayList<Movement> movements = new ArrayList<Movement>();
        balances.add(new Balance(12345,"CZK","CZK",1500));
        movements.add(new Movement("12.4 CZK", "den"));
        testAccounts.add(new Account(11111, balances, movements));
        when(mockRepository.loadAccounts()).thenReturn(testAccounts);
    }

    @Test
    public void testFindAccountByNumber() throws IOException {
        long accountNumber = 11111;
        Account account = accountService.findAccountByNumber(accountNumber);
        assertNotNull(account);
        assertEquals(accountNumber, account.getAccountNumber());
        assertEquals("CZK",account.getBalances().get(0).getAbbreviation());

        long nonExistentAccountNumber = 67890;
        Account nonExistentAccount = accountService.findAccountByNumber(nonExistentAccountNumber);
        assertNull(nonExistentAccount);
    }

    @Test
    public void testWithdrawEnoughFunds() throws IOException {
        Account account = accountService.findAccountByNumber(11111);
        // Make a withdrawal of 500 CZK from the test account
        PaymentDto payment = new PaymentDto("CZK", 12.0);
        String result = accountService.withdraw(account.getAccountNumber(), payment);

        // Check that the withdrawal was successful and the account balance was updated
        assertEquals("Platba úspěšná", result);
        assertEquals(1488, account.getBalances().get(0).getAmount(), 0.001);
        assertEquals("- 12.0 CZK", account.getMovements().get(1).getAmount());
        verify(mockRepository, times(1)).saveAccounts(anyList());
    }
    
    @Test
    public void testWithdrawCurrencyExchange() throws IOException {
        PaymentDto payment = new PaymentDto("USD", 50.0);
        when(exchangeService.ConvertToCZK("USD", 50.0)).thenReturn(1100.0);
        Account account = accountService.findAccountByNumber(11111);
        String result = accountService.withdraw(11111, payment);
        
        assertEquals("Platba úspěšná", result);
        assertEquals(400.0, account.getBalances().get(0).getAmount(), 0.001);
        assertEquals("- 1100.0 CZK", account.getMovements().get(1).getAmount());
        verify(mockRepository, times(1)).saveAccounts(anyList());
    }
    
        @Test
    public void testWithdrawInsufficientFunds() throws IOException {
        Account account = accountService.findAccountByNumber(11111);
        PaymentDto payment = new PaymentDto("CZK", 2000);
                
        String result = accountService.withdraw(account.getAccountNumber(), payment);
        assertEquals("Nedostatek prostředků", result);
        assertEquals(1500.0, account.getBalances().get(0).getAmount(), 0.001);
        assertEquals(1, account.getMovements().size());
        verify(mockRepository, never()).saveAccounts(anyList());
    }
    
        @Test
    public void testWithdrawCurrencyExchangeInsufficient() throws IOException {
        PaymentDto payment = new PaymentDto("USD", 100.0);
        when(exchangeService.ConvertToCZK("USD", 100.0)).thenReturn(2200.0);
        Account account = accountService.findAccountByNumber(11111);
        String result = accountService.withdraw(11111, payment);
        
        assertEquals("Nedostatek prostředků", result);
        assertEquals(1500.0, account.getBalances().get(0).getAmount(), 0.001);
        assertEquals(1, account.getMovements().size());
        verify(mockRepository, never()).saveAccounts(anyList());
    }
    
    @Test
    public void testDepositExistingCurrency() throws IOException {
        Account account = accountService.findAccountByNumber(11111);
        // Make a withdrawal of 500 CZK from the test account
        PaymentDto payment = new PaymentDto("CZK", 12.0);
        String result = accountService.deposit(account.getAccountNumber(), payment);

        // Check that the withdrawal was successful and the account balance was updated
        assertEquals("Vklad úspěšný", result);
        assertEquals(1512, account.getBalances().get(0).getAmount(), 0.001);
        assertEquals("+ 12.0 CZK", account.getMovements().get(1).getAmount());
    }
    
    @Test
    public void testDepositCurrencyExchange() throws IOException {
        PaymentDto payment = new PaymentDto("USD", 50.0);
        when(exchangeService.ConvertToCZK("USD", 50.0)).thenReturn(1100.0);
        Account account = accountService.findAccountByNumber(11111);
        String result = accountService.deposit(11111, payment);
        
        assertEquals("Vklad úspěšný", result);
        assertEquals(2600.0, account.getBalances().get(0).getAmount(), 0.001);
        assertEquals("+ 1100.0 CZK", account.getMovements().get(1).getAmount());
        verify(mockRepository, times(1)).saveAccounts(anyList());
    }
    
    @Test
    public void testBalancesToString() throws JsonProcessingException{
        Account account = accountService.findAccountByNumber(11111);
        when(objectMapper.writeValueAsString(account.getBalances())).thenReturn("funguje");
        String result = accountService.balancesToString(11111);
        assertEquals("funguje",result);
    }
    
    @Test
    public void testMovementsToString() throws JsonProcessingException{
        Account account = accountService.findAccountByNumber(11111);
        when(objectMapper.writeValueAsString(account.getMovements())).thenReturn("funguje");
        String result = accountService.movementsToString(11111);
        assertEquals("funguje",result);
    }
    
    @Test
    public void testRoundDouble() {
        double input1 = 1.234567;
        double expectedOutput1 = 1.23;
        double actualOutput1 = accountService.roundDouble(input1);
        assertEquals(expectedOutput1, actualOutput1);

        double input2 = 0.0;
        double expectedOutput2 = 0.0;
        double actualOutput2 = accountService.roundDouble(input2);
        assertEquals(expectedOutput2, actualOutput2);

        double input3 = -2.3456;
        double expectedOutput3 = -2.35;
        double actualOutput3 = accountService.roundDouble(input3);
        assertEquals(expectedOutput3, actualOutput3);
    }
    
}
