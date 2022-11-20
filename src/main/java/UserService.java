import models.User;

import java.util.List;
import java.util.Objects;

public class UserService {
    private int counter;
    UserService(){
        counter = 1;
    }
    public static User getUser(String bankName, String userName, List<User> users){
        for(User user : users){
            if(Objects.equals(user.getName(), userName) && Objects.equals(user.getBankName(), bankName)){
                return user;
            }
        }
        return null;
    }
    public User createOrgetUser(String bankName, String userName, List<User> users){
        User existingUser = getUser(bankName, userName, users);
        if(existingUser!=null){
            return existingUser;
        }
        User newUser = new User(counter++, userName, bankName);
        users.add(newUser);
        return newUser;
    }
}
