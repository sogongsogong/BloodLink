package sogongsogong.bloodlink.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MI extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer miId;
    private String address;

    public MI(String account, String password, String name, String phone, String address) {
        super(account, password, name, phone);
        this.address = address;
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
