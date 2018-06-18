package sogongsogong.bloodlink.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Entity
public class BDC {

    /*@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer bdcId;*/
    @Id
    private String number;
    private String type;
    private String name;
    private Calendar birth;
    private boolean sex;
    private Calendar date;
    private String place;

    //@ManyToOne(optional=false, targetEntity=Donor.class)
    //@JoinColumn(name="owner", referencedColumnName = "account")
    private String owner;

    //@ManyToOne(optional=false, targetEntity=MI.class)
    //@JoinColumn(name="dest", referencedColumnName = "account")
    private String dest;

    private boolean valid;

    public BDC() {
    }

    //public BDC(String number, String type, String name, Calendar birth, boolean sex, Calendar date, String place, String owner) {
    public BDC(String number, String type, String name, String birth, boolean sex, String date, String place, String owner) {
        this.number = number;
        this.type = type;
        this.name = name;
        //this.birth = birth;
        this.birth = stringToCalendar(birth);
        this.sex = sex;
        //this.date = date;
        this.date = stringToCalendar(date);
        this.place = place;
        this.owner = owner;
        this.dest = "";
        this.valid = false;
    }

    /*
    public Integer getBdcId() {
        return bdcId;
    }

    public void setBdcId(Integer bdcId) {
        this.bdcId = bdcId;
    }
    */

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
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
