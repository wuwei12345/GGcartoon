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
   public  interface  HeadJK{
//       ?pagesize=30&page=1
        @GET("hotlist?pagesize=30")
        Call<GridBean> repoDataBean(@Query("page")String page);
//       Call<GridBean> repoDataBean(@Path("gid") String gurid);


   }

}
