package ggcartoon.yztc.com.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
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
import ggcartoon.yztc.com.Adapter.GengxinAdapter;
import ggcartoon.yztc.com.Bean.GengxinBean;
import ggcartoon.yztc.com.View.DividerItemDecoration;
import ggcartoon.yztc.com.View.OkHttpUtils;
import ggcartoon.yztc.com.ggcartoon.ManHuaXiangQingActivity;
import ggcartoon.yztc.com.ggcartoon.R;
import ggcartoon.yztc.com.initerface.Initerface;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 最近更新
 * A simple {@link Fragment} subclass.
 */
public class GengxinFragment extends Fragment implements Initerface {
    @Bind(R.id.lv_gengxin)
    XRecyclerView lvGengxin;
    @Bind(R.id.gengxin_pb)
    ProgressBar gengxinPb;
    //    private PullToRefreshListView lv;
    private List<GengxinBean.DataBean> list;
    private int currentindex = 1;
    private GengxinAdapter adapter;
    Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    gengxinPb.setVisibility(View.INVISIBLE);
                    adapter=new GengxinAdapter();
                    adapter.setData(list);
                    lvGengxin.setAdapter(adapter);
                    adapter.setonItemClickLintener(new GengxinAdapter.onItemClickLintener() {
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
                    lvGengxin.refreshComplete();
                    lvGengxin.loadMoreComplete();
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
        View view = inflater.inflate(R.layout.fragment_gengxin, container, false);
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
        //设置分割线
        lvGengxin.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST));
        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        lvGengxin.setLayoutManager(manager);
        //设置刷新的加载和主题样式
        lvGengxin.setRefreshProgressStyle(ProgressStyle.BallPulse);
        lvGengxin.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);
        lvGengxin.setLoadingListener(new XRecyclerView.LoadingListener() {
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


    @Override
    public void initdata() {
        String path = "http://csapi.dm300.com:21889/android/search/recentupdate?pagesize=30&page=" + currentindex++;
        try {
            OkHttpUtils.run(path).enqueue(new Callback() {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initviewoper() {
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
