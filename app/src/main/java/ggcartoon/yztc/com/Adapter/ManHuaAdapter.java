package ggcartoon.yztc.com.Adapter;

import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.List;

import ggcartoon.yztc.com.ggcartoon.R;

/**
 * Created by Administrator on 2016/7/19.
 */
public class ManHuaAdapter extends RecyclerView.Adapter<ManHuaAdapter.MyViewHolder> {
    List<String> list;

    public ManHuaAdapter(List<String> list) {
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.manhua_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        //图片渐进加载
        ImageRequest request= ImageRequestBuilder.newBuilderWithSource(Uri.parse(list.get(position).toString()))
                .setProgressiveRenderingEnabled(true).build();
        PipelineDraweeController controller= (PipelineDraweeController) Fresco.newDraweeControllerBuilder().setImageRequest(request)
                .setOldController(holder.simpleDraweeView.getController()).build();
        holder.simpleDraweeView.setController(controller);
        if (mOnItemClickLitener!=null){
            holder.simpleDraweeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(holder.itemView,holder.getLayoutPosition());
                }
            });
            holder.simpleDraweeView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickLitener.onItemLongClick(holder.itemView,holder.getLayoutPosition());
                    return true;
                }
            });
        }
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
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView simpleDraweeView;
        //加一个进度条
        GenericDraweeHierarchy builder=new GenericDraweeHierarchyBuilder(Resources.getSystem()).
                setProgressBarImage(new ProgressBarDrawable()).build();
        public MyViewHolder(View itemView) {
            super(itemView);
            simpleDraweeView= (SimpleDraweeView) itemView.findViewById(R.id.simpleDrawee);
            simpleDraweeView.setHierarchy(builder);
        }
    }
}
