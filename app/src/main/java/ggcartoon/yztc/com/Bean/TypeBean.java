package ggcartoon.yztc.com.Bean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/30.
 */
public class TypeBean {

    /**
     * title : 国产
     * thumb : http://csimg.dm300.com/images/icon_cn.jpg
     * cateId : -101
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String title;
        private String thumb;
        private int cateId;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public int getCateId() {
            return cateId;
        }

        public void setCateId(int cateId) {
            this.cateId = cateId;
        }
    }
}
