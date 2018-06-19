package sogongsogong.bloodlink.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;

@Entity
//public class Donor extends User {
public class Donor {

    /*@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer donorId;*/
    private Calendar birth;
    private boolean sex;
    private String rh;
    private String abo;
    private int point;

    @Id
    private String account;
    private String password;
    private String name;
    private String phone;


    //@OneToMany
    //@JoinColumn(name="owner")
    //private Collection<BDC> bdc;

    public Donor() {
    }

    //public Donor(String account, String password, String name, String phone, Calendar birth, boolean sex, String rh, String abo) {
    public Donor(String account, String password, String name, String phone, String birth, boolean sex, String rh, String abo) {
        //super(account, password, name, phone);
        this.account = account;
        this.password = password;
        this.name = name;
        this.phone = phone;

        //this.birth = birth;
        this.birth = stringToCalendar(birth);
        this.sex = sex;
        this.rh = rh;
        this.abo = abo;
        this.point = 0;
    }

    /*
    public Integer getDonorId() {
        return donorId;
    }

    public void setDonorId(Integer donorId) {
        this.donorId = donorId;
    }
    */

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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
/*
    public Collection<BDC> getBdc() {
        return bdc;
    }

    public void setBdc(Collection<BDC> bdc) {
        this.bdc = bdc;
    }
*/
    @Override
    public String toString() {
        return "{" +
                "\"account\"=\"" + account + '\"' +
                ", \"name\"=\"" + name + '\"' +
                ", \"phone\"=\"" + phone + '\"' +

                ", \"birth\"=\"" + birth + '\"' +
                ", \"sex\"=" + sex +
                ", \"rh\"=\"" + rh + '\"' +
                ", \"abo\"=\"" + abo + '\"' +
                ", \"point\"=" + point +
                '}';
    }

    public Calendar stringToCalendar(String string) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(string));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar;
    }
}
