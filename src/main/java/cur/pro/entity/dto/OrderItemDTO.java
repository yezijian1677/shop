package cur.pro.entity.dto;

import cur.pro.entity.Code;
import cur.pro.entity.Game;
import cur.pro.entity.Orderitem;

import java.util.Date;

public class OrderItemDTO {

    private Integer id;

    private Double price;

    private String code;

    private Byte codeStat;

    private Date ctime;

    private Date utime;

    private Game game;

    public OrderItemDTO(Orderitem orderitem, Game game, Code code) {
        this.id = orderitem.getId();
        this.price = orderitem.getPrice();
        this.game = game;
        this.ctime = orderitem.getCtime();
        this.utime = orderitem.getUtime();
        if (code != null) {
            this.code = code.getCode();
            this.codeStat = code.getStat();
        }
    }

    public Integer getId() {
        return id;
    }

    public Double getPrice() {
        return price;
    }

    public String getCode() {
        return code;
    }

    public Byte getCodeStat() {
        return codeStat;
    }

    public Date getCtime() {
        return ctime;
    }

    public Date getUtime() {
        return utime;
    }

    public Game getGame() {
        return game;
    }
}
