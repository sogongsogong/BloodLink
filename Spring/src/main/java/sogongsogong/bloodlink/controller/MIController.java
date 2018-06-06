package sogongsogong.bloodlink.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sogongsogong.bloodlink.model.MI;
import sogongsogong.bloodlink.repository.MIRepository;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(path="/mi")
public class MIController {

    @Autowired
    private MIRepository miRepository;

    @RequestMapping(path = "/login")
    public boolean login(@RequestParam String account, @RequestParam String password) {
        MI mi = miRepository.findByAccount(account);
        boolean success = false;
        if(miRepository.existsByAccount(account) && mi.getPassword().equals(password)) {
            success = true;
        }
        return success;
    }

    @RequestMapping(method = GET, path="/search")
    public MI search(@RequestParam String account) {
        return miRepository.findByAccount(account);
    }



}
