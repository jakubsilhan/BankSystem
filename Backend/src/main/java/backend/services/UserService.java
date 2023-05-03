
package backend.services;

import backend.classes.User;
import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserByEmail(String email);
//    User createUser(User user);
//    User updateUser(Long id, User user);
//    void deleteUser(Long id);
}
