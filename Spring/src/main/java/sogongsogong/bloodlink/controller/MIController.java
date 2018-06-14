package sogongsogong.bloodlink.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sogongsogong.bloodlink.model.BDC;
import sogongsogong.bloodlink.model.MI;
import sogongsogong.bloodlink.model.User;
import sogongsogong.bloodlink.repository.BDCRepository;
import sogongsogong.bloodlink.repository.MIRepository;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(path="/mi")
public class MIController extends UserController{

    @Autowired
    private MIRepository miRepository;
    @Autowired
    private BDCRepository bdcRepository;

    @RequestMapping(path = "/login")
    public boolean login(@RequestParam String account, @RequestParam String password) {
        MI mi = miRepository.findByAccount(account);
        return login(mi, password);
    }
    
    @RequestMapping(method = GET, path = "/identify")
    public String identify(@RequestParam String account) {
        String result = "";
        if(miRepository.existsByAccount(account)) {
            MI mi = miRepository.findByAccount(account);
            result += mi.getAccount();
        }
        return result;
    }

    @RequestMappring(method = GET, path = "/search/account")
    public String searchByAccount(@RequestParam String account) {
        StringBuffer buffer = new StringBuffer();
        List<BDC> bdcs = bdcRepository.findByUsage(account);
        for(BDC bdc:bdcs) {
            if(bdc.get)
            buffer.append(bdc.toString()+"\\r\\n");
        }
        return buffer.toString();
    }

    @RequestMapping(method = GET, path = "/search/n")
    public String load(@RequestParam String account) {
        StringBuffer buffer = new StringBuffer();
        if(miRepository.existsByAccount(account)) {
            BDCRepository bdcRepository = null;
            List<BDC> bdcs = bdcRepository.findByOwner(account);
            for(BDC bdc:bdcs) {
                buffer.append(bdc.toString()+"\\r\\n");
            }
        }
        return buffer.toString();
    }


}
