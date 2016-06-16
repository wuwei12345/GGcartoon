package ggcartoon.yztc.com.Bean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/30.
 */
public class SelectBean {

    /**
     * title : 火影忍者-者之书
     * thumb : http://csimg.dm300.com/images/spider/20150324/14271857006417.jpg
     * comicId : 5394
     * authorName : 岸本齐史
     * comicType : 冒险,武侠格斗,热血,综合其他
     * lastCharpter : {"id":"461427","title":"第14话"}
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
        private String comicId;
        private String authorName;
        private String comicType;

        @Override
        public String toString() {
            return "DataBean{" +
                    "title='" + title + '\'' +
                    ", thumb='" + thumb + '\'' +
                    ", comicId='" + comicId + '\'' +
                    ", authorName='" + authorName + '\'' +
                    ", comicType='" + comicType + '\'' +
                    ", lastCharpter=" + lastCharpter +
                    '}';
        }

        /**
         * id : 461427
         * title : 第14话
         */

        private LastCharpterBean lastCharpter;

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

        public String getComicId() {
            return comicId;
        }

        public void setComicId(String comicId) {
            this.comicId = comicId;
        }

        public String getAuthorName() {
            return authorName;
        }

        public void setAuthorName(String authorName) {
            this.authorName = authorName;
        }

        public String getComicType() {
            return comicType;
        }

        public void setComicType(String comicType) {
            this.comicType = comicType;
        }

        public LastCharpterBean getLastCharpter() {
            return lastCharpter;
        }

        public void setLastCharpter(LastCharpterBean lastCharpter) {
            this.lastCharpter = lastCharpter;
        }

        public static class LastCharpterBean {
            private String id;
            private String title;

            @Override
            public String toString() {
                return "LastCharpterBean{" +
                        "id='" + id + '\'' +
                        ", title='" + title + '\'' +
                        '}';
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
