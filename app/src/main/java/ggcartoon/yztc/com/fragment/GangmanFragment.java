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
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lidroid.xutils.HttpUtils;

import java.io.IOException;
import java.util.List;

import ggcartoon.yztc.com.Adapter.GrideAdapter;
import ggcartoon.yztc.com.Bean.GridBean;
import ggcartoon.yztc.com.View.MyGirdView;
import ggcartoon.yztc.com.ggcartoon.ManHuaXiangQingActivity;
import ggcartoon.yztc.com.ggcartoon.R;
import ggcartoon.yztc.com.initerface.Initerface;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**精彩港漫
 * A simple {@link Fragment} subclass.
 */
public class GangmanFragment extends Fragment implements Initerface{

    //网络请求
    private HttpUtils mhttp;
    //网格视图
    private MyGirdView myGridView;
    //接口请求要传的ID
    private int currentindex = 1;
    //加载显示
    private ProgressBar pb;
    //Bean目录
    private List<GridBean.DataBean> list;
    //上下拉刷新
    private PullToRefreshScrollView gangmanScrollView;
    Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    pb.setVisibility(View.INVISIBLE);
                    GrideAdapter adapter = new GrideAdapter();
                    adapter.SetDatas(list);
                    myGridView.setAdapter(adapter);
                    gangmanScrollView.onRefreshComplete();
                    gangmanScrollView.getRefreshableView().fullScroll(0);
                    break;
                case 2:
                    Toast.makeText(getActivity(),"网络错误",Toast.LENGTH_SHORT).show();
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
        return inflater.inflate(R.layout.fragment_gangman, container, false);
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
        myGridView = (MyGirdView) getActivity().findViewById(R.id.gangman_grid_view);
        pb = (ProgressBar) getActivity().findViewById(R.id.gangman_pb);
        gangmanScrollView = (PullToRefreshScrollView) getActivity().findViewById(R.id.gangman_pulltorefresh);
        //GridView条目单机事件
        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ManHuaXiangQingActivity.class);
                intent.putExtra("comicId", list.get(position).getComicId());
                intent.putExtra("title", list.get(position).getTitle());
                startActivity(intent);
            }
        });
        gangmanScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                initdata();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                initdata();
            }
        });
    }
    //初始化上下拉刷新
    private void initScrollView() {
        gangmanScrollView.setMode(PullToRefreshBase.Mode.BOTH);
        gangmanScrollView.setPullLabel("下拉刷新", PullToRefreshBase.Mode.PULL_FROM_START);
        gangmanScrollView.setRefreshingLabel("正在加载...", PullToRefreshBase.Mode.PULL_FROM_START);
        gangmanScrollView.setReleaseLabel("放开刷新", PullToRefreshBase.Mode.PULL_FROM_START);
        gangmanScrollView.setPullLabel("上拉加载", PullToRefreshBase.Mode.PULL_FROM_END);
        gangmanScrollView.setRefreshingLabel("正在加载...", PullToRefreshBase.Mode.PULL_FROM_END);
        gangmanScrollView.setReleaseLabel("放开加载", PullToRefreshBase.Mode.PULL_FROM_END);
    }
    //获取网络数据
    @Override
    public void initdata() {
        String path = "http://csapi.dm300.com:21889/android/recom/hothklist?pagesize=30&page=" + currentindex++;
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
//                //json解析
//                String json = responseInfo.result;
//                String obj = com.alibaba.fastjson.JSONObject.parseObject(json).getString("data");
//                list = JSONArray.parseArray(obj.toString(), GridBean.DataBean.class);
//                pb.setVisibility(View.INVISIBLE);
//                mhandler.sendEmptyMessage(1);
//                gangmanScrollView.getRefreshableView().fullScroll(0);
//            }
//
//            @Override
//            public void onFailure(HttpException e, String s) {
//                Toast.makeText(getActivity(), "获取网络数据失败，请检查网络", Toast.LENGTH_SHORT).show();
//
//            }
//        });
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(path).build();
        Call call=client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                 mhandler.sendEmptyMessage(2);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json=response.body().string();
                String obj = com.alibaba.fastjson.JSONObject.parseObject(json).getString("data");
                list = JSONArray.parseArray(obj.toString(), GridBean.DataBean.class);
                mhandler.sendEmptyMessage(1);

            }
        });
    }

    @Override
    public void initviewoper() {
        initScrollView();
    }
}
