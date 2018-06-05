package sogongsogong.bloodlink.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Calendar;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;
    private String email;
    private String password;
    private String name;
    private Calendar birth;
    private boolean sex;
    private String phone;
    private BloodType bloodType;
    private List<BDC> bdcs;
    private int point;

    public User(Integer userId, String email, String password, String name, Calendar birth, boolean sex, String phone) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.birth = birth;
        this.sex = sex;
        this.phone = phone;
        //bloodtype
        this.point = 0;
    }
}
