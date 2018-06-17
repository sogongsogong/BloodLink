package sogongsogong.bloodlink.controller;

import sogongsogong.bloodlink.model.User;
import sogongsogong.bloodlink.repository.UserRepository;

import java.util.Iterator;
import java.util.List;


public abstract class UserController {

    private UserRepository userRepository;

    abstract public boolean check(String check) ;
    public boolean login(User user, String password) {
        return user.getPassword().equals(password);
    }
    abstract public String search(String key, String value);
    public String conceal(List list) {
        StringBuffer buffer = new StringBuffer();
        if(list != null) {
            Iterator iterator = list.iterator();
            while(iterator.hasNext()) {
                buffer.append(iterator.next().toString());
            }
        }
        return buffer.toString();
    }
    //abstract public void update(User user);
    //abstract public void update(String user, String password);
}
