package sogongsogong.bloodlink.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
public class MI extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer miId;
    private String address;
    private String account;
    private String passwd;
    private String name;
    private String phone;

   @OneToMany
   @JoinColumn(name="dest")
   List<BDC> bdc;

    public MI(String account, String password, String name, String phone, String address) {
        super(account, password, name, phone);
        this.address = address;
    }

    @Override
    public String getAccount(){
        return super.getAccount();
    }

    public Integer getMiId() {
        return miId;
    }

    public void setMiId(Integer miId) {
        this.miId = miId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
