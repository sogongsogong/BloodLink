package sogongsogong.bloodlink.controller;

import sogongsogong.bloodlink.model.User;


public abstract class UserController {

    public boolean login(User user, String password) {
        return user.getPassword().equals(password);
    }
    
    abstract public String search(String account);

}
