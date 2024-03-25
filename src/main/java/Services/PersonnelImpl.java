package Services;

import thirdparty.entities.User;

import java.util.HashMap;
import java.util.Map;

public class PersonnelImpl implements Personnel{

    private static Map<String, User> existingUsers = new HashMap<String, User>();
    /**
     * Uses credentials to
     * findout is user exists and
     * credentials are correct
     * @param userName
     * @param password
     * @return
     */

    static {
        populatePersonnelList();
    }
    public boolean arecredentialsValid(String userName, String password){
        if (existingUsers.containsKey(userName)) {
            User user = existingUsers.get(userName);
            if(password.equals(user.getPassword())){
                return true;
            }
        }
        return false;

    }
    private static void populatePersonnelList(){
        User user = new User(1, "Primary", "administrator","admin","Password123");
        existingUsers.put(user.getUserName(), user);

    }


}
