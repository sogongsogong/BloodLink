package sogongsogong.bloodlink.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Calendar;

@Entity
public class BDC {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer BDCId;
    private String BDCNo;
    private String name;
    private String type;
    private Calendar birth;
    private Calendar date;
    private String place;
    private String owner;
    private boolean valid;

    public BDC(Integer BDCId, String BDCNo, String name, String type, Calendar birth, Calendar date, String place, String owner) {
        this.BDCId = BDCId;
        this.BDCNo = BDCNo;
        this.name = name;
        this.type = type;
        this.birth = birth;
        this.date = date;
        this.place = place;
        this.owner = owner;
        this.valid = false;
    }
}
