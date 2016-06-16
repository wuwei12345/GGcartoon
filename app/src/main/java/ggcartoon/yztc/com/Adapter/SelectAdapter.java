package ggcartoon.yztc.com.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import ggcartoon.yztc.com.Bean.SelectBean;
import ggcartoon.yztc.com.ggcartoon.R;

/**
 * Created by Administrator on 2016/5/31.
 */
public class SelectAdapter extends BaseAdapter {
    List<SelectBean.DataBean> list;

    public void setData(List<SelectBean.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
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
        ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gengxin, null);
            vh.gengxinIv = (ImageView) convertView.findViewById(R.id.gengxin_iv);
            vh.gengxinTitile = (TextView) convertView.findViewById(R.id.gengxin_titile);
            vh.gengxinType = (TextView) convertView.findViewById(R.id.gengxin_type);
            vh.gengxinUpdate = (TextView) convertView.findViewById(R.id.gengxin_update);
            vh.gengxinZuozhe = (TextView) convertView.findViewById(R.id.gengxin_zuozhe);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.gengxinTitile.setText(list.get(position).getTitle());
        vh.gengxinZuozhe.setText(list.get(position).getAuthorName());
        vh.gengxinType.setText(list.get(position).getComicType());
        if (list.get(position).getLastCharpter()==null) {
            vh.gengxinUpdate.setText("");
        } else {
            vh.gengxinUpdate.setText(list.get(position).getLastCharpter().getTitle());
        }
        Picasso.with(parent.getContext()).load(list.get(position).getThumb()).into(vh.gengxinIv);
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.gengxin_iv)
        ImageView gengxinIv;
        @Bind(R.id.gengxin_titile)
        TextView gengxinTitile;
        @Bind(R.id.gengxin_zuozhe)
        TextView gengxinZuozhe;
        @Bind(R.id.gengxin_type)
        TextView gengxinType;
        @Bind(R.id.gengxin_update)
        TextView gengxinUpdate;

    }
}
