package ggcartoon.yztc.com.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/4/25.
 */
public class TuijianAdapter extends FragmentPagerAdapter {
    private List<Fragment> list;//fragemtn列表
    private List<String> list_Title;//列表名
    public TuijianAdapter(FragmentManager fm, List<Fragment> list,List<String> list_Title) {
        super(fm);
        this.list=list;
        this.list_Title=list_Title;
    }

    @Override
    public Fragment getItem(int position) {
        return list!=null?list.get(position):null;
    }

    @Override
    public int getCount() {
        return list_Title!=null?list_Title.size():0;
    }
    //此方法用来显示tab上的名字
    @Override
    public CharSequence getPageTitle(int position) {
        return list_Title.get(position%list_Title.size());
    }
}
