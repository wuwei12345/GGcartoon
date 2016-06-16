package ggcartoon.yztc.com.Bean;

import java.util.List;

public class ManHuaXiangQing {

    /**
     * title : 神明大人什么也不做
     * comicId : 1
     * thumb : http://csimg.dm300.com/images/spider/20150324/14271837845034.jpg
     * authorName : 三国ハヂメ
     * comicType : 百合
     * areaName : 日本
     * updateTime : 2015-03-27
     * comicSrc : [{"title":"汗汗漫画(速度比较慢)","id":"11","lastCharpterTitle":"神明大人什么也不做 006集","lastCharpterId":"1133280","lastCharpterUpdateTime":1433779200},{"title":"733dm","id":"10","lastCharpterTitle":"第6话","lastCharpterId":"924487","lastCharpterUpdateTime":1432828800},{"title":"热血动漫","id":"6","lastCharpterTitle":"06话","lastCharpterId":"738604","lastCharpterUpdateTime":1431532800},{"title":"动漫之家","id":"2","lastCharpterTitle":"第06话","lastCharpterId":"12","lastCharpterUpdateTime":1431532800},{"title":"吹雪漫画","id":"3","lastCharpterTitle":"第06话","lastCharpterId":"340201","lastCharpterUpdateTime":1427385546},{"title":"CC图库","id":"5","lastCharpterTitle":"第6话","lastCharpterId":"189282","lastCharpterUpdateTime":1427353199}]
     * tucaos : 0
     * intro : 神明大人什么也不做漫画 ，姐妹三人生活着的槙村家突然出现了第四个妹妹 弥子，因为在国外生活所以语言和文化都还没习惯，为了面瘫的弥子而一直担心操劳的家人们之间发生的喜剧。
     * charpters : null
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String title;
        private int comicId;
        private String thumb;
        private String authorName;
        private String comicType;
        private String areaName;
        private String updateTime;
        private String tucaos;
        private String intro;
        private Object charpters;
        /**
         * title : 汗汗漫画(速度比较慢)
         * id : 11
         * lastCharpterTitle : 神明大人什么也不做 006集
         * lastCharpterId : 1133280
         * lastCharpterUpdateTime : 1433779200
         */

        private List<ComicSrcBean> comicSrc;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getComicId() {
            return comicId;
        }

        public void setComicId(int comicId) {
            this.comicId = comicId;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
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

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getTucaos() {
            return tucaos;
        }

        public void setTucaos(String tucaos) {
            this.tucaos = tucaos;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public Object getCharpters() {
            return charpters;
        }

        public void setCharpters(Object charpters) {
            this.charpters = charpters;
        }

        public List<ComicSrcBean> getComicSrc() {
            return comicSrc;
        }

        public void setComicSrc(List<ComicSrcBean> comicSrc) {
            this.comicSrc = comicSrc;
        }

        public static class ComicSrcBean {
            private String title;
            private String id;
            private String lastCharpterTitle;
            private String lastCharpterId;
            private int lastCharpterUpdateTime;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getLastCharpterTitle() {
                return lastCharpterTitle;
            }

            public void setLastCharpterTitle(String lastCharpterTitle) {
                this.lastCharpterTitle = lastCharpterTitle;
            }

            public String getLastCharpterId() {
                return lastCharpterId;
            }

            public void setLastCharpterId(String lastCharpterId) {
                this.lastCharpterId = lastCharpterId;
            }

            public int getLastCharpterUpdateTime() {
                return lastCharpterUpdateTime;
            }

            public void setLastCharpterUpdateTime(int lastCharpterUpdateTime) {
                this.lastCharpterUpdateTime = lastCharpterUpdateTime;
            }

            @Override
            public String toString() {
                return "ComicSrcBean{" +
                        "title='" + title + '\'' +
                        ", id='" + id + '\'' +
                        ", lastCharpterTitle='" + lastCharpterTitle + '\'' +
                        ", lastCharpterId='" + lastCharpterId + '\'' +
                        ", lastCharpterUpdateTime=" + lastCharpterUpdateTime +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "title='" + title + '\'' +
                    ", comicId=" + comicId +
                    ", thumb='" + thumb + '\'' +
                    ", authorName='" + authorName + '\'' +
                    ", comicType='" + comicType + '\'' +
                    ", areaName='" + areaName + '\'' +
                    ", updateTime='" + updateTime + '\'' +
                    ", tucaos='" + tucaos + '\'' +
                    ", intro='" + intro + '\'' +
                    ", charpters=" + charpters +
                    ", comicSrc=" + comicSrc +
                    '}';
        }
    }
}
