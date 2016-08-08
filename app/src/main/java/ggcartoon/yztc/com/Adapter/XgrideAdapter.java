package ggcartoon.yztc.com.Adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.List;

import ggcartoon.yztc.com.Bean.GridBean;
import ggcartoon.yztc.com.ggcartoon.R;

/**
 * Created by Administrator on 2016/8/3.
 */
public class XgrideAdapter extends RecyclerView.Adapter<XgrideAdapter.HotViewHolder>{
    List<GridBean.DataBean> list;

    public XgrideAdapter(List<GridBean.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    @Override
    public HotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        HotViewHolder holder = new HotViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.gridview_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final HotViewHolder holder, int position) {
        //图片渐进式加载
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(list.get(position).getThumb()))
                .setProgressiveRenderingEnabled(true).build();
        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder().setImageRequest(request)
                .setOldController(holder.ivThumb.getController()).build();
        holder.ivThumb.setController(controller);
        holder.tvName.setText(list.get(position).getLastCharpter().getTitle());
        holder.tvTitle.setText(list.get(position).getTitle());
        if (monItemClickLinener != null) {
            holder.ivThumb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    monItemClickLinener.onItemClick(holder.itemView, holder.getLayoutPosition());
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return list!=null?list.size():0;
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

    public class HotViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView ivThumb;
        TextView tvTitle, tvName;

        public HotViewHolder(View inflate) {
            super(inflate);
            ivThumb = (SimpleDraweeView) inflate.findViewById(R.id.iv_thumb);
            tvTitle = (TextView) inflate.findViewById(R.id.tv_title);
            tvName = (TextView) inflate.findViewById(R.id.tv_name);
        }
    }
}
