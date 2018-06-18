package sogongsogong.bloodlink.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sogongsogong.bloodlink.model.BDC;
import sogongsogong.bloodlink.model.Donor;
import sogongsogong.bloodlink.repository.DonorRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/donor")
//public class DonorController extends UserController {
public class DonorController {

    @Autowired
    private DonorRepository donorRepository;

    @RequestMapping(value = "/register")
    public boolean register(@RequestParam String account, @RequestParam String password, @RequestParam String name, @RequestParam String phone, @RequestParam String birth, @RequestParam boolean sex, @RequestParam String rh, @RequestParam String abo) {
        return register(new Donor(account, password, name, phone, birth, sex, rh, abo));
    }

    //@RequestMapping(value = "/register")
    public boolean register(@RequestBody Donor donor) {
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
        Donor donor = info(account);
        if(donor != null) {
            response = donor.getPassword().equals(password);
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
                list = new ArrayList<>();
                list.add(donorRepository.findByAccount(value));
            } else if(key.equals("name")) {
                list = donorRepository.findByName(value);
            } else if(key.equals("phone")) {
                list = donorRepository.findByPhone(value);
            }
        }
        StringBuffer buffer = new StringBuffer();
        if(list != null) {
            Iterator iterator = list.iterator();
            while(iterator.hasNext()) {
                buffer.append(iterator.next().toString());
            }
        }
        return buffer.toString();
        //return conceal(list);
    }

    @RequestMapping(value = "/{account}", method = GET)
    public Donor info(@PathVariable String account) {
        return donorRepository.findByAccount(account);
    }

/*
    @RequestMapping(value = "/{account}/bdcs")
    public List<BDC> read(@PathVariable String account) {
        Donor donor = info(account);
        Iterator<BDC> iterator = donor.getBdc().iterator();
        List<BDC> list = new ArrayList<>();
        while(iterator.hasNext()) {
            list.add(iterator.next());
        }
        return list;
    }
*/

    /*@RequestMapping(value = "/{account}/point")
    public boolean update(@PathVariable String account, @RequestParam int point) {
        boolean response = false;
        Donor donor = info(account);
        if(donor != null) {
            point += donor.getPoint();
            donor.setPoint(point);
            donorRepository.save(donor);
            response = true;
        }
        return response;
    }*/



    /*
    @RequestMapping(value = "/update/{account}", method = PUT)
    public void update(@PathVariable String account, @RequestParam String password, @RequestParam String phone, @RequestParam String name, @RequestParam String birth, @RequestParam boolean sex, @RequestParam String rh, @RequestParam String abo) {
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
