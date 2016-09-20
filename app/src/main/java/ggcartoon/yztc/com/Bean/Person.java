package ggcartoon.yztc.com.Bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

/**
 * Created by wuwei on 16/9/1.
 */

public class Person extends BmobObject{
    int id;
    private String title;
    private String updateTime;
    private String thumb;
    private String lastCharpterTitle;
    private String comicId;
    private String username;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "ShouCang{" +
                ", title='" + title + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", thumb='" + thumb + '\'' +
                ", lastCharpterTitle='" + lastCharpterTitle + '\'' +
                ", comicId='" + comicId + '\'' +
                '}';
    }



    public Person() {
        // TODO Auto-generated constructor stub
    }
    public Person(String comicId,String title, String updateTime, String thumb, String lastCharpterTitle) {
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
