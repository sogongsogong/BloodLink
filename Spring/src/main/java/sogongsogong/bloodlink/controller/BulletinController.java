package sogongsogong.bloodlink.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(path = "/bulletin")
public class BulletinController {

    @Autowired
    private BulletinRepository bulletinRepository;

    @RequestMapping(path = "/register")
    public String register(@RequestParam String title, @RequestParam String author, @RequestParam int goal, @RequestParam String content) {
        bulletinRepository.save(new Bulletin(title, author, goal, content));
        return "Bulletin '" + title + "' registered!";
    }

    @RequestMapping(method = GET, path = "/search/id")
    public Bulletin searchById(@RequestParam Integer id) {
        return bulletinRepository.findById(id).get();
    }

    @RequestMapping(method = GET, path = "/search/title")
    public List<Bulletin> searchByTitle(@RequestParam String title) {
        return bulletinRepository.findByTitle(title);
    }

    @RequestMapping(method = GET, path = "/search/author")
    public List<Bulletin> searchByAuthor(@RequestParam String author) {
        return bulletinRepository.findByAuthor(author);
    }

    @RequestMapping(method = GET, path = "/search/all")
    public List<Bulletin> searchAll() {
        List<Bulletin> list = new ArrayList<>();
        Iterable<Bulletin> bulletins = bulletinRepository.findAll();
        bulletins.forEach(list::add);
        return list;
    }

    @RequestMapping(method = PUT, path = "/update/expire")
    public String update(@RequestParam Integer id, @RequestParam boolean expire) {
        Bulletin bulletin = bulletinRepository.findById(id).get();
        if(bulletin.isExpire() != expire) {
            bulletin.setExpire(expire);
            bulletinRepository.save(bulletin);
        }
        return "Bulletin '" + id + "' valid updated!";
    }

    @RequestMapping(method = PUT, path = "/update/count")
    public String update(@RequestParam Integer id, @RequestParam int count) {
        Bulletin bulletin = bulletinRepository.findById(id).get();
        int goal = bulletin.getGoal();
        count += bulletin.getCount();
        bulletin.setCount(count);
        if(count > goal) {
            bulletin.setExpire(true);
        }
        bulletinRepository.save(bulletin);
        return "Bulletin '" + id + "' has "+ count + " points";
    }
}
