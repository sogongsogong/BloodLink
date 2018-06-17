package sogongsogong.bloodlink.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sogongsogong.bloodlink.model.BDC;
import sogongsogong.bloodlink.repository.BDCRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/bdc")
public class BDCController {

    @Autowired
    private BDCRepository bdcRepository;

    /*@RequestMapping(value = "/register", method = POST)
    public boolean register(@RequestParam String number, @RequestParam() String type, @RequestParam String name, @RequestParam Calendar birth, @RequestParam boolean sex, @RequestParam Calendar date, @RequestParam String place, @RequestParam String owner) {
        return register(new BDC(number, type, name, birth, sex, date, place, owner));
    }*/

    @RequestMapping(value = "/register", method = POST)
    public boolean register(@RequestBody BDC bdc) {
        boolean response = false;
        String number = bdc.getNumber();
        JsonNode jsonNode = null;
        try {
            jsonNode = new ObjectMapper().readTree(check(number));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(jsonNode.path("bdc").textValue().equals("avail")) {
            bdc.setValid(true);
            bdcRepository.save(bdc);
            response = true;
        }
        return response;
    }

    @RequestMapping(value = "/check", method = GET)
    public String check(@RequestParam String number) {
        String response = "{\"bdc\":\"";
        if(bdcRepository.existsByNumber(number)) {
            response += "exist";
        } else {
            if(number.matches("\\d{2}-\\d{2}-\\d{6}")) {
                response += "avail";
            } else {
                response += "wrong";
            }
        }
        return response + "\"}";
    }

    @RequestMapping(value = "/search/{account}", method = GET)
    public List<BDC> search(@PathVariable(required = false) String account, @RequestParam(required = false) String key, @RequestParam(required = false) String value) {
        List<BDC> bdcs = bdcRepository.findByOwner(account);
        Iterator<BDC> iterator = bdcs.iterator();
        List<BDC> list = new ArrayList<>();
        while(iterator.hasNext()) {
            BDC bdc = iterator.next();
            boolean match = false;
            if(bdc.getDest().length() == 0) {
                if(key == null || value == null) {
                    match = true;
                } else {
                    if(key.equals("number")) {
                        if(bdc.getNumber().equals(value)) {
                            match = true;
                        }
                    } else if(key.equals("type")) {
                        if(bdc.getType().equals(value)) {
                            match = true;
                        }
                    } else if(key.equals("valid")) {
                        if(value.equals("yes")) {
                            if(bdc.isValid()) {
                                match = true;
                            }
                        } else if(value.equals("no")) {
                            if(!bdc.isValid()) {
                                match = true;
                            }
                        } else if(value.equals("all")) {
                            match = true;
                        }
                    }
                }
            }
            if(match) {
                list.add(bdc);
            }
        }
        return list;
    }

    @RequestMapping(value = "/{number}", method = GET)
    public BDC info(@PathVariable String number) {
        BDC bdc = null;
        if(bdcRepository.existsByNumber(number)) {
            bdc = bdcRepository.findByNumber(number);
        }
        return bdc;
    }

    @RequestMapping(value = "/{number}/update", method = PUT)
    public boolean update(@PathVariable String number, @RequestParam boolean valid) {
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

    @RequestMapping(value = "/{number}/send/{target}", method = PUT)
    public boolean send(@PathVariable String number, @PathVariable String target, @RequestParam String account) {
        boolean send = false;
        if (bdcRepository.existsByNumber(number)) {
            BDC bdc = info(number);
            if (bdc != null && bdc.isValid()) {
                if (target.equals("donee")) {
                    bdc.setOwner(account);
                    send = true;
                } else if (target.equals("mi")) {
                    bdc.setDest(account);
                    send = true;
                } else {
                    bdc.setDest("");
                }
                bdcRepository.save(bdc);
            }
        }
        return send;
    }
}
