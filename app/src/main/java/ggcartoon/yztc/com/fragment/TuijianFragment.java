package ggcartoon.yztc.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ggcartoon.yztc.com.Adapter.TuijianAdapter;
import ggcartoon.yztc.com.ggcartoon.R;
import ggcartoon.yztc.com.initerface.Initerface;

//推荐页面
public class TuijianFragment extends Fragment implements Initerface{
    //viewpager
    private ViewPager mvp;
    //tabLyaout
    private TabLayout mtabLayout;
    //存放四个fragment
    private List<Fragment> myFragmentlist;
    //存放tab列表名称
    private List<String> list_Title;
    //推荐adapter
    private TuijianAdapter adapter;
    //fragment
    private HotFragment mhotFragment;
    private EdtioFragment medtioFragment;
    private GangmanFragment mgangmanfragment;
    private GengxinFragment mgengxinfragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tuijian, container, false);
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
        mvp = (ViewPager) getActivity().findViewById(R.id.mvp);
        //设置要缓存的页面
        mvp.setOffscreenPageLimit(4);
        mtabLayout= (TabLayout) getActivity().findViewById(R.id.tab_FindFragment_title);
        mhotFragment=new HotFragment();
        medtioFragment=new EdtioFragment();
        mgangmanfragment=new GangmanFragment();
        mgengxinfragment=new GengxinFragment();

    }

    @Override
    public void initdata() {
        //加载4个fragment
        myFragmentlist=new ArrayList<Fragment>();
        myFragmentlist.add(mhotFragment);
        myFragmentlist.add(medtioFragment);
        myFragmentlist.add(mgangmanfragment);
        myFragmentlist.add(mgengxinfragment);
        //加载TAB列表名
        list_Title= new ArrayList<>();
        list_Title.add("热门连载");
        list_Title.add("小编推荐");
        list_Title.add("精彩港漫");
        list_Title.add("最近更新");
        //设置Tablayout模式
        mtabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    public void initviewoper() {
        //为tablayout添加名称
        mtabLayout.addTab(mtabLayout.newTab().setText(list_Title.get(0)));
        mtabLayout.addTab(mtabLayout.newTab().setText(list_Title.get(0)));
        mtabLayout.addTab(mtabLayout.newTab().setText(list_Title.get(0)));
        mtabLayout.addTab(mtabLayout.newTab().setText(list_Title.get(0)));
        adapter=new TuijianAdapter(getActivity().getSupportFragmentManager(),myFragmentlist,list_Title);
        //mvp加载adapter
        mvp.setAdapter(adapter);
        //tablayout加载viewpage
        mtabLayout.setupWithViewPager(mvp);
    }
}
