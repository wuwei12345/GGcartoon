package ggcartoon.yztc.com.ggcartoon;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.HttpUtils;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ggcartoon.yztc.com.Adapter.SelectAdapter;
import ggcartoon.yztc.com.Bean.SelectBean;
import ggcartoon.yztc.com.View.SwipBackActivityS;
import ggcartoon.yztc.com.initerface.Initerface;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.handmark.pulltorefresh.library.PullToRefreshBase.Mode.BOTH;
import static com.handmark.pulltorefresh.library.PullToRefreshBase.Mode.PULL_FROM_END;
import static com.handmark.pulltorefresh.library.PullToRefreshBase.Mode.PULL_FROM_START;

public class TypeActivity extends SwipBackActivityS implements Initerface {


    @Bind(R.id.Type_Toolbar)
    Toolbar TypeToolbar;
    @Bind(R.id.lv_mhlb)
    PullToRefreshListView lvMhlb;
    @Bind(R.id.but_up)
    FloatingActionButton butUp;
    private HttpUtils httpUtils;
    private SelectAdapter adapter;
    private String path;
    private String cateid;
    private List<SelectBean.DataBean> list;
    int page = 1;
    OkHttpClient client;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    //设置adpter
                    adapter.setData(list);
                    lvMhlb.setAdapter(adapter);
                    //刷新后隐藏
                    lvMhlb.onRefreshComplete();
                    break;
                case 2:
                    //设置adpter
                    adapter.setData(list);
                    adapter.notifyDataSetChanged();
                    //刷新后隐藏
                    lvMhlb.onRefreshComplete();
                    break;
                case 3:
                    Toast.makeText(TypeActivity.this, "没有更多", Toast.LENGTH_SHORT).show();
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

    //初始化控件
    @Override
    public void initview() {
        TypeToolbar.setTitle("分类");
        setSupportActionBar(TypeToolbar);
        //设置返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TypeToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //httpUtils = new HttpUtils();
        adapter = new SelectAdapter();
        lvMhlb = (PullToRefreshListView) findViewById(R.id.lv_mhlb);
        initListView();
    }

    @Override
    public void initdata() {
        Intent intent = getIntent();
        cateid = intent.getStringExtra("cateid");
        String title = intent.getStringExtra("title");
        TypeToolbar.setTitle(title);
        //网络访问
        run();
    }

    //加载网络数据
    void run() {
        path = "http://csapi.dm300.com:21889/android/search/categorylist?cateId=" + cateid
                + "&pagesize=30&tophot=1&page=" + page++;
        client = new OkHttpClient();
        Request request = new Request.Builder().url(path).build();
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
                if (JSONArray.parseArray(obj.toString(), SelectBean.DataBean.class) != null) {
                    if (list == null) {
                        list = JSONArray.parseArray(obj.toString(), SelectBean.DataBean.class);
                        handler.sendEmptyMessageDelayed(1, 2000);
                    } else {
                        list.addAll(JSONArray.parseArray(obj.toString(), SelectBean.DataBean.class));
                        handler.sendEmptyMessageDelayed(2, 2000);
                    }
                } else {
                    handler.sendEmptyMessageDelayed(3, 2000);
                }
            }
        });

    }

    private void initListView() {
        // TODO Auto-generated method stub
        lvMhlb.setMode(BOTH);
        lvMhlb.setPullLabel("下拉刷新", PULL_FROM_START);
        lvMhlb.setRefreshingLabel("正在加载...", PULL_FROM_START);
        lvMhlb.setReleaseLabel("放开刷新", PULL_FROM_START);
        lvMhlb.setPullLabel("上拉加载", PULL_FROM_END);
        lvMhlb.setRefreshingLabel("正在加载...", PULL_FROM_END);
        lvMhlb.setReleaseLabel("放开刷新up...", PULL_FROM_END);
    }

    @Override
    public void initviewoper() {
        lvMhlb.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TypeActivity.this, ManHuaXiangQingActivity.class);
                intent.putExtra("comicId", list.get(position - 1).getComicId() + "");
                intent.putExtra("title", list.get(position - 1).getTitle() + "");
                startActivity(intent);
            }
        });
        lvMhlb.setOnRefreshListener(onRefreshListener);
        butUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lvMhlb.getRefreshableView().setSelection(0);
            }
        });
    }

    private PullToRefreshListView.OnRefreshListener<ListView> onRefreshListener = new PullToRefreshBase.OnRefreshListener<ListView>() {
        @Override
        public void onRefresh(PullToRefreshBase<ListView> refreshView) {
            run();
        }
    };
}
