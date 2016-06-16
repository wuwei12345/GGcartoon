package ggcartoon.yztc.com.Bean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/9.
 */
public class PinLunBean {

    /**
     * total : 4
     * page : 1
     * comment_list : [{"id":"36638","nickname":"呱呱用户","uid":"55810","root_id":"0","parent_id":"0","content":"在第21画就可以看到 甲子同学被带上了面具 头发变回了黑色","post_time":"1454306740","reply_no":"0","user_thumb":""},{"id":"36637","nickname":"呱呱用户","uid":"55810","root_id":"0","parent_id":"0","content":"变成人偶后在带上一次就变回来了","post_time":"1454306559","reply_no":"0","user_thumb":""},{"id":"24145","nickname":"叶良辰","uid":"30513","root_id":"0","parent_id":"0","content":"一点都不恐怖。。","post_time":"1446942442","reply_no":"0","user_thumb":"http://csimg.dm300.com/images/user_thumb/14453199977551.jpg"},{"id":"23106","nickname":"伊子","uid":"4678","root_id":"0","parent_id":"0","content":"沙发","post_time":"1446277379","reply_no":"0","user_thumb":"http://csimg.dm300.com/images/user_thumb/14355460365616.jpg"}]
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String total;
        private int page;
        /**
         * id : 36638
         * nickname : 呱呱用户
         * uid : 55810
         * root_id : 0
         * parent_id : 0
         * content : 在第21画就可以看到 甲子同学被带上了面具 头发变回了黑色
         * post_time : 1454306740
         * reply_no : 0
         * user_thumb :
         */

        private List<CommentListBean> comment_list;

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public List<CommentListBean> getComment_list() {
            return comment_list;
        }

        public void setComment_list(List<CommentListBean> comment_list) {
            this.comment_list = comment_list;
        }

        public static class CommentListBean {
            private String id;
            private String nickname;
            private String uid;
            private String root_id;
            private String parent_id;
            private String content;
            private String post_time;
            private String reply_no;
            private String user_thumb;

            @Override
            public String toString() {
                return "CommentListBean{" +
                        "id='" + id + '\'' +
                        ", nickname='" + nickname + '\'' +
                        ", uid='" + uid + '\'' +
                        ", root_id='" + root_id + '\'' +
                        ", parent_id='" + parent_id + '\'' +
                        ", content='" + content + '\'' +
                        ", post_time='" + post_time + '\'' +
                        ", reply_no='" + reply_no + '\'' +
                        ", user_thumb='" + user_thumb + '\'' +
                        '}';
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getRoot_id() {
                return root_id;
            }

            public void setRoot_id(String root_id) {
                this.root_id = root_id;
            }

            public String getParent_id() {
                return parent_id;
            }

            public void setParent_id(String parent_id) {
                this.parent_id = parent_id;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getPost_time() {
                return post_time;
            }

            public void setPost_time(String post_time) {
                this.post_time = post_time;
            }

            public String getReply_no() {
                return reply_no;
            }

            public void setReply_no(String reply_no) {
                this.reply_no = reply_no;
            }

            public String getUser_thumb() {
                return user_thumb;
            }

            public void setUser_thumb(String user_thumb) {
                this.user_thumb = user_thumb;
            }
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "total='" + total + '\'' +
                    ", page=" + page +
                    ", comment_list=" + comment_list +
                    '}';
        }
    }
}
