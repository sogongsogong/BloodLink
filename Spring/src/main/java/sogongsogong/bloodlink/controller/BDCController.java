package sogongsogong.bloodlink.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sogongsogong.bloodlink.model.BDC;
import sogongsogong.bloodlink.repository.BDCRepository;

import java.io.IOException;
import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/bdc")
public class BDCController {

    @Autowired
    private BDCRepository bdcRepository;

    @RequestMapping(value = "/register")
    //public boolean register(@RequestParam String number, @RequestParam() String type, @RequestParam String name, @RequestParam String birth, @RequestParam boolean sex, @RequestParam String date, @RequestParam String place, @RequestParam String owner) {
    public String register(@RequestParam String number, @RequestParam() String type, @RequestParam String name, @RequestParam String birth, @RequestParam boolean sex, @RequestParam String date, @RequestParam String place, @RequestParam String owner) {
        return register(new BDC(number, type, name, birth, sex, date, place, owner));
    }

    //@RequestMapping(value = "/register")
    //public boolean register(@RequestBody BDC bdc) {
    public String register(@RequestBody BDC bdc) {
        //boolean response = false;
        String response = null;
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = new HashMap<>();
        String number = bdc.getNumber();
        try {
            String result = mapper.readTree(check(number)).path("result").textValue();
            if(result.equals("avail")) {
                bdc.setValid(true);
                bdcRepository.save(bdc);
                //response = true;
                map.put("result","register");
                map.put("number", number);
            } else {
                map.put("result",result);
            }
            response = mapper.writeValueAsString(map);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    @RequestMapping(value = "/check", method = GET)
    public String check(@RequestParam String number) {
        //String response = "{\"bdc\":\"";
        String response = null, result;
        Map<String, String> map = new HashMap<>();
        if(bdcRepository.existsByNumber(number)) {
            result = "exist";
        } else {
            if(number.matches("\\d{2}-\\d{2}-\\d{6}")) {
                result = "avail";
            } else {
                result = "wrong";
            }
        }
        map.put("result",result);
        try {
            response = new ObjectMapper().writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return response;
        //return response + "\"}";
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
        return bdcRepository.findByNumber(number);
    }


    @RequestMapping(value = "/{number}/update")
    //public boolean update(@PathVariable String number, @RequestParam boolean valid) {
    public String update(@PathVariable String number, @RequestParam boolean valid) {
        //boolean change = false;
        String response = null, result;
        Map<String, Object> map = new HashMap<>();
        BDC bdc = info(number);
        if(bdc != null) {
            if(bdc.isValid()!=valid) {
                bdc.setValid(valid);
                bdcRepository.save(bdc);
                //change = true;
                result = "success";
            } else {
                result = "fail";
            }
            map.put("result",result);
            map.put("valid",bdc.isValid());
        } else {
            map.put("result","wrong");
        }
        try {
            response = new ObjectMapper().writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return response;
        //return change;
    }

    /*
    @RequestMapping(value = "/{number}/send/{target}")
    //public boolean send(@PathVariable String number, @PathVariable String target, @RequestParam String account) {
    public String send(@PathVariable String number, @PathVariable String target, @RequestParam String account) {
        //boolean send = false;
        String response = null, result;
        Map<String, String> map = new HashMap<>();
        BDC bdc = info(number);
        if(bdc != null && bdc.isValid()) {
            if (target.equals("donee")) {
                bdc.setOwner(account);
                //send = true;
                result = "success";
            } else if (target.equals("mi")) {
                bdc.setDest(account);
                //send = true;
                result = "success";
            } else {
                bdc.setDest("");
                result = "wrong";
            }
            bdcRepository.save(bdc);
        } else {
            result = "fail";
        }
        map.put("result",result);
        try {
            response = new ObjectMapper().writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return response;
        //return send;
    }
    */
}
