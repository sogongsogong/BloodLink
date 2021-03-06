package sogongsogong.bloodlink.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sogongsogong.bloodlink.model.BDC;
import sogongsogong.bloodlink.model.Donor;
import sogongsogong.bloodlink.model.MI;
import sogongsogong.bloodlink.repository.BDCRepository;
import sogongsogong.bloodlink.repository.DonorRepository;
import sogongsogong.bloodlink.repository.MIRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/mi")
//public class MIController extends UserController{
public class MIController {

    @Autowired
    private MIRepository miRepository;
    /*@Autowired
    private DonorRepository donorRepository;
    @Autowired
    private BDCRepository bdcRepository;*/

    @RequestMapping(value = "/register")
    public boolean register(@RequestParam String account, @RequestParam String password, @RequestParam String name, @RequestParam String phone, @RequestParam String address) {
        return register(new MI(account, password, name, phone, address));
    }

    //@RequestMapping(value = "/register")
    public boolean register(@RequestBody MI mi) {
        boolean response = false;
        if(!check(mi.getAccount())) {
            miRepository.save(mi);
            response = true;
        }
        return response;
    }

    @RequestMapping(value = "/check", method = GET)
    public boolean check(@RequestParam String account) {
        return miRepository.existsByAccount(account);
    }

    @RequestMapping(value = "/login")
    public boolean login(@RequestParam String account, @RequestParam String password) {
        boolean response = false;
        MI mi = info(account);
        if(mi != null) {
            response = mi.getPassword().equals(password);
        }
        return response;
    }

    @RequestMapping(value = "/search", method = GET)
    public String search(@RequestParam(required = false) String key, @RequestParam(required = false) String value) {
        List<MI> list = null;
        if(key == null || value == null) {
            list = new ArrayList<>();
            Iterable<MI> mis = miRepository.findAll();
            mis.forEach(list::add);
        } else {
            if(key.equals("account")) {
                list = new ArrayList<>();
                list.add(miRepository.findByAccount(value));
            } else if(key.equals("name")) {
                list = miRepository.findByName(value);
            } else if(key.equals("phone")) {
                list = miRepository.findByPhone(value);
            }
        }
        StringBuffer buffer = new StringBuffer();
        if(list != null) {
            Iterator iterator = list.iterator();
            while(iterator.hasNext()) {
                buffer.append(iterator.next().toString());
                buffer.append('\n');
            }
        }
        return buffer.toString();
        //return conceal(list);
    }

    @RequestMapping(value = "/{account}", method = GET)
    public MI info(@PathVariable String account) {
        return miRepository.findByAccount(account);
    }

    /*
    @RequestMapping(value = "/{account}/read", method = GET)
    public List<BDC> read(@PathVariable String account) {
        MI mi = info(account);
        Iterator<BDC> iterator = mi.getBdc().iterator();
        List<BDC> list = new ArrayList<>();
        while(iterator.hasNext()) {
            list.add(iterator.next());
        }
        return list;
    }
    */

    /*
    @RequestMapping(value = "/{account}/queue", method = GET) //all, wait, used
    public String queue(@PathVariable String account, @RequestParam(required = false) String stat, @RequestParam(required = false) String key, @RequestParam(required = false) String value) {
        List<BDC> bdcs = bdcRepository.findByDest(account);
        Iterator<BDC> iterator = bdcs.iterator();
        StringBuffer buffer = new StringBuffer();
        while(iterator.hasNext()) {
            String string;
            BDC bdc = iterator.next();
            Donor donor = (Donor)donorRepository.findByAccount(bdc.getOwner());
            boolean match = false;
            if(stat == null || stat.equals("all")) {
                if(key==null && value==null) {
                    match = true;
                }
            } else if(stat.equals("used")) {
                if(!bdc.isValid()) {
                    match = join(key, value, bdc, donor);
                }
            } else if(stat.equals("wait")) {
                if(bdc.isValid()) {
                    match = join(key, value, bdc, donor);
                }
            }
            if(match) {
                string = bdc.getNumber() + " " + donor.getName() + " " + donor.getAccount() + " " + bdc.isValid() + "\n";
            } else {
                string = "";
            }
            buffer.append(string);
        }
        return buffer.toString();
    }

    public boolean join(String key, String value, BDC bdc, Donor donor) {
        boolean match = false;
        if(key==null && value==null) {
            match = true;
        } else {
            if(key.equals("number")) {
                if(bdc.getNumber().equals(value)) {
                    match = true;
                }
            } else if(key.equals("account")) {
                if(donor.getAccount().equals(value)) {
                    match = true;
                }
            } else if(key.equals("name")) {
                if(donor.getName().equals(value)) {
                    match = true;
                }
            }
        }
        return match;
    }
    */

/*
    @RequestMapping(value = "/{account}/{use}", method = PUT)
    public boolean use(@PathVariable String account, @PathVariable String use, @RequestParam String number) {
        boolean response = false;
        BDC bdc = null;
        if(bdcRepository.existsByNumber(number)) {
            bdc = bdcRepository.findByNumber(number);
            if(use.equals("call")) {
                bdc.setValid(false);
                response = true;
            } else if(use.equals("recall")) {
                bdc.setDest("");
                bdc.setValid(true);
                response = true;
            }
        }
        return response;
    }
    */
/*
    @RequestMapping(value = "/{account}/{use}")
    public boolean use(@PathVariable String account, @PathVariable String use, @RequestParam String number) {
        boolean response = false;
        Iterator<BDC> iterator = info(account).getBdc().iterator();
        while(iterator.hasNext()) {
            BDC bdc = iterator.next();
            if(bdc.getNumber().equals(number)) {
                if(use.equals("call")) {
                    bdc.setValid(false);
                    response = true;
                } else if(use.equals("recall")) {
                    bdc.setDest("");
                    bdc.setValid(true);
                    response = true;
                }
                break;
            }
        }
        return response;
    }
*/


}
