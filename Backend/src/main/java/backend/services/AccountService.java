
package backend.services;

import backend.classes.Account;
import backend.dto.PaymentDto;

public interface AccountService {

    public Account findAccountByNumber(long accountNumber);
    
    public String withdraw(long accountNumber, PaymentDto payment);
    public String deposit(long accountNumber, PaymentDto payment);
    
    public String balancesToString(long accountNumber);
    public String movementsToString(long accountNumber);
    
}
