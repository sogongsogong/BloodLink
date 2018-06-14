package sogongsogong.bloodlink.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sogongsogong.bloodlink.model.Donor;
import sogongsogong.bloodlink.repository.DonorRepository;

import java.util.Calendar;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(path="/donor")
public class DonorController extends UserController{

    @Autowired
    private DonorRepository donorRepository;

    @RequestMapping(path="/register")
    public boolean register(@RequestParam String account, @RequestParam String password, @RequestParam String confirm, @RequestParam String name, @RequestParam String phone, @RequestParam Calendar birth, @RequestParam boolean sex, @RequestParam String rh, @RequestParam String abo) {
        //donorRepository.save(new Donor(account, password, name, phone, birth, sex, rh, abo));
        return register(new Donor(account, password, name, phone, birth, sex, rh, abo), confirm);
    }

    public boolean register(Donor donor, String password) {
        boolean success = false;
        if(!donorRepository.existsByAccount(donor.getAccount())) {
            if(donor.getPassword().equals(password)) {
                donorRepository.save(donor);
                success = true;
            }
        }
        return success;
    }

    @RequestMapping(method = GET, path="/check")
    public boolean isExist(@RequestParam String account) {
        return donorRepository.existsByAccount(account);
    }

    @RequestMapping(path = "/login")
    public boolean login(@RequestParam String account, @RequestParam String password) {
        Donor donor = donorRepository.findByAccount(account);
        return login(donor, password);
    }

    @RequestMapping(method = GET, path = "/search")
    public String search(@RequestParam String account) {
        String result="";
        if(donorRepository.existsByAccount(account)) {
            Donor donor = donorRepository.findByAccount(account);
            result += donor.getAccount();
        }
        return result;
    }

    @RequestMapping(path="/update/info")
    public String update(@RequestParam String account, @RequestParam String password, @RequestParam String phone, @RequestParam String name, @RequestParam Calendar birth, @RequestParam boolean sex, @RequestParam String rh, @RequestParam String abo) {
        Donor donor = donorRepository.findByAccount(account);
        donor.setPassword(password);
        donor.setPhone(phone);
        donor.setName(name);
        donor.setBirth(birth);
        donor.setSex(sex);
        donor.setRh(rh);
        donor.setAbo(abo);
        donorRepository.save(donor);
        return "Donor '" + account + "' updated!";
    }

    @RequestMapping(path="/update/point")
    public String update(@RequestParam String account, @RequestParam int point) {
        Donor donor = donorRepository.findByAccount(account);
        point += donor.getPoint();
        donor.setPoint(point);
        donorRepository.save(donor);
        return "Donor '" + account + "' has "+ point + " points";
    }
}
