package ggcartoon.yztc.com.Adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import ggcartoon.yztc.com.Bean.GridBean;
import ggcartoon.yztc.com.ggcartoon.R;

/**
 * Created by Administrator on 2016/4/28.
 */
public class GrideAdapter extends BaseAdapter {
    List<GridBean.DataBean> list;
    public void SetDatas(List<GridBean.DataBean> list){
        this.list=list;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return list!=null?list.size():0;
    }

    @Override
    public Object getItem(int position) {
        return list!=null?list.get(position):null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            holder=new ViewHolder();
        convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.gridview_item,null);
            holder.ivThumb= (ImageView) convertView.findViewById(R.id.iv_thumb);
            holder.tvTitle= (TextView) convertView.findViewById(R.id.tv_title);
            holder.tvName= (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        }else{
        holder= (ViewHolder) convertView.getTag();
        }
    GridBean.DataBean gridBean=list.get(position);
        holder.tvTitle.setText(gridBean.getTitle());
        holder.tvName.setText(gridBean.getLastCharpter().getTitle());
        String thumb=gridBean.getThumb();
//        Picasso.with(parent.getContext()).load(thumb).memoryPolicy( MemoryPolicy.NO_STORE).networkPolicy( NetworkPolicy.OFFLINE).priority(Picasso.Priority.HIGH).into(holder.ivThumb);
       // 设置加载前的图片，加载中的图片，设置下载的图片是否缓存在内存中，设置图片解码的类型
        DisplayImageOptions options = new DisplayImageOptions.Builder().
                showImageOnLoading(R.drawable.icon_error_warn).
                showImageOnFail(R.drawable.loading_1).cacheInMemory(true).
                bitmapConfig(Bitmap.Config.RGB_565).build();
        ImageLoader.getInstance().displayImage(thumb, holder.ivThumb, options);//显示图片
//        Picasso.with(parent.getContext()).load(thumb).into(holder.ivThumb);
        return convertView;
    }
    class ViewHolder{
        ImageView ivThumb;
        TextView tvTitle,tvName;
    }
}
