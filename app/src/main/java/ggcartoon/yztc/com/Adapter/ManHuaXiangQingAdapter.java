package ggcartoon.yztc.com.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import ggcartoon.yztc.com.Bean.ManHuaXiangQing;
import ggcartoon.yztc.com.ggcartoon.R;

/**
 * Created by Administrator on 2016/5/4.
 */
public class ManHuaXiangQingAdapter extends RecyclerView.Adapter<ManHuaXiangQingAdapter.MyViewHolder> {
    List<ManHuaXiangQing.DataBean.ComicSrcBean> list;

    public ManHuaXiangQingAdapter(List<ManHuaXiangQing.DataBean.ComicSrcBean> list) {
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_manhuaxiangqing, parent,
                false));
        return holder;
    }

    //单击事件
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        ManHuaXiangQing.DataBean.ComicSrcBean comicSrcBean=list.get(position);
        //把时间转换为字串
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        long time=new Long(comicSrcBean.getLastCharpterUpdateTime());
        String times=format.format(time*1000);
        holder.tv_name.setText(comicSrcBean.getTitle());
        holder.tv_where.setText(comicSrcBean.getLastCharpterTitle());
        holder.tv_time.setText(times);

        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(holder.itemView, holder.getLayoutPosition());
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickLitener.onItemLongClick(holder.itemView, holder.getLayoutPosition());
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_where, tv_time;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_mhxq_name);
            tv_time = (TextView) itemView.findViewById(R.id.tv_mhxq_time);
            tv_where = (TextView) itemView.findViewById(R.id.tv_mhxq_where);
        }
    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHold viewHold;
//        if (convertView==null){
//            viewHold=new ViewHold();convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_manhuaxiangqing,null);
//            viewHold.tv_name= (TextView) convertView.findViewById(R.id.tv_mhxq_name);
//            viewHold.tv_time= (TextView) convertView.findViewById(R.id.tv_mhxq_time);
//            viewHold.tv_where= (TextView) convertView.findViewById(R.id.tv_mhxq_where);
//            convertView.setTag(viewHold);
//        }else{
//            viewHold= (ViewHold) convertView.getTag();
//        }
//        ManHuaXiangQing.DataBean.ComicSrcBean comicSrcBean=list.get(position);
//        //把时间转换为字串
//        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
//        long time=new Long(comicSrcBean.getLastCharpterUpdateTime());
//        String times=format.format(time*1000);
//        viewHold.tv_name.setText(comicSrcBean.getTitle());
//        viewHold.tv_where.setText(comicSrcBean.getLastCharpterTitle());
//        viewHold.tv_time.setText(times);
//        return convertView;
//    }
//    class ViewHold{
//        TextView tv_name,tv_where,tv_time;
//    }
}
