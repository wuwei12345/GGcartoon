package ggcartoon.yztc.com.Bean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/16.
 */
public class ManHuaBean {


    /**
     * charpterId : 67
     * title : web版中学篇①
     * sid : 10
     * updateTime : 1427183785
     * counts : 13
     * size : 2.6
     * addrs : ["http://images.dmzj.com/s/%E6%80%9D%E6%98%A5%E6%9C%9F%20bitter%20change/web%E7%89%88%E4%B8%AD%E5%AD%A6%E7%AF%87%E2%91%A0_1398345938/00.jpg","http://images.dmzj.com/s/%E6%80%9D%E6%98%A5%E6%9C%9F%20bitter%20change/web%E7%89%88%E4%B8%AD%E5%AD%A6%E7%AF%87%E2%91%A0_1398345938/2-16.jpg","http://images.dmzj.com/s/%E6%80%9D%E6%98%A5%E6%9C%9F%20bitter%20change/web%E7%89%88%E4%B8%AD%E5%AD%A6%E7%AF%87%E2%91%A0_1398345938/2-17.jpg","http://images.dmzj.com/s/%E6%80%9D%E6%98%A5%E6%9C%9F%20bitter%20change/web%E7%89%88%E4%B8%AD%E5%AD%A6%E7%AF%87%E2%91%A0_1398345938/2-18.jpg","http://images.dmzj.com/s/%E6%80%9D%E6%98%A5%E6%9C%9F%20bitter%20change/web%E7%89%88%E4%B8%AD%E5%AD%A6%E7%AF%87%E2%91%A0_1398345938/2-19.jpg","http://images.dmzj.com/s/%E6%80%9D%E6%98%A5%E6%9C%9F%20bitter%20change/web%E7%89%88%E4%B8%AD%E5%AD%A6%E7%AF%87%E2%91%A0_1398345938/2-20.jpg","http://images.dmzj.com/s/%E6%80%9D%E6%98%A5%E6%9C%9F%20bitter%20change/web%E7%89%88%E4%B8%AD%E5%AD%A6%E7%AF%87%E2%91%A0_1398345938/2-21.jpg","http://images.dmzj.com/s/%E6%80%9D%E6%98%A5%E6%9C%9F%20bitter%20change/web%E7%89%88%E4%B8%AD%E5%AD%A6%E7%AF%87%E2%91%A0_1398345938/2-22.jpg","http://images.dmzj.com/s/%E6%80%9D%E6%98%A5%E6%9C%9F%20bitter%20change/web%E7%89%88%E4%B8%AD%E5%AD%A6%E7%AF%87%E2%91%A0_1398345938/2-23.jpg","http://images.dmzj.com/s/%E6%80%9D%E6%98%A5%E6%9C%9F%20bitter%20change/web%E7%89%88%E4%B8%AD%E5%AD%A6%E7%AF%87%E2%91%A0_1398345938/2-24.jpg","http://images.dmzj.com/s/%E6%80%9D%E6%98%A5%E6%9C%9F%20bitter%20change/web%E7%89%88%E4%B8%AD%E5%AD%A6%E7%AF%87%E2%91%A0_1398345938/2-25.jpg","http://images.dmzj.com/s/%E6%80%9D%E6%98%A5%E6%9C%9F%20bitter%20change/web%E7%89%88%E4%B8%AD%E5%AD%A6%E7%AF%87%E2%91%A0_1398345938/2-26.jpg","http://images.dmzj.com/s/%E6%80%9D%E6%98%A5%E6%9C%9F%20bitter%20change/web%E7%89%88%E4%B8%AD%E5%AD%A6%E7%AF%87%E2%91%A0_1398345938/%E8%AF%B4%E6%98%8E.jpg"]
     * surl : http://manhua.dmzj.com/sichunqibitterchange/27866.shtml
     * iszm : 0
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        @Override
        public String toString() {
            return "DataBean{" +
                    "addrs=" + addrs +
                    '}';
        }

        private List<String> addrs;

        public List<String> getAddrs() {
            return addrs;
        }

        public void setAddrs(List<String> addrs) {
            this.addrs = addrs;
        }
    }
}
