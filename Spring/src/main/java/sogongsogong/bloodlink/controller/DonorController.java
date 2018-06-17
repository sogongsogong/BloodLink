package sogongsogong.bloodlink.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sogongsogong.bloodlink.model.Donor;
import sogongsogong.bloodlink.model.User;
import sogongsogong.bloodlink.repository.DonorRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping("/donor")
public class DonorController extends UserController{

    @Autowired
    private DonorRepository donorRepository;

    /*@RequestMapping(value = "/register", method = POST)
    public boolean register(@RequestParam String account, @RequestParam String password, @RequestParam String name, @RequestParam String phone, @RequestParam Calendar birth, @RequestParam boolean sex, @RequestParam String rh, @RequestParam String abo) {
        return register(new Donor(account, password, name, phone, birth, sex, rh, abo));
    }*/

    @RequestMapping(value = "/register", method = POST)
    public boolean register(@RequestBody Donor donor) {
    //public boolean register(Donor donor) {
        boolean response = false;
        if(!check(donor.getAccount())) {
            donorRepository.save(donor);
            response = true;
        }
        return response;
    }

    @RequestMapping(value = "/check", method = GET)
    public boolean check(@RequestParam String account) {
        return donorRepository.existsByAccount(account);
    }

    @RequestMapping(value = "/login")
    public boolean login(@RequestParam String account, @RequestParam String password) {
        boolean response = false;
        List<Donor> donor = donorRepository.findByAccount(account);
        if(donor.size()==1) {
            response = login(donor.get(0), password);
        }
        return response;
    }

    @RequestMapping(value = "/search", method = GET)
    public String search(@RequestParam(required = false) String key, @RequestParam(required = false) String value) {
        List<Donor> list = null;
        if(key == null || value == null) {
            list = new ArrayList<>();
            Iterable<Donor> donors = donorRepository.findAll();
            donors.forEach(list::add);
        } else {
            if(key.equals("account")) {
                list = donorRepository.findByAccount(value);
            } else if(key.equals("name")) {
                list = donorRepository.findByName(value);
            } else if(key.equals("phone")) {
                list = donorRepository.findByPhone(value);
            }
        }

        return conceal(list);
    }

    @RequestMapping(value = "/{account}", method = GET)
    public Donor info(@PathVariable String account) {
        return (Donor)donorRepository.findByAccount(account).get(0);
    }

    @RequestMapping(value = "/{account}/point", method = PUT)
    public boolean update(@PathVariable String account, @RequestParam int point) {
        boolean response = false;
        if(donorRepository.existsByAccount(account)) {
            Donor donor = info(account);
            point += donor.getPoint();
            donor.setPoint(point);
            donorRepository.save(donor);
            response = true;
        }
        return response;
    }

    /*
    @RequestMapping(value = "/update/{account}", method = PUT)
    public void update(@PathVariable String account, @RequestParam String password, @RequestParam String phone, @RequestParam String name, @RequestParam Calendar birth, @RequestParam boolean sex, @RequestParam String rh, @RequestParam String abo) {
        Donor donor = donorRepository.findByAccount(account);
        donor.setPassword(password);
        donor.setPhone(phone);
        donor.setName(name);
        donor.setBirth(birth);
        donor.setSex(sex);
        donor.setRh(rh);
        donor.setAbo(abo);
        update(donor);
    }

    @RequestMapping(value = "/update/{account}", method = PUT)
    public void update(@RequestBody User donor) {
        donorRepository.save(donor);
    }

    @RequestMapping(value = "/update/{account}", method = PUT)
    public void update(@RequestParam String password) {
        Donor donor = donorRepository.findByAccount();
        donor.setPassword(password);
        donorRepository.save(donor);
    }
    */
}
