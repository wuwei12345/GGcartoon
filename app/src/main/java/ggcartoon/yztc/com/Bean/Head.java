package ggcartoon.yztc.com.Bean;

import java.util.List;

/**
 * 顶部广告栏的bean
 * Created by Administrator on 2016/4/27.
 */
public class Head {

    /**
     * title : 妖精的尾巴：第444话<皇帝斯普利玵>
     * recom_type : 0
     * recom_return : 39
     * thumb : http://csimg.dm300.com/images/upload/20150714/14368443711916.jpg
     * recom_index : 5
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
        private String recom_type;
        private String recom_return;
        private String thumb;
        private String recom_index;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getRecom_type() {
            return recom_type;
        }

        public void setRecom_type(String recom_type) {
            this.recom_type = recom_type;
        }

        public String getRecom_return() {
            return recom_return;
        }

        public void setRecom_return(String recom_return) {
            this.recom_return = recom_return;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getRecom_index() {
            return recom_index;
        }

        public void setRecom_index(String recom_index) {
            this.recom_index = recom_index;
        }
    }
}
