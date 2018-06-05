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
    private boolean expire;
    private int goal;
    private int rate;
    private String content;

    public Bulletin(Integer bulletinId, String title, String author, int goal, String content) {
        this.bulletinId = bulletinId;
        this.title = title;
        this.author = author;
        this.expire = false;
        this.goal = goal;
        this.rate = 0;
        this.content = content;
    }
}
