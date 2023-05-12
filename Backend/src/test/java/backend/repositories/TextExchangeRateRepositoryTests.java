
package backend.repositories;

import backend.classes.CurrencyExchangeRate;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

public class TextExchangeRateRepositoryTests {
    
    private static File testFile = new File("test.txt");
    private TextExchangeRateRepository repository;

    @BeforeEach
    public void setUp() throws IOException {
        // Create a test data file
        testFile.createNewFile();
        FileWriter writer = new FileWriter(testFile);
        writer.write("2022-05-11 #88\n");
        writer.write("Country|Currency|Amount|Abbreviation|Rate\n");
        writer.write("USA|Dollar|1|USD|1.0\n");
        writer.write("Germany|Euro|1|EUR|0.85\n");
        writer.close();

        repository = new TextExchangeRateRepository(testFile);
    }
    
    @AfterAll
    public static void cleanUp(){
        testFile.delete();
    }

    @Test
    public void getRateDateTest() throws IOException{
        String actualDate = repository.getRateDate();
        assertEquals("2022-05-11", actualDate);
    }
    
    @Test
    public void getExchangeRatesTest() throws IOException{
        List<CurrencyExchangeRate> expected = new ArrayList<>();
        expected.add(new CurrencyExchangeRate("USA","Dollar",1,"USD",1.0));
        expected.add(new CurrencyExchangeRate("Germany","Euro",1,"EUR",0.85));
        List<CurrencyExchangeRate> actual = repository.getExchangeRates();
        //First currency
        assertEquals(expected.get(0).getCountry(), actual.get(0).getCountry());
        assertEquals(expected.get(0).getCurrency(), actual.get(0).getCurrency());
        assertEquals(expected.get(0).getAbbreviation(), actual.get(0).getAbbreviation());
        assertEquals(expected.get(0).getRate(), actual.get(0).getRate());
        //Second currency
        assertEquals(expected.get(1).getCountry(), actual.get(1).getCountry());
        assertEquals(expected.get(1).getCurrency(), actual.get(1).getCurrency());
        assertEquals(expected.get(1).getAbbreviation(), actual.get(1).getAbbreviation());
        assertEquals(expected.get(1).getRate(), actual.get(1).getRate());
        
    }
        
}
