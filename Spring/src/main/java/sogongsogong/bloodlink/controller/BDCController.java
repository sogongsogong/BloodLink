package sogongsogong.bloodlink.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sogongsogong.bloodlink.model.BDC;
import sogongsogong.bloodlink.repository.BDCRepository;

import java.util.Calendar;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping(path = "/bdc")
public class BDCController {

    @Autowired
    private BDCRepository bdcRepository;

    private final String REGISTER = "registered";
    private final String DUPLICATE = "duplicate";
    private final String AVAIL = "avail";
    private final String WRONG = "wrong";



    @RequestMapping(path = "/register")
    public String register(@RequestParam String number, @RequestParam String type, @RequestParam String name, @RequestParam Calendar birth, @RequestParam boolean sex, @RequestParam Calendar date, @RequestParam String place, @RequestParam String owner) {
        return register(new BDC(number, type, name, birth, sex, date, place, owner));
    }

    public String register(BDC bdc) {
        String result = "";
        String check = check(bdc.getNumber());
        if(check.equals(AVAIL)) {
            bdc.setValid(true);
            bdcRepository.save(bdc);
            result += REGISTER;
        } else {
            result += check;
        }
        return result;
    }

    @RequestMapping(method = GET, path = "/check")
    public String check(@RequestParam String number) {
        String result = "";
        if(bdcRepository.existsByNumber(number)) {
            result += DUPLICATE;
        } else {
            if(number.matches("\\d{2}-\\d{2}-\\d{6}")) {
                result += AVAIL;
            } else {
                result += WRONG;
            }
        }
        return result;
    }

    @RequestMapping(method = GET, path = "/search")
    public String search(@RequestParam String number) {
        String result = "";
        if(bdcRepository.existsByNumber(number)) {
            result += bdcRepository.findByNumber(number).toString();
        }
        return result;
    }

    @RequestMapping(method = PUT, path = "/update")
    public boolean update(String number, boolean valid) {
        boolean change = false;
        if(bdcRepository.existsByNumber(number)) {
            BDC bdc = bdcRepository.findByNumber(number);
            if(bdc.isValid()!=valid) {
                bdc.setValid(valid);
                bdcRepository.save(bdc);
                change = true;
            }
        }
        return change;
    }

    @RequestMapping(method = PUT, path = "/send")
    public boolean send(@RequestParam String number, @RequestParam String target, @RequestParam String account) {
        boolean send = false;
        if(bdcRepository.existsByNumber(number)) {
            BDC bdc = bdcRepository.findByNumber(number);
            if(target.equals("donee")) {
                bdc.setOwner(account);
            } else if(target.equals("mi")) {
                bdc.setUsage(account);
            } else {
                bdc.setUsage("");
            }
            bdcRepository.save(bdc);
        }
        return send;
    }
}
