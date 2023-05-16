
package backend.services;

import backend.classes.EmailCode;
import backend.repositories.TextExchangeRateRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TwoFaAuthTests {

    private static File testFile = new File(System.getProperty("user.dir") + File.separator + "data" + File.separator + "test.json");
    private TwoFaAuth twoFaAuth;
    
    @BeforeEach
    public void setUp() throws IOException {
        // Create a test data file
        testFile.createNewFile();
        FileWriter writer = new FileWriter(testFile);
        writer.write("[ {\n");
        writer.write("  \"email\" : \"john.doe1@gmail.com\",\n");
        writer.write("  \"code\" : \"1234\"\n");
        writer.write("}, {\n");
        writer.write("  \"email\" : \"john.doe2@gmail.com\",\n");
        writer.write("  \"code\" : \"9968\"\n");
        writer.write("} ]\n");
        writer.close();

        twoFaAuth = new TwoFaAuth(testFile);
    }
    
    @AfterAll
    public static void cleanUp(){
        testFile.delete();
    }
    
    @Test
    void testGenerateCode() {
        String code = twoFaAuth.generateCode();
        assertTrue(code.matches("\\d{4}"));
    }
    
    @Test
    void testValidateCode() throws IOException {
        // Test that a valid code is validated successfully
        assertTrue(twoFaAuth.validateCode("john.doe1@gmail.com", "1234"));
        // Test that an invalid code is not validated
        assertFalse(twoFaAuth.validateCode("john.doe2@example.com", "1234"));
    }
    
    @Test
    void testSaveCode() throws IOException {
        String email1 = "john.doe1@gmail.com";
        String email2 = "jane.doe@gmail.com";
        // Call the method being tested
        String savedCode1 = twoFaAuth.saveCode(email1);
        String savedCode2 = twoFaAuth.saveCode(email2);

        // Read the data file and verify that the code was saved correctly
        List<EmailCode> codes = readDataFile();
        assertEquals(3, codes.size());
        assertEquals(email1, codes.get(0).getEmail());
        assertEquals(savedCode1, codes.get(0).getCode());
        // Check new generated user and code
        assertEquals(email2, codes.get(2).getEmail());
        assertEquals(savedCode2, codes.get(2).getCode());
    }
    
    private List<EmailCode> readDataFile() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(testFile, new TypeReference<List<EmailCode>>(){});
    }
    
    
    
}
