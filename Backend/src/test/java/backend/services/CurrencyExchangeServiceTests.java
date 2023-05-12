
package backend.services;

import backend.classes.CurrencyExchangeRate;
import backend.repositories.TextExchangeRateRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

public class CurrencyExchangeServiceTests {
    
    @Mock
    TextExchangeRateRepository mockRepository;
    @InjectMocks
    CurrencyExchangeService mockService;
    
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void getRateDateTest() throws IOException{
        String expectedDate = "2022-05-11";

        // Use Mockito to mock the behavior of the repository method
        when(mockRepository.getRateDate()).thenReturn(expectedDate);

        // Call the service method and assert that it returns the expected date
        String actualDate = mockService.getRateDate();
        assertEquals(expectedDate, actualDate);
    }
    
    @Test
    void testConvertToCZK() throws IOException {
        // Create a mock exchange rate repository

        // Create test data for the mock repository
        List<CurrencyExchangeRate> rates = new ArrayList<>();
        rates.add(new CurrencyExchangeRate("Country1", "Currency1", 1, "CUR1", 2.0));
        rates.add(new CurrencyExchangeRate("Country2", "Currency2", 1, "CUR2", 3.0));
        when(mockRepository.getExchangeRates()).thenReturn(rates);

        // Call the method to test
        double result = mockService.ConvertToCZK("CUR2", 10.0);
        // Assert the result
        assertEquals(30.0, result, 0.001);
        result = mockService.ConvertToCZK("CUR1", 10);
        assertEquals(20, result);
    }
    
}
