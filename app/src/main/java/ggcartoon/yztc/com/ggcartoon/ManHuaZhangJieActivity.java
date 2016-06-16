package ggcartoon.yztc.com.ggcartoon;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import ggcartoon.yztc.com.Adapter.ManHuaZhangJieAdapter;
import ggcartoon.yztc.com.Bean.ManHuaZhangJieBean;
import ggcartoon.yztc.com.initerface.Initerface;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ManHuaZhangJieActivity extends SwipeBackActivity implements Initerface, View.OnClickListener {
    //返回按钮
    private ImageView ivMhlb;
    //标题
    private TextView tvMhlb;
    //章节列表
    private ListView mazjList;
    //网络请求
    private HttpUtils mhttputils;
    //漫画名，和对应的ID
    private String id, titlebar, comicId;
    //bean
    private List<ManHuaZhangJieBean.DataBean> MHZJ;
    //adapter
    private ManHuaZhangJieAdapter adapter;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 0:
                    adapter.setDatas(MHZJ);
                    //item单击事件
                    mazjList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent=new Intent(ManHuaZhangJieActivity.this,ManHuaActivity.class);
                            System.out.println("--ID-->"+MHZJ.get(position).getId());
                            intent.putExtra("bid",MHZJ.get(position).getId()+"");
                            startActivity(intent);
                        }
                    });
                 break;
                default:

                 break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manhuazhangjie);
        initview();
        initdata();
        initviewoper();
    }

    //初始化控件
    @Override
    public void initview() {
        mhttputils = new HttpUtils();
        ivMhlb = (ImageView) findViewById(R.id.iv_mhlb);
        tvMhlb = (TextView) findViewById(R.id.tv_mhlb);
        mazjList = (ListView) findViewById(R.id.mazj_list);
    }

    @Override
    public void initdata() {
        //获取上个页面携带的数据
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        titlebar=intent.getStringExtra("titlebar");
        comicId=intent.getStringExtra("comIcid");
        //给章节列表设置标题
        tvMhlb.setText(titlebar);
        ivMhlb.setOnClickListener(this);
    }

    @Override
    public void initviewoper() {
        String path ="http://csapi.dm300.com:21889/android/comic/charpterlist?comicsrcid=" + id + "&comicid=" + comicId;
        //通过网络获取章节列表
        run(path);
//        ZJdownLond();
        adapter=new ManHuaZhangJieAdapter();
        mazjList.setAdapter(adapter);
    }
//Xutils网络请求
//    private void ZJdownLond() {
//        //错误！id或者comicId错误
//
////        System.out.println("---->"+path);
//        mhttputils.send(HttpRequest.HttpMethod.GET, path, new RequestCallBack<String>() {
//            @Override
//            public void onSuccess(ResponseInfo<String> responseInfo) {
//                try {
//                    String json=responseInfo.result;
//                    JSONObject jsonobj = new JSONObject(json);
//                    JSONArray array = jsonobj.getJSONArray("data");
//                    MHZJ= JSON.parseArray(array.toString(),ManHuaZhangJieBean.DataBean.class);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                handler.sendEmptyMessage(0);
//                adapter.setDatas(MHZJ);
//            }
//
//            @Override
//            public void onFailure(HttpException e, String s) {
//                Toast.makeText(ManHuaZhangJieActivity.this,"网络请求失败",Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
    //OKhttp网络请求
    void run(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(ManHuaZhangJieActivity.this, "网络获取失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                try {
                    JSONObject jsonobj = new JSONObject(json);
                    JSONArray array = jsonobj.getJSONArray("data");
                    MHZJ= JSON.parseArray(array.toString(),ManHuaZhangJieBean.DataBean.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(0);
            }
        });

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回
            case R.id.iv_mhlb:
                ManHuaZhangJieActivity.this.finish();
                break;
            default:

                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
