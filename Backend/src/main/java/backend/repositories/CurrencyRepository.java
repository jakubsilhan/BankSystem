
package backend.repositories;

import backend.classes.Currency;
import java.io.IOException;
import java.util.List;

public interface CurrencyRepository {
        List<Currency> loadAccounts() throws IOException;
    void saveAccounts(List<Currency> currencies) throws IOException;
}
