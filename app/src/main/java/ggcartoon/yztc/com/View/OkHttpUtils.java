package ggcartoon.yztc.com.View;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Administrator on 2016/7/20.
 */
public class OkHttpUtils {
    //饿汉式单例类.在第一次调用的时候实例化自己
    private  OkHttpUtils(){}
    private   static  OkHttpUtils OK=new OkHttpUtils();
    //静态工厂方法
    public static OkHttpUtils getInstance(){
        return OK;
    }
    //git请求
    public static Call run(String uri) throws Exception {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(uri).build();

        return client.newCall(request);
    }
}
