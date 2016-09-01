package ggcartoon.yztc.com.View;

import java.util.HashMap;
import java.util.Map;

import ggcartoon.yztc.com.Bean.GridBean;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2016/8/18.
 */
public class RetrofitUtils {
    /**
     * 热门连载
     */
    public interface HeadJK {
        @GET("hotlist?pagesize=30")
        Call<GridBean> repoDataBean(@Query("page") String page);
    }

    /**
     * 小编推荐
     */
    public interface EditJX {
        @GET("editorlist?pagesize=30")
        Call<GridBean> repoDataBean(@Query("page") String page);
    }
}
