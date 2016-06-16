package ggcartoon.yztc.com.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.HttpUtils;

import java.io.IOException;
import java.util.List;

import ggcartoon.yztc.com.Adapter.GengxinAdapter;
import ggcartoon.yztc.com.Bean.GengxinBean;
import ggcartoon.yztc.com.ggcartoon.ManHuaXiangQingActivity;
import ggcartoon.yztc.com.ggcartoon.R;
import ggcartoon.yztc.com.initerface.Initerface;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.handmark.pulltorefresh.library.PullToRefreshBase.Mode.BOTH;
import static com.handmark.pulltorefresh.library.PullToRefreshBase.Mode.PULL_FROM_END;
import static com.handmark.pulltorefresh.library.PullToRefreshBase.Mode.PULL_FROM_START;

/**
 * A simple {@link Fragment} subclass.
 */
public class GengxinFragment extends Fragment implements Initerface {
    private HttpUtils mhttp;
    private PullToRefreshListView lv;
    private ProgressBar pb;
    private List<GengxinBean.DataBean> list;
    private int currentindex = 1;
    private GengxinAdapter adapter;
    Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    pb.setVisibility(View.INVISIBLE);
                    adapter.setData(list);
                    lv.onRefreshComplete();
                    lv.getRefreshableView().setSelection(0);
                    break;
                case 1:
                    Toast.makeText(getActivity(), "获取网络数据失败", Toast.LENGTH_SHORT).show();
                    break;
                default:

                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gengxin, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initview();
        initdata();
        initviewoper();
    }

    @Override
    public void initview() {
        mhttp = new HttpUtils();
        adapter = new GengxinAdapter();
        lv = (PullToRefreshListView) getActivity().findViewById(R.id.lv_gengxin);
        pb = (ProgressBar) getActivity().findViewById(R.id.gengxin_pb);
    }

    private void initListView() {
        // TODO Auto-generated method stub
        lv.setMode(BOTH);
        lv.setPullLabel("下拉刷新", PULL_FROM_START);
        lv.setRefreshingLabel("正在加载...", PULL_FROM_START);
        lv.setReleaseLabel("放开刷新", PULL_FROM_START);
        lv.setPullLabel("上拉加载", PULL_FROM_END);
        lv.setRefreshingLabel("正在加载ing...", PULL_FROM_END);
        lv.setReleaseLabel("放开刷新up...", PULL_FROM_END);
    }

    @Override
    public void initdata() {
        String path = "http://csapi.dm300.com:21889/android/search/recentupdate?pagesize=30&page=" + currentindex++;
//        mhttp.send(HttpRequest.HttpMethod.GET, path, new RequestCallBack<String>() {
//            @Override
//            public void onLoading(long total, long current, boolean isUploading) {
//                super.onLoading(total, current, isUploading);
//                if (!isUploading) {
//                    pb.setVisibility(View.VISIBLE);
//                }
//            }
//
//            @Override
//            public void onSuccess(ResponseInfo<String> responseInfo) {
//                String json = responseInfo.result;
//                list = JSONArray.parseArray(JSONObject.parseObject(json).getString("data").toString(), GengxinBean.DataBean.class);
//                 list = JSONArray.parseArray(JSONObject.parseObject(json).getString("data").toString(), GengxinBean.DataBean.class);
//        pb.setVisibility(View.INVISIBLE);
//        adapter.setData(list);
//        lv.onRefreshComplete();
//        lv.getRefreshableView().setSelection(0);
//            }
//
//            @Override
//            public void onFailure(HttpException e, String s) {
//                Toast.makeText(getActivity(), "获取网络数据失败", Toast.LENGTH_SHORT).show();
//            }
//        });

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(path).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mhandler.sendEmptyMessage(1);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                list = JSONArray.parseArray(JSONObject.parseObject(json).getString("data").toString(), GengxinBean.DataBean.class);
                mhandler.sendEmptyMessage(0);
            }
        });
    }

    @Override
    public void initviewoper() {
        initListView();
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ManHuaXiangQingActivity.class);
                intent.putExtra("comicId", list.get(position - 1).getComicId());
                intent.putExtra("title", list.get(position - 1).getTitle());
                startActivity(intent);
            }
        });
        lv.setOnRefreshListener(onRefreshListener);
    }

    private PullToRefreshBase.OnRefreshListener<ListView> onRefreshListener = new PullToRefreshBase.OnRefreshListener<ListView>() {
        @Override
        public void onRefresh(PullToRefreshBase<ListView> refreshView) {
            initdata();
        }
    };
}
