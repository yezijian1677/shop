package cur.pro.entity.dto;

import cur.pro.entity.Game;
import cur.pro.entity.Kind;
import cur.pro.entity.Tag;

import java.util.Date;
import java.util.List;

public class GameDTO {

    private Integer id;

    private String creater;

    private String name;

    private String desc;

    private String systemcfg;

    private Double price;

    private Double discount;

    private Date ctime;

    private Date utime;

    private Byte stat;

    private List<Kind> kinds;

    private List<Tag> tags;

    private List<String> img;

    public GameDTO() {}

    public GameDTO(Game game, List<Tag> tags, List<String> img) {
        this.id = game.getId();
        this.creater = game.getCreater();
        this.name = game.getName();
        this.desc = game.getDesc();
        this.systemcfg = game.getSystemcfg();
        this.price = game.getPrice();
        this.discount = game.getDiscount();
        this.ctime = game.getCtime();
        this.utime = game.getUtime();
        this.stat = game.getStat();
        this.tags = tags;
        this.img = img;
    }

    public GameDTO(Game game, List<Kind> kinds, List<Tag> tags, List<String> img) {
        this.id = game.getId();
        this.creater = game.getCreater();
        this.name = game.getName();
        this.desc = game.getDesc();
        this.systemcfg = game.getSystemcfg();
        this.price = game.getPrice();
        this.discount = game.getDiscount();
        this.ctime = game.getCtime();
        this.utime = game.getUtime();
        this.stat = game.getStat();
        this.kinds = kinds;
        this.tags = tags;
        this.img = img;
    }

    public Integer getId() {
        return id;
    }

    public String getCreater() {
        return creater;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getSystemcfg() {
        return systemcfg;
    }

    public Double getPrice() {
        return price;
    }

    public Double getDiscount() {
        return discount;
    }

    public Date getCtime() {
        return ctime;
    }

    public Date getUtime() {
        return utime;
    }

    public Byte getStat() {
        return stat;
    }

    public List<Kind> getKinds() {
        return kinds;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public List<String> getImg() {
        return img;
    }
}
