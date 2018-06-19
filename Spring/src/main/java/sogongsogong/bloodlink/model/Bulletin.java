package sogongsogong.bloodlink.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Bulletin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer bulletinId;
    private String title;
    private String author;
    private String name;
    private boolean expire;
    private int goal;
    private int count;
    private String content;

    public Bulletin() {
    }

    public Bulletin(String title, String author, String name, int goal, String content) {
        this.title = title;
        this.author = author;
        this.name = name;
        this.expire = false;
        this.goal = goal;
        this.count = 0;
        this.content = content;
    }

    public Integer getBulletinId() {
        return bulletinId;
    }

    public void setBulletinId(Integer bulletinId) {
        this.bulletinId = bulletinId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String author) {
        this.name = name;
    }

    public boolean isExpire() {
        return expire;
    }

    public void setExpire(boolean expire) {
        this.expire = expire;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
