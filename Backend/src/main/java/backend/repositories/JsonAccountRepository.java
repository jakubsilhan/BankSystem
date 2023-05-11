
package backend.repositories;

import backend.classes.Account;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class JsonAccountRepository implements AccountRepository {
    
    private final String jsonFilePath = System.getProperty("user.dir") + File.separator + "data" + File.separator + "accounts.json";
    private final ObjectMapper mapper = new ObjectMapper();
    
    public JsonAccountRepository(){};

    @Override
    public List<Account> loadAccounts() throws IOException {
        return mapper.readValue(new File(jsonFilePath), new TypeReference<List<Account>>(){});
    }

    @Override
    public void saveAccounts(List<Account> accounts) throws IOException {
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(new File(jsonFilePath), accounts);
    }
    
}
