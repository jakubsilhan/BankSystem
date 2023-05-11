
package backend.services;

import backend.classes.Account;
import backend.classes.Balance;
import backend.classes.Movement;
import backend.dto.PaymentDto;
import backend.repositories.AccountRepository;
import backend.repositories.JsonAccountRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JsonAccountServiceTests {
    
    @Mock
    private JsonAccountRepository mockRepository;
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
        balances.add(new Balance(12345,"EURO","EUR",12.4));
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
        assertEquals("EUR",account.getBalances().get(0).getAbbreviation());

        long nonExistentAccountNumber = 67890;
        Account nonExistentAccount = accountService.findAccountByNumber(nonExistentAccountNumber);
        assertNull(nonExistentAccount);
    }

    @Test
    public void testWithdraw_withEnoughFunds() throws IOException {
        Account account = accountService.findAccountByNumber(11111);
        // Make a withdrawal of 500 CZK from the test account
        PaymentDto payment = new PaymentDto("EUR", 12.0);
        String result = accountService.withdraw(account.getAccountNumber(), payment);

        // Check that the withdrawal was successful and the account balance was updated
        assertEquals("Platba úspěšná", result);
        assertEquals(0.4, account.getBalances().get(0).getAmount(), 0.001);
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
