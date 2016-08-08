package ggcartoon.yztc.com.Adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import ggcartoon.yztc.com.Bean.TypeBean;
import ggcartoon.yztc.com.ggcartoon.R;

/**
 * Created by Administrator on 2016/5/30.
 */
public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.ViewHolder> {
    List<TypeBean.DataBean> list;

    public TypeAdapter(List<TypeBean.DataBean> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder vh = new ViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.type_list_item, parent, false));
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.roundIv.setImageURI(Uri.parse(list.get(position).getThumb()));
            holder.typeTv.setText(list.get(position).getTitle());
        if (monItemClickLinener!=null){
            holder.roundIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    monItemClickLinener.onItemClick(holder.itemView,holder.getLayoutPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }
    //单击事件
    public interface onItemClickLintener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int Position);
    }

    private onItemClickLintener monItemClickLinener;

    public void setonItemClickLintener(onItemClickLintener monItemClickLinener) {
        this.monItemClickLinener = monItemClickLinener;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView roundIv;
        TextView typeTv;
        public ViewHolder(View itemView) {
            super(itemView);
            roundIv= (SimpleDraweeView) itemView.findViewById(R.id.round_iv);
            typeTv= (TextView) itemView.findViewById(R.id.type_tv);
        }
    }
}
