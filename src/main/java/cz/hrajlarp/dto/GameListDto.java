package cz.hrajlarp.dto;

/**
 * Created by jbalhar on 6/29/2014.
 */
public class GameListDto {
    private int id;
    private String name;
    private String image;
    private String info;

    private String dateAsDM;
    private String dateAsDayName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDateAsDM() {
        return dateAsDM;
    }

    public void setDateAsDM(String dateAsDM) {
        this.dateAsDM = dateAsDM;
    }

    public String getDateAsDayName() {
        return dateAsDayName;
    }

    public void setDateAsDayName(String dateAsDayName) {
        this.dateAsDayName = dateAsDayName;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
