package sogongsogong.bloodlink.model;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String account;
    private String password;
    private String phone;
    private List<BDC> bdcs;

    public User(String account, String password, String phone) {
        this.account = account;
        this.password = password;
        this.phone = phone;
        this.bdcs = new ArrayList<>();
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<BDC> getBdcs() {
        return bdcs;
    }

    public void setBdcs(List<BDC> bdcs) {
        this.bdcs = bdcs;
    }
}
