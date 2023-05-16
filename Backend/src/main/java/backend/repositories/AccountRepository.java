
package backend.repositories;

import backend.classes.Account;
import java.io.IOException;
import java.util.List;

public interface AccountRepository {
    List<Account> loadAccounts() throws IOException;
    void saveAccounts(List<Account> accounts) throws IOException;
}
