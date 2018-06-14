package sogongsogong.bloodlink.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sogongsogong.bloodlink.model.BDC;
import sogongsogong.bloodlink.repository.BDCRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(path = "/bdc")
public class BDCController {

    @Autowired
    private BDCRepository bdcRepository;

    private final String REGISTER = "bdc 등록";
    private final String DUPLICATE = "bdc 중복";
    private final String AVAIL = "bdc 등록 가능";
    private final String WRONG = "잘못된 bdc 번호";

    @RequestMapping(path="/register")
    public String register(@RequestParam String number, @RequestParam String type, @RequestParam String name, @RequestParam Calendar birth, @RequestParam boolean sex, @RequestParam Calendar date, @RequestParam String place, @RequestParam String owner) {
        return register(new BDC(number, type, name, birth, sex, date, place, owner));
    }

    public String register(BDC bdc) {
        String result = "";
        if(check(bdc.getNumber()).equals(AVAIL)) {
            bdc.setValid(true);
            bdcRepository.save(bdc);
            result += REGISTER;
        } else {
            result += WRONG;
        }
        return result;
    }

    @RequestMapping(method = GET, path="/check")
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

    @RequestMapping(method = GET, path="/search")
    public BDC search(@RequestParam String number) {
        return bdcRepository.findByNumber(number);
    }

    public List<BDC> read() {
        List<BDC> list = new ArrayList<>();
        Iterable<BDC> bdcs = bdcRepository.findAll();
        bdcs.forEach(list::add);
        return list;
    }

    /*
    @RequestMapping(path="/approve")
    public boolean update(@RequestParam String number) {
        boolean approve = false;
        if(bdcRepository.existsByNumber(number)) {
            BDC bdc = bdcRepository.findByNumber(number);
            bdc.setValid(true);
            bdcRepository.save(bdc);
            approve = true;
        }
        return approve;
    }
    */


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

    @RequestMapping(path="/send")
    public boolean send(@RequestParam String number, @RequestParam String target) {
        boolean send = false;
        if(bdcRepository.existsByNumber(number)) {
            BDC bdc = bdcRepository.findByNumber(number);
            bdc.setOwner(target);
            bdcRepository.save(bdc);
        }
        return send;
    }
}
