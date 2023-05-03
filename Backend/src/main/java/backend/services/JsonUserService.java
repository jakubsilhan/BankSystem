
package backend.services;

import backend.classes.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

@Service
public class JsonUserService implements UserService {
    
    private final List<User> users;
    private static final String DATADIRECTORY = System.getProperty("user.dir") + File.separator + "data";
    
    public JsonUserService() throws IOException {
        String jsonFilePath = DATADIRECTORY+ File.separator + "users.json";
        ObjectMapper objectMapper = new ObjectMapper();
        File file = ResourceUtils.getFile(jsonFilePath);
        users = objectMapper.readValue(file, new TypeReference<List<User>>(){});
    }
    
    @Override
    public List<User> getAllUsers() {
        return users;
    }

    @Override
    public User getUserByEmail(String email) {
        return users.stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }
        
}
