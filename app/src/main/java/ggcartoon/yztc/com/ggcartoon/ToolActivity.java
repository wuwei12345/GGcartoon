package ggcartoon.yztc.com.ggcartoon;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ggcartoon.yztc.com.Adapter.ToolAdapter;
import ggcartoon.yztc.com.View.DividerItemDecoration;
import ggcartoon.yztc.com.View.SwipBackActivityS;

public class ToolActivity extends SwipBackActivityS {
    ToolAdapter adapter;
    List<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上则不会显示
        CollapsingToolbarLayout mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        mCollapsingToolbarLayout.setTitle("CollapsingToolbarLayout");
        //通过CollapsingToolbarLayout修改字体颜色
        mCollapsingToolbarLayout.setBackgroundColor(Color.WHITE);
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);//设置还没收缩时状态下字体颜色
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.GREEN);//设置收缩后Toolbar上字体的颜色
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        for (int i = 0; i <= 20; i++) {
            list.add("测试" + i);
        }
        recyclerView.setAdapter(adapter = new ToolAdapter(list,this));
        adapter.setOnItemClickLitener(new ToolAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(ToolActivity.this,"点击了第"+position,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(ToolActivity.this,"长按了第"+position,Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.pinglun:
                Toast.makeText(ToolActivity.this,"评论被单击",Toast.LENGTH_SHORT).show();
                break;
            case R.id.jianjie:
                Toast.makeText(ToolActivity.this,"漫画简介被单击",Toast.LENGTH_SHORT).show();
                break;
            default:

                break;
        }
        return false;
    }




    //    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
//        List<Integer> heights;
//        List<String> list;
//        public HomeAdapter(List<String> list) {
//            this.list = list;
//            getRandomHeight(list);
//        }
//
//        @Override
//        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
//                    ToolActivity.this).inflate(R.layout.item_gengxin, parent,
//                    false));
//            return holder;
//        }
//        private void getRandomHeight(List<String> lists){//得到随机item的高度
//            heights = new ArrayList<>();
//            for (int i = 0; i < lists.size(); i++) {
//                heights.add((int)(200+Math.random()*400));
//            }
//        }
//        @Override
//        public void onBindViewHolder(MyViewHolder holder, int position) {
//            ViewGroup.LayoutParams params =  holder.itemView.getLayoutParams();//得到item的LayoutParams布局参数
//            params.height = heights.get(position);//把随机的高度赋予item布局
//            holder.itemView.setLayoutParams(params);//把params设置给item布局
//            holder.tv.setText(list.get(position));
//        }
//        @Override
//        public int getItemCount() {
//            return list.size();
//        }
//
//        //define interface
//
//        class MyViewHolder extends RecyclerView.ViewHolder {
//
//            TextView tv;
//            public MyViewHolder(View view) {
//                super(view);
//                tv = (TextView) view.findViewById(R.id.gengxin_titile);
//            }
//        }


//    }
}
