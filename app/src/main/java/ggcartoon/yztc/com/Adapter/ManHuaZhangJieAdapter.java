package ggcartoon.yztc.com.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ggcartoon.yztc.com.Bean.ManHuaZhangJieBean;
import ggcartoon.yztc.com.ggcartoon.R;

/**
 * Created by Administrator on 2016/5/13.
 */
public class ManHuaZhangJieAdapter extends BaseAdapter {
    private List<ManHuaZhangJieBean.DataBean> list;
    public void setDatas(List<ManHuaZhangJieBean.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return list!=null?list.size():0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder VH;
        if (convertView==null){
            VH=new ViewHolder();
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.mhzj_lv_item,null);
            VH.titlebar= (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(VH);
        }else{
            VH= (ViewHolder) convertView.getTag();
        }
            ManHuaZhangJieBean.DataBean ZJ = list.get(position);
            VH.titlebar.setText(ZJ.getTitle());
        return convertView;
    }
    class ViewHolder{
    TextView titlebar;
    }
}
