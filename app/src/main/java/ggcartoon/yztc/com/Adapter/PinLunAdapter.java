package ggcartoon.yztc.com.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ggcartoon.yztc.com.Bean.PinLunBean;
import ggcartoon.yztc.com.ggcartoon.R;

/**
 * Created by Administrator on 2016/5/9.
 */
public class PinLunAdapter extends RecyclerView.Adapter<PinLunAdapter.ViewHolder> {
    private List<PinLunBean.DataBean.CommentListBean> mlist;

    public void setDate(List<PinLunBean.DataBean.CommentListBean> list) {
        this.mlist = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.pinlun_list_item, null));
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder viewhold, int position) {
        PinLunBean.DataBean.CommentListBean PL=mlist.get(position);
        viewhold.nickName.setText(PL.getNickname());
        viewhold.content.setText(PL.getContent());
        viewhold.replayNo.setText(PL.getReply_no());
        String posttime=PL.getPost_time();
        long parseLong = Long.parseLong(posttime);
        long time = System.currentTimeMillis() - (parseLong * 1000);
        long mill = (long) Math.ceil(time / 1000);
        long minute = (long) Math.ceil(time / 60 / 1000.0f);
        long hour = (long) Math.ceil(time / 60 / 60 / 1000.0f);
        long day = (long) Math.ceil(time / 24 / 60 / 60 / 1000.0f);
        if (day - 1 > 0) {
            viewhold.postTime.setText(day + "天前");
        } else if (hour - 1 > 0) {
            if (hour >= 24) {
                viewhold.postTime.setText("一天前");
            } else {
                viewhold.postTime.setText(hour + "小时前");
            }
        } else if (minute - 1 > 0) {
            if (minute == 60) {
                viewhold.postTime.setText("一小时前");
            } else {
                viewhold.postTime.setText(minute + "分钟前");
            }
        } else if (mill - 1 > 0) {
            if (mill == 60) {
                viewhold.postTime.setText("一分钟前");
            } else {
                viewhold.postTime.setText(mill + "秒前");
            }
        } else {
            viewhold.postTime.setText("刚刚");
        }
        String userthmb=PL.getUser_thumb();
        if (!userthmb.equals("")){
            Picasso.with(viewhold.itemView.getContext()).load(userthmb).into(viewhold.userThumb);
        }
    }

    @Override
    public int getItemCount() {
        return mlist != null ? mlist.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView userThumb;
        TextView replayNo, nickName, content, postTime;

        public ViewHolder(View itemView) {
            super(itemView);
            userThumb = (ImageView) itemView.findViewById(R.id.user_thumb);
            nickName = (TextView) itemView.findViewById(R.id.nick_name);
            postTime = (TextView) itemView.findViewById(R.id.post_time);
            replayNo = (TextView) itemView.findViewById(R.id.replay_no);
            content = (TextView) itemView.findViewById(R.id.content);
        }
    }

//    @Override
//    public int getCount() {
//        return mlist!=null?mlist.size():0;
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return mlist!=null?mlist.get(position):null;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        Viewhold viewhold;
//        if (convertView==null){
//            viewhold=new Viewhold();
//            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.pinlun_list_item,null);
//            viewhold.userThumb = (ImageView) convertView.findViewById(R.id.user_thumb);
//            viewhold.nickName = (TextView) convertView.findViewById(R.id.nick_name);
//            viewhold.postTime = (TextView) convertView.findViewById(R.id.post_time);
//            viewhold.replayNo = (TextView) convertView.findViewById(R.id.replay_no);
//            viewhold.content = (TextView) convertView.findViewById(R.id.content);
//            convertView.setTag(viewhold);
//        }else{
//            viewhold= (Viewhold) convertView.getTag();
//        }
//        PinLunBean.DataBean.CommentListBean PL=mlist.get(position);
//        viewhold.nickName.setText(PL.getNickname());
//        viewhold.content.setText(PL.getContent());
//        viewhold.replayNo.setText(PL.getReply_no());
//        String posttime=PL.getPost_time();
//        long parseLong = Long.parseLong(posttime);
//        long time = System.currentTimeMillis() - (parseLong * 1000);
//        long mill = (long) Math.ceil(time / 1000);
//        long minute = (long) Math.ceil(time / 60 / 1000.0f);
//        long hour = (long) Math.ceil(time / 60 / 60 / 1000.0f);
//        long day = (long) Math.ceil(time / 24 / 60 / 60 / 1000.0f);
//        if (day - 1 > 0) {
//            viewhold.postTime.setText(day + "天前");
//        } else if (hour - 1 > 0) {
//            if (hour >= 24) {
//                viewhold.postTime.setText("一天前");
//            } else {
//                viewhold.postTime.setText(hour + "小时前");
//            }
//        } else if (minute - 1 > 0) {
//            if (minute == 60) {
//                viewhold.postTime.setText("一小时前");
//            } else {
//                viewhold.postTime.setText(minute + "分钟前");
//            }
//        } else if (mill - 1 > 0) {
//            if (mill == 60) {
//                viewhold.postTime.setText("一分钟前");
//            } else {
//                viewhold.postTime.setText(mill + "秒前");
//            }
//        } else {
//            viewhold.postTime.setText("刚刚");
//        }
//        String userthmb=PL.getUser_thumb();
//        if (!userthmb.equals("")){
//            Picasso.with(parent.getContext()).load(userthmb).into(viewhold.userThumb);
//        }
//        return convertView;
//    }
//
//    class Viewhold{
//        ImageView userThumb;
//        TextView replayNo,nickName,content,postTime;
//    }
}
