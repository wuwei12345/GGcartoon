package ggcartoon.yztc.com.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ggcartoon.yztc.com.Adapter.XgrideAdapter;
import ggcartoon.yztc.com.Bean.GridBean;
import ggcartoon.yztc.com.View.OkHttpUtils;
import ggcartoon.yztc.com.ggcartoon.ManHuaXiangQingActivity;
import ggcartoon.yztc.com.ggcartoon.R;
import ggcartoon.yztc.com.initerface.Initerface;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 精彩港漫
 * A simple {@link Fragment} subclass.
 */
public class GangmanFragment extends Fragment implements Initerface {

    @Bind(R.id.gangman_recyclerView)
    XRecyclerView gangmanRecyclerView;
    //接口请求要传的ID
    private int currentindex = 1;
    //加载显示
    private ProgressBar pb;
    //Bean目录
    private List<GridBean.DataBean> list;
    XgrideAdapter adapter;
    Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    pb.setVisibility(View.INVISIBLE);
                   adapter = new XgrideAdapter(list);
                    gangmanRecyclerView.setAdapter(adapter);
                    adapter.setonItemClickLintener(new XgrideAdapter.onItemClickLintener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent intent = new Intent(getActivity(), ManHuaXiangQingActivity.class);
                            intent.putExtra("comicId", list.get(position-1).getComicId());
                            intent.putExtra("title", list.get(position-1).getTitle());
                            startActivity(intent);
                        }

                        @Override
                        public void onItemLongClick(View view, int Position) {

                        }
                    });
                    //停止刷新
                    gangmanRecyclerView.refreshComplete();
                    gangmanRecyclerView.loadMoreComplete();
                    break;
                case 2:
                    Toast.makeText(getActivity(), "网络错误", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    adapter = new XgrideAdapter(list);
                    adapter.notifyDataSetChanged();
                    //停止刷新
                    gangmanRecyclerView.refreshComplete();
                    gangmanRecyclerView.loadMoreComplete();
                    break;
                case 4:
                    Toast.makeText(getActivity(), "没有更多", Toast.LENGTH_SHORT).show();
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
        View view = inflater.inflate(R.layout.fragment_gangman, container, false);
        ButterKnife.bind(this, view);
        return view;
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
        pb = (ProgressBar) getActivity().findViewById(R.id.gangman_pb);
        //RecyclerView初始化
        gangmanRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));

    }

    //初始化上下拉刷新
    private void initScrollView() {
        //设置上拉刷新和下拉加载的主题样式
        gangmanRecyclerView.setRefreshProgressStyle(ProgressStyle.BallPulse);
        gangmanRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);
        gangmanRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                initdata();
            }

            @Override
            public void onLoadMore() {
                initdata();
            }
        });
    }

    //获取网络数据
    @Override
    public void initdata() {
        String path = "http://csapi.dm300.com:21889/android/recom/hothklist?pagesize=30&page=" + currentindex++;
        try {
            OkHttpUtils.run(path).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    mhandler.sendEmptyMessage(2);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String json = response.body().string();
                    if (JSONObject.parseObject(json).getString("data") != null) {
                        String obj = JSONObject.parseObject(json).getString("data");
                        if (list == null) {
                            list = JSONArray.parseArray(obj.toString(), GridBean.DataBean.class);
                            mhandler.sendEmptyMessageDelayed(1, 2000);
                        } else {
                            list.addAll(JSONArray.parseArray(obj.toString(), GridBean.DataBean.class));
                            mhandler.sendEmptyMessageDelayed(3, 2000);
                        }
                    } else {
                        mhandler.sendEmptyMessage(4);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initviewoper() {
        initScrollView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
