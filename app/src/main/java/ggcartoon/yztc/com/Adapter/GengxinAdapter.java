package ggcartoon.yztc.com.Adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import ggcartoon.yztc.com.Bean.GengxinBean;
import ggcartoon.yztc.com.ggcartoon.R;

/**
 * Created by Administrator on 2016/5/25.
 */
public class GengxinAdapter extends BaseAdapter {
    private List<GengxinBean.DataBean> list;

    public void setData(List<GengxinBean.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();//强制刷新每个item内容
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
            vh.mhlb_iv = (SimpleDraweeView) convertView.findViewById(R.id.gengxin_iv);
            vh.mhlb_goto = (ImageView) convertView.findViewById(R.id.gengxin_goto);
            vh.mhlb_title = (TextView) convertView.findViewById(R.id.gengxin_titile);
            vh.mhlb_zuozhe = (TextView) convertView.findViewById(R.id.gengxin_zuozhe);
            vh.mhlb_type = (TextView) convertView.findViewById(R.id.gengxin_type);
            vh.mhlb_update = (TextView) convertView.findViewById(R.id.gengxin_update);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.mhlb_title.setText(list.get(position).getTitle());
        vh.mhlb_zuozhe.setText(list.get(position).getAuthorName());
        vh.mhlb_type.setText(list.get(position).getComicType());
        vh.mhlb_update.setText(list.get(position).getLastCharpter().getTitle());
        vh.mhlb_iv.setImageURI(Uri.parse(list.get(position).getThumb()));
        return convertView;

    }

    class ViewHolder {
        private SimpleDraweeView mhlb_iv;
        private ImageView mhlb_goto;
        private TextView mhlb_title, mhlb_zuozhe, mhlb_type, mhlb_update;
    }
}
