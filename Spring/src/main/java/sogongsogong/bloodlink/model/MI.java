package sogongsogong.bloodlink.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
public class MI {
//public class MI extends User {

    //@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    //private Integer miId;
    private String address;

    @Id
    private String account;
    private String password;
    private String name;
    private String phone;

    //@OneToMany
    //@JoinColumn(name="dest")
    //List<BDC> bdc;

    public MI() {
    }

    public MI(String account, String password, String name, String phone, String address) {
        //super(account, password, name, phone);
        this.account = account;
        this.password = password;
        this.name = name;
        this.phone = phone;

        this.address = address;
    }

    /*
    public Integer getMiId() {
        return miId;
    }

    public void setMiId(Integer miId) {
        this.miId = miId;
    }
    */

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
    public List<BDC> getBdc() {
        return bdc;
    }

    public void setBdc(List<BDC> bdc) {
        this.bdc = bdc;
    }
*/
    @Override
    public String toString() {
        return "{" +
                "\"account\"=\"" + account + '\"' +
                ", \"name\"=\"" + name + '\"' +
                ", \"phone\"=\"" + phone + '\"' +

                ", \"address\"=\"" + address + '\"' +
                '}';
    }
}
