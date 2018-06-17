package sogongsogong.bloodlink.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sogongsogong.bloodlink.model.Bulletin;
import sogongsogong.bloodlink.repository.BulletinRepository;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping("/bulletin")
public class BulletinController {

    @Autowired
    private BulletinRepository bulletinRepository;

    @RequestMapping(value = "/register")
    public void register(@RequestParam String title, @RequestParam String author, @RequestParam String name, @RequestParam int goal, @RequestParam String content) {
        bulletinRepository.save(new Bulletin(title, author, name, goal, content));
    }

    @RequestMapping(value = "/search", method = GET)
    public List<Bulletin> search(@RequestParam(required = false) String key, @RequestParam(required = false) String value) {
        List<Bulletin> list = null;
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

    @RequestMapping(value = "/{author}", method = GET)
    public List<Bulletin> search(@PathVariable String author) {
        return bulletinRepository.findByAuthor(author);
    }

    @RequestMapping(value = "/{id}", method = GET)
    public Bulletin info(@PathVariable Integer id) {
        Bulletin bulletin = null;
        if(bulletinRepository.existsById(id)) {
            bulletin = bulletinRepository.findById(id).get();
        }
        return bulletin;
    }

    @RequestMapping(value = "/{id}/expire", method = PUT)
    public boolean update(@PathVariable Integer id, @RequestParam boolean expire) {
        boolean response = false;
        Bulletin bulletin = info(id);
        if(bulletin != null && bulletin.isExpire() != expire) {
            bulletin.setExpire(expire);
            bulletinRepository.save(bulletin);
            response = true;
        }
        return response;
    }

    @RequestMapping(value = "/{id}/count", method = PUT)
    public boolean update(@PathVariable Integer id, @RequestParam int count) {
        boolean response = false;
        Bulletin bulletin = info(id);
        if(bulletin != null) {
            int goal = bulletin.getGoal();
            count += bulletin.getCount();
            bulletin.setCount(count);
            if(count > goal) {
                bulletin.setExpire(true);
            }
            bulletinRepository.save(bulletin);
            response = true;
        }
        return response;
    }



}
