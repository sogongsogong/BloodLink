package sogongsogong.bloodlink.model;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Collection;

@Entity
public class Donor extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer donorId;
    private Calendar birth;
    private boolean sex;
    private String rh;
    private String abo;
    private int point;
    private String account;

    @OneToMany
    @JoinColumn(name="owner")
    private Collection<BDC> bdc;


    public Donor(String account, String password, String name, String phone, Calendar birth, boolean sex, String rh, String abo) {
        super(account, password, name, phone);
        this.birth = birth;
        this.sex = sex;
        this.rh = rh;
        this.abo = abo;
        this.point = 0;
    }

    public String getAccount(){
        return super.getAccount();
    }

    public Integer getDonorId() {
        return donorId;
    }
    public void setDonorId(Integer donorId) {
        this.donorId = donorId;
    }

    public Calendar getBirth() {
        return birth;
    }

    public void setBirth(Calendar birth) {
        this.birth = birth;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getRh() {
        return rh;
    }

    public void setRh(String rh) {
        this.rh = rh;
    }

    public String getAbo() {
        return abo;
    }

    public void setAbo(String abo) {
        this.abo = abo;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
