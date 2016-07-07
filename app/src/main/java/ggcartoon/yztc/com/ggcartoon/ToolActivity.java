package ggcartoon.yztc.com.ggcartoon;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ggcartoon.yztc.com.Adapter.ToolAdapter;
import ggcartoon.yztc.com.View.DividerItemDecoration;
import ggcartoon.yztc.com.View.SelectPicPopupWindow;
import ggcartoon.yztc.com.View.SwipBackActivityS;
//测试界面
public class ToolActivity extends SwipBackActivityS implements View.OnTouchListener,GestureDetector.OnGestureListener{
    ToolAdapter adapter;
    List<String> list;
    SelectPicPopupWindow window;
    GestureDetector gestureDetector;
    CoordinatorLayout COOR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool);
        gestureDetector=new GestureDetector(this);
        COOR= (CoordinatorLayout) findViewById(R.id.main);
        COOR.setOnTouchListener(this);
        COOR.setLongClickable(true);
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
//                window=new SelectPicPopupWindow(ToolActivity.this);
//                window.showAtLocation((ToolActivity.this.findViewById(R.id.main)),Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
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

private View.OnClickListener ItemClick=new View.OnClickListener() {
    @Override
    public void onClick(View v) {
            switch(v.getId()){
                default:
                    break;
            }         
    }
};

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {


        if (e1.getX()-e2.getX()>50&& Math.abs(velocityX)>0){
            Toast.makeText(this, "向左手势", Toast.LENGTH_SHORT).show();
        }else if (e2.getX()-e1.getX()>50&& Math.abs(velocityX)>0){
            Toast.makeText(this, "向右手势", Toast.LENGTH_SHORT).show();
        }
        return false;
    }



}
