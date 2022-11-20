import Models.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TestUserService {
    List<User> users;
    UserService userService;
    public TestUserService(){
        users = new ArrayList<>();
        userService = new UserService();
    }
    @Test
    public void testcreateOrgetUser(){
        userService.createOrgetUser("IDIDI", "QW", users);
        assert users.size() == 1;
        userService.createOrgetUser("IDIDI", "QWE", users);
        assert users.size() == 2;
        userService.createOrgetUser("IDIDI", "QW", users);
        assert users.size() == 2;
    }
}
