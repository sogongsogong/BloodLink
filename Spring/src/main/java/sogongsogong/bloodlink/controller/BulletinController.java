package sogongsogong.bloodlink.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sogongsogong.bloodlink.model.Bulletin;
import sogongsogong.bloodlink.repository.BulletinRepository;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(path = "/bulletin")
public class BulletinController {

    @Autowired
    private BulletinRepository bulletinRepository;
    private final AtomicInteger counter = new AtomicInteger();

    @RequestMapping(path="/register")
    public String register(@RequestParam String title, @RequestParam String author, @RequestParam int goal, @RequestParam String content) {
        bulletinRepository.save(new Bulletin(counter.incrementAndGet(), title, author, goal, content));
        return "Bulletin '" + title + "' registered!";
    }

    @RequestMapping(method = GET, path="/search")
    public List<Bulletin> search(@RequestParam String BDCNo) {
        return bulletinRepository.findByTitle(BDCNo);
    }
}
