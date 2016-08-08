package ggcartoon.yztc.com.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ggcartoon.yztc.com.Adapter.GrideAdapter;
import ggcartoon.yztc.com.Bean.GridBean;
import ggcartoon.yztc.com.Bean.Head;
import ggcartoon.yztc.com.View.MyGirdView;
import ggcartoon.yztc.com.View.OkHttpUtils;
import ggcartoon.yztc.com.ggcartoon.ManHuaXiangQingActivity;
import ggcartoon.yztc.com.ggcartoon.R;
import ggcartoon.yztc.com.initerface.Initerface;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 热门推荐
 * A simple {@link Fragment} subclass.
 */
public class HotFragment extends Fragment implements Initerface, ViewPager.OnPageChangeListener {
    @Bind(R.id.Hot_MyGirdView)
    MyGirdView HotMyGirdView;
    //viewpage
    private ViewPager mHeadvp;
    //用来存放获取的数据
    private List<Head.DataBean> list;
    //广告栏的网址
    private String vppath = "http://csapi.dm300.com:21889/android/recom/editorrecomlist?pagesize=4&platform_id=0";
    //标题栏
    private TextView tvTitle;
    private List<ImageView> imageres;
    private List<String> titleres;
    //用来存放加载的网络图片
    private ImageView imageview;
    private BitmapUtils bitmapUtils;
    //网络请求
    private HttpUtils http;
    //上下拉刷新
    private PullToRefreshScrollView hotScrollView;
    //初始加载
    private ProgressBar mProgressBar;
    //热门漫画推荐
    private String gvPath = "http://csapi.dm300.com:21889/android/recom/hotlist?pagesize=30&page=";
    //bean
    GridBean gridbena;
    private List<GridBean.DataBean> Gridelist;
    //广告栏的adapter
    HeadvpAdapter headvpadapter;
    int currenindex = 1;
    Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    //adapter填充
                    tvTitle.setText(titleres.get(0));
                    tvTitle.setBackgroundColor(Color.argb(100, 0, 0, 0));
                    headvpadapter = new HeadvpAdapter();
                    mHeadvp.setAdapter(headvpadapter);
                    break;
                case 1:
                    mProgressBar.setVisibility(View.INVISIBLE);
                    //列表中展示数据
                    GrideAdapter adapter = new GrideAdapter(Gridelist);
                    HotMyGirdView.setAdapter(adapter);
                    hotScrollView.onRefreshComplete();
                    //加载后返回到最顶部
                    hotScrollView.getRefreshableView().fullScroll(0);
                    break;
                case 2:
                    //adapter填充
                    tvTitle.setText(titleres.get(0));
                    tvTitle.setBackgroundColor(Color.argb(100, 0, 0, 0));
                    headvpadapter = new HeadvpAdapter();
                    mHeadvp.setAdapter(headvpadapter);
                    break;
                case 3:
                    Toast.makeText(getActivity(), "网络获取失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    public HotFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hot, container, false);
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

    //初始化控件
    @Override
    public void initview() {
        mHeadvp = (ViewPager) getActivity().findViewById(R.id.head_vp);
//        http = new HttpUtils();
        imageres = new ArrayList<ImageView>();
        titleres = new ArrayList<String>();
        tvTitle = (TextView) getActivity().findViewById(R.id.tv_title);
        mProgressBar = (ProgressBar) getActivity().findViewById(R.id.hot_pb);
        hotScrollView = (PullToRefreshScrollView) getActivity().findViewById(R.id.hot_pulltorefresh);
        //RecyclerView初始化为网格状，3列
//        HotRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        hotScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                initPullRefreshData();
                //获取导航栏展示推荐漫画
                initHeadvpData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                initPullRefreshData();
                //获取导航栏展示推荐漫画
                initHeadvpData();
            }
        });
        HotMyGirdView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //跳到相应的漫画简介界面，把对应的漫画名字id传过去
                Intent intent = new Intent(getActivity(), ManHuaXiangQingActivity.class);
                intent.putExtra("comicId", Gridelist.get(position).getComicId());
                intent.putExtra("title", Gridelist.get(position).getTitle());
                startActivity(intent);
            }
        });
    }

    @Override
    public void initdata() {
        mProgressBar.setVisibility(View.VISIBLE);
        //上下拉刷新的初始化
        initScrollView();
        //获取导航栏展示推荐漫画
        initHeadvpData();
        //获取漫画数据并填充
        initPullRefreshData();
    }

    //获取数据
    private void initPullRefreshData() {
        String url = gvPath + currenindex++;
        try {
            OkHttpUtils.run(url).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    mhandler.sendEmptyMessage(3);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String json = response.body().string();
                    try {
                        JSONObject jsonobject = new JSONObject(json);
                        JSONArray jsonarray = jsonobject.getJSONArray("data");
                        Gridelist = JSON.parseArray(jsonarray.toString(), GridBean.DataBean.class);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mhandler.sendEmptyMessage(1);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //初始化上下拉刷新
    private void initScrollView() {
        hotScrollView.setMode(PullToRefreshBase.Mode.BOTH);
        hotScrollView.setPullLabel("下拉刷新", PullToRefreshBase.Mode.PULL_FROM_START);
        hotScrollView.setRefreshingLabel("正在加载...", PullToRefreshBase.Mode.PULL_FROM_START);
        hotScrollView.setReleaseLabel("放开刷新", PullToRefreshBase.Mode.PULL_FROM_START);
        hotScrollView.setPullLabel("上拉加载", PullToRefreshBase.Mode.PULL_FROM_END);
        hotScrollView.setRefreshingLabel("正在加载...", PullToRefreshBase.Mode.PULL_FROM_END);
        hotScrollView.setReleaseLabel("放开加载", PullToRefreshBase.Mode.PULL_FROM_END);

    }

    //广告获取内容
    private void initHeadvpData() {

        try {
            OkHttpUtils.run(vppath).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    mhandler.sendEmptyMessage(3);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String json = response.body().string();
                    try {
                        String obj = com.alibaba.fastjson.JSONObject.parseObject(json).getString("data");
                        list = com.alibaba.fastjson.JSONArray.parseArray(obj.toString(), Head.DataBean.class);
                        for (int i = 0; i < list.size(); i++) {
                            imageview = new ImageView(getActivity());
                            bitmapUtils.display(imageview, list.get(i).getThumb());
                            titleres.add(list.get(i).getTitle());
                            imageres.add(imageview);
                        }
                        mhandler.sendEmptyMessage(0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initviewoper() {
        bitmapUtils = new BitmapUtils(getActivity());
        //viewpage的滑动监听事件
        mHeadvp.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //加载广告标题
        tvTitle.setText(titleres.get(position));
        tvTitle.setBackgroundColor(Color.argb(100, 0, 0, 0));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    class HeadvpAdapter extends PagerAdapter {
        // 获取要滑动的控件的数量，在这里我们以滑动的广告栏为例，那么这里就应该是展示的广告图片的ImageView数量
        @Override
        public int getCount() {
            return imageres != null ? imageres.size() : 0;
        }

        // 来判断显示的是否是同一张图片，这里我们将两个参数相比较返回即可
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        // PagerAdapter只缓存三张要显示的图片，如果滑动的图片超出了缓存的范围，就会调用这个方法，将图片销毁
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imageres.get(position));
        }

        /*当要显示的图片可以进行缓存的时候，会调用这个方法进行显示图片的初始化，
       我们将要显示的ImageView加入到ViewGroup中，然后作为返回值返回即可*/
        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            switch (position) {
                case 0:
                    imageres.get(0).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), ManHuaXiangQingActivity.class);
                            intent.putExtra("comicId", list.get(position).getRecom_return());
                            intent.putExtra("title", list.get(position).getTitle());
                            startActivity(intent);
                        }
                    });
                    break;
                case 1:
                    imageres.get(1).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), ManHuaXiangQingActivity.class);
                            intent.putExtra("comicId", list.get(position).getRecom_return());
                            intent.putExtra("title", list.get(position).getTitle());
                            startActivity(intent);
                        }
                    });
                    break;
                case 2:
                    imageres.get(2).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), ManHuaXiangQingActivity.class);
                            intent.putExtra("comicId", list.get(position).getRecom_return());
                            intent.putExtra("title", list.get(position).getTitle());
                            startActivity(intent);
                        }
                    });
                    break;
            }
            container.addView(imageres.get(position));
            return imageres.get(position);
        }
    }
}
