package ggcartoon.yztc.com.ggcartoon;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import ggcartoon.yztc.com.Adapter.PinLunAdapter;
import ggcartoon.yztc.com.Bean.PinLunBean;
import ggcartoon.yztc.com.View.SwipBackActivityS;
import ggcartoon.yztc.com.initerface.Initerface;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class PingLunListActivity extends SwipBackActivityS implements Initerface, View.OnClickListener {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    //返回按钮，标题
    private TextView PL_title;
    //List列表
    private ListView list;
    //Http
    private HttpUtils httpUtils;
    //评论bean
    private PinLunBean.DataBean PL_Date;
    //评论adapter
    private PinLunAdapter plAdapter;
    //Toolbar
    String title, comicId;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    //将bean的内容传入adapter
                    plAdapter.setDate(PL_Date.getComment_list());
                    break;
                case 2:
                    Toast.makeText(PingLunListActivity.this, "网络获取失败", Toast.LENGTH_SHORT).show();
                    break;
                default:

                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinglunlist);
        ButterKnife.bind(this);
        initview();
        initdata();
        initviewoper();
    }

    //初始化控件
    @Override
    public void initview() {
        toolbar.setTitle("评论");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        httpUtils = new HttpUtils();
        list = (ListView) findViewById(R.id.PL_list);
    }

    @Override
    public void initdata() {
        //获取传过来的值
        Intent intent = getIntent();
        title = intent.getStringExtra("titlebar");
        comicId = intent.getStringExtra("comIcid");
    }

    @Override
    public void initviewoper() {
        String path = "http://csapi.dm300.com:21889/android/comment/getCommentList?parent_id=0&pagesize=30&page=1&root_id=0&comicid=" + comicId;
//        initDown();、
        //加载网络数据
        run(path);
        //设置adapter
        plAdapter = new PinLunAdapter();
        list.setAdapter(plAdapter);
    }

    //Xutils网络请求
//    private void initDown() {
//
//        httpUtils.send(HttpRequest.HttpMethod.GET, path, new RequestCallBack<String>() {
//            @Override
//            public void onSuccess(ResponseInfo<String> responseInfo) {
//                try {
//                    //json解析
//                    String json=responseInfo.result;
//                    JSONObject obj=new JSONObject(json);
//                    JSONObject obj2=obj.getJSONObject("data");
//                    PL_Date= JSON.parseObject(obj2.toString(),PinLunBean.DataBean.class);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                //将bean的内容传入adapter
//                plAdapter.setDate(PL_Date.getComment_list());
//            }
//
//            @Override
//            public void onFailure(HttpException e, String s) {
//                Toast.makeText(PingLunListActivity.this,"网络错误",Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
    //okhttp网络访问
    void run(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(2);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                try {
                    //json解析
                    JSONObject obj = new JSONObject(json);
                    JSONObject obj2 = obj.getJSONObject("data");
                    PL_Date = JSON.parseObject(obj2.toString(), PinLunBean.DataBean.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(1);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回按钮
            default:

                break;
        }
    }
}
