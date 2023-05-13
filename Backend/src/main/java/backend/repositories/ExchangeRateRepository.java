
package backend.repositories;

import backend.classes.CurrencyExchangeRate;
import java.io.IOException;
import java.util.List;

public interface ExchangeRateRepository {

    String getRateDate() throws IOException;

    List<CurrencyExchangeRate> getExchangeRates() throws IOException;

}
