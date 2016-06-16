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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lidroid.xutils.HttpUtils;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ggcartoon.yztc.com.Adapter.SelectAdapter;
import ggcartoon.yztc.com.Bean.SelectBean;
import ggcartoon.yztc.com.initerface.Initerface;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TypeActivity extends SwipeBackActivity implements Initerface {

    @Bind(R.id.iv_mhlb)
    ImageView ivMhlb;
    @Bind(R.id.tv_mhlb)
    TextView tvMhlb;
    @Bind(R.id.lv_mhlb)
    ListView lvMhlb;
    private HttpUtils httpUtils;
    private SelectAdapter adapter;
    private String path;
    private List<SelectBean.DataBean> list;
    OkHttpClient client;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    //设置adpter
                    adapter.setData(list);
                    lvMhlb.setAdapter(adapter);
                    break;
                default:

                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);
        ButterKnife.bind(this);
        initview();
        initdata();
        initviewoper();
    }

    @Override
    public void initview() {
        //初始化控件
        httpUtils = new HttpUtils();
        adapter = new SelectAdapter();
        ivMhlb = (ImageView) findViewById(R.id.iv_mhlb);
        tvMhlb = (TextView) findViewById(R.id.tv_mhlb);
        lvMhlb = (ListView) findViewById(R.id.lv_mhlb);
    }

    @Override
    public void initdata() {
        Intent intent = getIntent();
        String cateid = intent.getStringExtra("cateid");
        String title = intent.getStringExtra("title");
        tvMhlb.setText(title);
        path = "http://csapi.dm300.com:21889/android/search/categorylist?cateId=" + cateid
                + "&pagesize=30&tophot=1&page=1";
        //网络访问
        run(path);
    }

    //加载网络数据
    void run(String url) {
        client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(TypeActivity.this, "网络获取失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                String obj = JSONObject.parseObject(json).getString("data");
                list = JSONArray.parseArray(obj.toString(), SelectBean.DataBean.class);
                handler.sendEmptyMessage(1);
            }
        });

    }

    @Override
    public void initviewoper() {
        lvMhlb.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(TypeActivity.this, ManHuaXiangQingActivity.class);
                intent.putExtra("comicId", list.get(position).getComicId()+"");
                intent.putExtra("title", list.get(position).getTitle()+"");
                startActivity(intent);
            }
        });
        ivMhlb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TypeActivity.this.finish();
            }
        });
    }
}
