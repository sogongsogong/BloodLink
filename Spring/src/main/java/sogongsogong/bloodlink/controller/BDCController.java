package sogongsogong.bloodlink.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    @RequestMapping(path="/register")
    public String register(@RequestParam String number, @RequestParam String type, @RequestParam String name, @RequestParam Calendar birth, @RequestParam boolean sex, @RequestParam Calendar date, @RequestParam String place, @RequestParam String owner) {
        bdcRepository.save(new BDC(number, type, name, birth, sex, date, place, owner));
        return "BDC '" + number + "' registered!";
    }

    @RequestMapping(method = GET, path="/search")
    public BDC search(@RequestParam String number) {
        return bdcRepository.findByNumber(number);
    }

    @RequestMapping(method = GET, path="/search/all")
    public List<BDC> searchAll() {
        List<BDC> list = new ArrayList<>();
        Iterable<BDC> bdcs = bdcRepository.findAll();
        bdcs.forEach(list::add);
        return list;
    }

    @RequestMapping(method = GET, path="/check")
    public boolean isExist(@RequestParam String number) {
        return bdcRepository.existsByNumber(number);
    }

    @RequestMapping(path="/update/owner")
    public String update(@RequestParam String number, @RequestParam String owner) {
        BDC bdc = bdcRepository.findByNumber(number);
        bdc.setOwner(owner);
        bdcRepository.save(bdc);
        return "BDC '" + number + "' owner updated!";
    }

    @RequestMapping(path="/update/valid")
    public String update(@RequestParam String number, @RequestParam boolean valid) {
        BDC bdc = bdcRepository.findByNumber(number);
        if(bdc.isValid() != valid) {
            bdc.setValid(valid);
            bdcRepository.save(bdc);
        }
        return "BDC '" + number + "' valid updated!";
    }
}
