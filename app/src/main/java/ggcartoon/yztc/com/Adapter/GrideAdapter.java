package ggcartoon.yztc.com.Adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ggcartoon.yztc.com.Bean.GridBean;
import ggcartoon.yztc.com.ggcartoon.R;

/**
 * Created by Administrator on 2016/4/28.
 */
public class GrideAdapter extends BaseAdapter {
    List<GridBean.DataBean> list;

    public GrideAdapter(List<GridBean.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
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
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.gridview_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        //图片渐进式加载
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(list.get(position).getThumb()))
                .setProgressiveRenderingEnabled(true).build();
        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder().setImageRequest(request)
                .setOldController(holder.ivThumb.getController()).build();
        holder.ivThumb.setController(controller);
        holder.tvName.setText(list.get(position).getLastCharpter().getTitle());
        holder.tvTitle.setText(list.get(position).getTitle());
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.iv_thumb)
        SimpleDraweeView ivThumb;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_name)
        TextView tvName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

//    class ViewHolder {
//        SimpleDraweeView ivThumb;
//        TextView tvTitle, tvName;
//    }
//    @Override
//    public HotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        HotViewHolder holder = new HotViewHolder(LayoutInflater.from(parent.getContext()).
//                inflate(R.layout.gridview_item, parent, false));
//        return holder;
//    }
//
//    @Override
//    public void onBindViewHolder(final HotViewHolder holder, int position) {
//        //图片渐进式加载
//        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(list.get(position).getThumb()))
//                .setProgressiveRenderingEnabled(true).build();
//        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder().setImageRequest(request)
//                .setOldController(holder.ivThumb.getController()).build();
//        holder.ivThumb.setController(controller);
//        holder.tvName.setText(list.get(position).getLastCharpter().getTitle());
//        holder.tvTitle.setText(list.get(position).getTitle());
//        if (monItemClickLinener != null) {
//            holder.ivThumb.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    monItemClickLinener.onItemClick(holder.itemView, holder.getLayoutPosition());
//                }
//            });
//
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return list!=null?list.size():0;
//    }
//
//    //单击事件
//    public interface onItemClickLintener {
//        void onItemClick(View view, int position);
//
//        void onItemLongClick(View view, int Position);
//    }
//
//    private onItemClickLintener monItemClickLinener;
//
//    public void setonItemClickLintener(onItemClickLintener monItemClickLinener) {
//        this.monItemClickLinener = monItemClickLinener;
//    }
//
//    public class HotViewHolder extends RecyclerView.ViewHolder {
//        SimpleDraweeView ivThumb;
//        TextView tvTitle, tvName;
//
//        public HotViewHolder(View inflate) {
//            super(inflate);
//            ivThumb = (SimpleDraweeView) inflate.findViewById(R.id.iv_thumb);
//            tvTitle = (TextView) inflate.findViewById(R.id.tv_title);
//            tvName = (TextView) inflate.findViewById(R.id.tv_name);
//        }
//    }


}
