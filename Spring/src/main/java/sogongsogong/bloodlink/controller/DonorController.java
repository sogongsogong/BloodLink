package sogongsogong.bloodlink.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sogongsogong.bloodlink.model.Donor;
import sogongsogong.bloodlink.repository.DonorRepository;

import java.util.Calendar;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(path="/donor")
public class DonorController {

    @Autowired
    private DonorRepository donorRepository;

    @RequestMapping(path="/register")
    public String register(@RequestParam String account, @RequestParam String password, @RequestParam String phone, @RequestParam String name, @RequestParam Calendar birth, @RequestParam boolean sex, @RequestParam String rh, @RequestParam String abo) {
        donorRepository.save(new Donor(account, password, phone, name, birth, sex, rh, abo));
        return "Donor '" + account + "' registered!";
    }

    @RequestMapping(path = "/login")
    public boolean login(@RequestParam String account, @RequestParam String password) {
        Donor donor = donorRepository.findByAccount(account);
        boolean success = false;
        if(donorRepository.existsByAccount(account) && donor.getPassword().equals(password)) {
            success = true;
        }
        return success;
    }

    @RequestMapping(method = GET, path="/search")
    public Donor search(@RequestParam String account) {
        return donorRepository.findByAccount(account);
    }

    @RequestMapping(method = GET, path="/check")
    public boolean isExist(@RequestParam String account) {
        return donorRepository.existsByAccount(account);
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
