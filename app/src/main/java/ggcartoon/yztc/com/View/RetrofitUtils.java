package ggcartoon.yztc.com.View;

import java.util.HashMap;

import ggcartoon.yztc.com.Bean.GridBean;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2016/8/18.
 */
public class RetrofitUtils {
   public  interface  HeadJK{
//       ?pagesize=30&page={pageindex}
        @GET("hotlist")
        Call<GridBean> repoDataBean(@Path("pageindex")String path, @QueryMap()HashMap<String,String> params);

    }

}
