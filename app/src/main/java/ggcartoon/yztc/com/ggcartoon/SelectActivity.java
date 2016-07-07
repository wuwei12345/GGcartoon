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
import android.widget.TextView;
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

public class SelectActivity extends SwipBackActivityS implements Initerface {

    @Bind(R.id.lv_mhlb)
    PullToRefreshListView lvMhlb;
    @Bind(R.id.tv_NO)
    TextView tvNO;
    @Bind(R.id.select_toolbar)
    Toolbar selectToolbar;
    @Bind(R.id.but_ups)
    FloatingActionButton butUps;
    private HttpUtils httpUtils;
    private String path;
    private List<SelectBean.DataBean> list;
    private SelectAdapter adapter;
    String value;
    int page = 1;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    //隐藏搜索为空的textview
                    tvNO.setVisibility(View.GONE);
                    //设置adapter
                    adapter.setData(list);
                    lvMhlb.setAdapter(adapter);
                    //显示列表
                    lvMhlb.setVisibility(View.VISIBLE);
                    lvMhlb.onRefreshComplete();
                    break;
                case 2:
                    Toast.makeText(SelectActivity.this, "网络网络获取数据失败", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(SelectActivity.this, "没有更多", Toast.LENGTH_SHORT).show();
                    lvMhlb.onRefreshComplete();
                    break;
                case 4:
                    //隐藏搜索为空的textview
                    tvNO.setVisibility(View.GONE);
                    //设置adapter
                    adapter.setData(list);
                    //刷新adapter
                    adapter.notifyDataSetChanged();
                    //显示列表
                    lvMhlb.setVisibility(View.VISIBLE);
                    lvMhlb.onRefreshComplete();
                    break;
                default:

                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        ButterKnife.bind(this);
        initview();
        initdata();
        initviewoper();
    }

    //初始化控件
    @Override
    public void initview() {
        selectToolbar = (Toolbar) findViewById(R.id.select_toolbar);
        selectToolbar.setTitle("搜索");
        setSupportActionBar(selectToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        selectToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        httpUtils = new HttpUtils();
        adapter = new SelectAdapter();
        tvNO = (TextView) findViewById(R.id.tv_NO);
        lvMhlb = (PullToRefreshListView) findViewById(R.id.lv_mhlb);

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
    public void initdata() {
        Intent intent = getIntent();
        value = intent.getStringExtra("value");
        selectToolbar.setTitle(value);
//        download();
        //加载数据
        run();
    }

    //Xutils加载网络数据
//    private void download() {
//        httpUtils.send(HttpRequest.HttpMethod.GET, path, new RequestCallBack<String>() {
//            @Override
//            public void onSuccess(ResponseInfo<String> responseInfo) {
//                String json = responseInfo.result;
//                if (JSONObject.parseObject(json).getString("data") != null) {
//                    String obj = JSONObject.parseObject(json).getString("data");
//                    list = JSONArray.parseArray(obj.toString(), SelectBean.DataBean.class);
//                    handler.sendEmptyMessage(1);
//                }
//            }
//
//            @Override
//            public void onFailure(HttpException e, String s) {
//                Toast.makeText(SelectActivity.this,"加载数据失败，请检查网络",Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
    //加载网络数据
    void run() {
        path = "http://csapi.dm300.com:21889/android/search/query?pagesize=30&page=" + page++ + "&keyword=" + value;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(path).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(2);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                if (JSONObject.parseObject(json).getString("data") != null) {
                    String obj = JSONObject.parseObject(json).getString("data");
                    if (list == null) {
                        list = JSONArray.parseArray(obj.toString(), SelectBean.DataBean.class);
                        handler.sendEmptyMessageDelayed(1, 2000);
                    } else {
                        list.addAll(JSONArray.parseArray(obj.toString(), SelectBean.DataBean.class));
                        handler.sendEmptyMessageDelayed(4, 2000);
                    }

                } else {
                    handler.sendEmptyMessageDelayed(3, 2000);
                }
            }
        });

    }

    @Override
    public void initviewoper() {
        initListView();
        //列表的单击事件
        lvMhlb.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SelectActivity.this, ManHuaXiangQingActivity.class);
                intent.putExtra("comicId", list.get(position - 1).getComicId() + "");
                intent.putExtra("title", list.get(position - 1).getTitle() + "");
                startActivity(intent);
            }
        });
        lvMhlb.setOnRefreshListener(onRefreshListener);
        butUps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lvMhlb.getRefreshableView().setSelection(0);
            }
        });
    }

    private PullToRefreshBase.OnRefreshListener<ListView> onRefreshListener = new PullToRefreshBase.OnRefreshListener<ListView>() {
        @Override
        public void onRefresh(PullToRefreshBase<ListView> refreshView) {
            run();
        }
    };
}
