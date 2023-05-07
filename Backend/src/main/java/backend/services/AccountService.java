
package backend.services;

import backend.classes.Account;
import backend.dto.PaymentDto;
import java.io.IOException;

public interface AccountService {

    public Account findAccountByNumber(long accountNumber);
    
    public void withdraw(long accountNumber, PaymentDto payment);
    public void deposit(long accountNumber, PaymentDto payment);
    
    public String balancesToString(long accountNumber);
    public String movementsToString(long accountNumber);
    
}
