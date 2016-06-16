package ggcartoon.yztc.com.Bean;

/**
 * Created by Administrator on 2016/5/4.
 */
public class ShouCang {
    private int id;
    private String title;
    private String updateTime;
    private String thumb;
    private String lastCharpterTitle;
    private String comicId;

    @Override
    public String toString() {
        return "ShouCang{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", thumb='" + thumb + '\'' +
                ", lastCharpterTitle='" + lastCharpterTitle + '\'' +
                ", comicId='" + comicId + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ShouCang() {
        // TODO Auto-generated constructor stub
    }
    public ShouCang(String comicId,String title, String updateTime, String thumb, String lastCharpterTitle) {
        super();
        this.comicId = comicId;
        this.title = title;
        this.updateTime = updateTime;
        this.thumb = thumb;
        this.lastCharpterTitle = lastCharpterTitle;
    }

    public String getComicId() {
        return comicId;
    }
    public void setComicId(String comicId) {
        this.comicId = comicId;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getLastCharpterTitle() {
        return lastCharpterTitle;
    }

    public void setLastCharpterTitle(String lastCharpterTitle) {
        this.lastCharpterTitle = lastCharpterTitle;
    }
}
