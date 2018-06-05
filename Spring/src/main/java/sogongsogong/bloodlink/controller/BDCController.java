package sogongsogong.bloodlink.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sogongsogong.bloodlink.model.BDC;
import sogongsogong.bloodlink.repository.BDCRepository;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(path = "/bdc")
public class BDCController {

    @Autowired
    private BDCRepository bdcRepository;
    private final AtomicInteger counter = new AtomicInteger();

    @RequestMapping(path="/register")
    public String register(@RequestParam String BDCNo, @RequestParam String name, @RequestParam String type, @RequestParam Calendar birth, @RequestParam Calendar date, @RequestParam String place, @RequestParam String owner) {
        bdcRepository.save(new BDC(counter.incrementAndGet(), BDCNo, name, type, birth, date, place, owner));
        return "BDC '" + BDCNo + "' registered!";
    }

    @RequestMapping(method = GET, path="/search")
    public List<BDC> search(@RequestParam String BDCNo) {
        return bdcRepository.findByNo(BDCNo);
    }
}
