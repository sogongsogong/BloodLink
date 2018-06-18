package sogongsogong.bloodlink.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sogongsogong.bloodlink.model.BDC;
import sogongsogong.bloodlink.model.Bulletin;
import sogongsogong.bloodlink.model.Donor;
import sogongsogong.bloodlink.repository.BDCRepository;
import sogongsogong.bloodlink.repository.BulletinRepository;
import sogongsogong.bloodlink.repository.DonorRepository;
import sogongsogong.bloodlink.repository.MIRepository;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class ServiceController {

    @Autowired
    private BDCRepository bdcRepository;
    @Autowired
    private BulletinRepository bulletinRepository;
    @Autowired
    private DonorRepository donorRepository;
    @Autowired
    private MIRepository miRepository;

    @RequestMapping(value = "/bdc/{number}/send/{target}")
    //public boolean send(@PathVariable String number, @PathVariable String target, @RequestParam String account) {
    public String send(@PathVariable String number, @PathVariable String target, @RequestParam String account) {
        //boolean send = false;
        String response = null, result;
        Map<String, String> map = new HashMap<>();
        BDC bdc = bdcRepository.findByNumber(number);
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

    @RequestMapping(value = "/bulletin/{id}/count")
    //public boolean update(@PathVariable Integer id, @RequestParam int count) {
    public String count(@PathVariable Integer id, @RequestParam int count) {
        //boolean response = false;
        String response = null, result;
        Map<String, Object> map = new HashMap<>();
        Bulletin bulletin = bulletinRepository.findById(id).get();
        if(bulletin != null) {
            int goal = bulletin.getGoal();
            count += bulletin.getCount();
            bulletin.setCount(count);
            if(count > goal) {
                bulletin.setExpire(true);
            }
            bulletinRepository.save(bulletin);
            //response = true;
            result = "success";
        } else {
            result = "wrong";
        }
        map.put("result",result);
        try {
            response = new ObjectMapper().writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return response;
    }


    @RequestMapping(value = "/donor/{account}/point")
    public boolean point(@PathVariable String account, @RequestParam int point) {
        boolean response = false;
        Donor donor = donorRepository.findByAccount(account);
        if(donor != null) {
            point += donor.getPoint();
            donor.setPoint(point);
            donorRepository.save(donor);
            response = true;
        }
        return response;
    }


    @RequestMapping(value = "/mi/{account}/queue", method = GET)
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

    @RequestMapping(value = "/mi/{account}/{use}")
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
}
