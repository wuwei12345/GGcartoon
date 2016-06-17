package ggcartoon.yztc.com.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ggcartoon.yztc.com.Bean.ManHuaZhangJieBean;
import ggcartoon.yztc.com.ggcartoon.R;

/**
 * Created by Administrator on 2016/5/13.
 */
public class ManHuaZhangJieAdapter extends RecyclerView.Adapter<ManHuaZhangJieAdapter.myViewHolder>{
    private List<ManHuaZhangJieBean.DataBean> list;

    public ManHuaZhangJieAdapter(List<ManHuaZhangJieBean.DataBean> list) {
        this.list = list;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        myViewHolder viewHolder=new myViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.mhzj_lv_item,parent,false));
        return viewHolder;
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
    public void onBindViewHolder(final myViewHolder holder, int position) {
        holder.tv.setText(list.get(position).getTitle());
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(holder.itemView,holder.getLayoutPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView tv;
       public myViewHolder(View itemView) {
           super(itemView);
           tv= (TextView) itemView.findViewById(R.id.title);
       }
   }
}
