package sogongsogong.bloodlink.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sogongsogong.bloodlink.model.Bulletin;
import sogongsogong.bloodlink.repository.BulletinRepository;

import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping("/bulletin")
public class BulletinController {

    @Autowired
    private BulletinRepository bulletinRepository;

    @RequestMapping(value = "/register")
    public void register(@RequestParam String title, @RequestParam String author, @RequestParam String name, @RequestParam int goal, @RequestParam String content) {
        //bulletinRepository.save(new Bulletin(title, author, name, goal, content));
        register(new Bulletin(title, author, name, goal, content));
    }

    //@RequestMapping(value = "/register")
    public void register(@RequestBody Bulletin bulletin) {
        bulletinRepository.save(bulletin);
    }

    @RequestMapping(value = "/search", method = GET)
    public List<Bulletin> search(@RequestParam(required = false) String key, @RequestParam(required = false) String value) {
        //List<Bulletin> bulletins = bulletinRepository.findByExpire(false);
        //Iterator<Bulletin> iterator = bulletins.iterator();
        List<Bulletin> list = new ArrayList<>();
        /*while(iterator.hasNext()) {
            Bulletin bulletin = iterator.next();
            boolean match = false;
            if(key == null || value == null) {
                match = true;
            } else {
                if(key.equals("title") && bulletin.getTitle().equals(value)) {
                    match = true;
                } else if(key.equals("name") && bulletin.getName().equals(value)) {
                    match = true;
                }
            }
            if(match) {
                list.add(bulletin);
            }
        }
        */
        if(key == null || value == null) {
            list = new ArrayList<>();
            Iterable<Bulletin> bulletins = bulletinRepository.findAll();
            bulletins.forEach(list::add);
        } else {
            if (key.equals("title")) {
                list = bulletinRepository.findByTitle(value);
            } else if (key.equals("name")) {
                list = bulletinRepository.findByName(value);
            } else if (key.equals("active")) {
                if (value.equals("yes")) {
                    list = bulletinRepository.findByExpire(false);
                } else if (value.equals("no")) {
                    list = bulletinRepository.findByExpire(true);
                } else if (value.equals("all")) {
                    list = new ArrayList<>();
                    Iterable<Bulletin> bulletins = bulletinRepository.findAll();
                    bulletins.forEach(list::add);
                } else {
                    list = bulletinRepository.findByExpire(false);
                }
            }
        }
        return list;
    }
/*
    @RequestMapping(value = "/{author}", method = GET)
    public List<Bulletin> search(@PathVariable String author) {
        return bulletinRepository.findByAuthor(author);
    }
*/
    @RequestMapping(value = "/{id}")
    public Bulletin info(@PathVariable Integer id) {
        Bulletin bulletin = null;
        if(bulletinRepository.findById(id).isPresent()) {
            bulletin = bulletinRepository.findById(id).get();
        }
        return bulletin;
    }

    @RequestMapping(value = "/{id}/expire")
    //public boolean update(@PathVariable Integer id, @RequestParam boolean expire) {
    public String update(@PathVariable Integer id, @RequestParam boolean expire) {
        //boolean response = false;
        String response = null, result;
        Map<String, Object> map = new HashMap<>();
        Bulletin bulletin = info(id);
        if(bulletin != null) {
            if(bulletin.isExpire()!=expire) {
                bulletin.setExpire(expire);
                bulletinRepository.save(bulletin);
                //response = true;
                result = "success";
            } else {
                result = "fail";
            }
            map.put("result",result);
            map.put("expire",bulletin.isExpire());
        } else {
            map.put("result","wrong");
        }
        try {
            response = new ObjectMapper().writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return response;
    }

    /*@RequestMapping(value = "/{id}/count")
    //public boolean update(@PathVariable Integer id, @RequestParam int count) {
    public String update(@PathVariable Integer id, @RequestParam int count) {
        //boolean response = false;
        String response = null, result;
        Map<String, Object> map = new HashMap<>();
        Bulletin bulletin = info(id);
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
    }*/



}
