package ggcartoon.yztc.com.Adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import ggcartoon.yztc.com.Bean.GengxinBean;
import ggcartoon.yztc.com.ggcartoon.R;

/**
 * Created by Administrator on 2016/5/25.
 */
public class GengxinAdapter extends RecyclerView.Adapter<GengxinAdapter.GengxinViewHolder> {
    private List<GengxinBean.DataBean> list;

    public void setData(List<GengxinBean.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();//强制刷新每个item内容
    }

    @Override
    public GengxinViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        GengxinViewHolder holder = new GengxinViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_gengxin, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final GengxinViewHolder holder, int position) {
        holder.mhlb_title.setText(list.get(position).getTitle());
        holder.mhlb_zuozhe.setText(list.get(position).getAuthorName());
        holder.mhlb_type.setText(list.get(position).getComicType());
        holder.mhlb_update.setText(list.get(position).getLastCharpter()!=null?list.get(position).getLastCharpter().getTitle():" ");
        holder.mhlb_iv.setImageURI(Uri.parse(list.get(position).getThumb()));
        if (monItemClickLinener!=null){
            holder.mglb_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    monItemClickLinener.onItemClick(holder.itemView,holder.getLayoutPosition());
                }
            });
        }
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

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public class GengxinViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout mglb_layout;
        private SimpleDraweeView mhlb_iv;
        private ImageView mhlb_goto;
        private TextView mhlb_title, mhlb_zuozhe, mhlb_type, mhlb_update;
        public GengxinViewHolder(View itemView) {
            super(itemView);
            mglb_layout= (RelativeLayout) itemView.findViewById(R.id.gengxin_layout);
            mhlb_iv = (SimpleDraweeView) itemView.findViewById(R.id.gengxin_iv);
            mhlb_goto = (ImageView) itemView.findViewById(R.id.gengxin_goto);
            mhlb_title = (TextView) itemView.findViewById(R.id.gengxin_titile);
            mhlb_zuozhe = (TextView) itemView.findViewById(R.id.gengxin_zuozhe);
            mhlb_type = (TextView) itemView.findViewById(R.id.gengxin_type);
            mhlb_update = (TextView) itemView.findViewById(R.id.gengxin_update);
        }
    }

//
//
//
//    @Override
//    public int getCount() {
//        return list != null ? list.size() : 0;
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return list.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder vh;
//        if (convertView == null) {
//            vh = new ViewHolder();
//            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gengxin, null);
//
//            convertView.setTag(vh);
//        } else {
//            vh = (ViewHolder) convertView.getTag();
//        }
//
//        return convertView;
//
//    }
//
//    class ViewHolder {
//
//    }


}
