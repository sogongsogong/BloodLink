package sogongsogong.bloodlink.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sogongsogong.bloodlink.model.User;
import sogongsogong.bloodlink.repository.UserRepository;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(path="/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    private final AtomicInteger counter = new AtomicInteger();

    @RequestMapping(path="/register")
    public String register(@RequestParam String email, @RequestParam String password, @RequestParam String name, @RequestParam Calendar birth, @RequestParam boolean sex, @RequestParam String phone) {
        userRepository.save(new User(counter.incrementAndGet(), email, password, name, birth, sex, phone));
        return "User '" + email + "' registered!";
    }

    @RequestMapping(method = GET, path="/search")
    public List<User> search(String email) {
        return userRepository.findByEmail(email);
    }
}
