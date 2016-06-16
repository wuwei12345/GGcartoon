package ggcartoon.yztc.com.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import ggcartoon.yztc.com.Bean.TypeBean;
import ggcartoon.yztc.com.ggcartoon.R;

/**
 * Created by Administrator on 2016/5/30.
 */
public class TypeAdapter extends BaseAdapter {
    private List<TypeBean.DataBean> list;

    public void setData(List<TypeBean.DataBean> list) {
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
            vh=new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.type_list_item, null);
            vh.roundIv= (RoundedImageView) convertView.findViewById(R.id.round_iv);
            vh.typeTv= (TextView) convertView.findViewById(R.id.type_tv);
            convertView.setTag(vh);
        } else {
            vh= (ViewHolder) convertView.getTag();
        }
        vh.typeTv.setText(list.get(position).getTitle());
        Picasso.with(parent.getContext()).load(list.get(position).getThumb()).into(vh.roundIv);
        return convertView;
    }

     class ViewHolder {
        @Bind(R.id.round_iv)
        RoundedImageView roundIv;
        @Bind(R.id.type_tv)
        TextView typeTv;
    }
}
