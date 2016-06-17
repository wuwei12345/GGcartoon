package ggcartoon.yztc.com.ggcartoon;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ggcartoon.yztc.com.Adapter.ManHuaXiangQingAdapter;
import ggcartoon.yztc.com.Bean.ManHuaXiangQing;
import ggcartoon.yztc.com.Bean.ShouCang;
import ggcartoon.yztc.com.View.DividerItemDecoration;
import ggcartoon.yztc.com.View.SelectPicPopupWindow;
import ggcartoon.yztc.com.View.SwipBackActivityS;
import ggcartoon.yztc.com.initerface.Initerface;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//漫画详情页面
public class ManHuaXiangQingActivity extends SwipBackActivityS implements Initerface {

    @Bind(R.id.mhxq_image)
    ImageView mhxqImage;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @Bind(R.id.fab_detail)
    FloatingActionButton fabDetail;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    //List<bean>
    private ManHuaXiangQing.DataBean MH;
    //adapter
    private ManHuaXiangQingAdapter adapter;
    //章节列表窗口
    SelectPicPopupWindow window;
    //地址
    String path;
    String TitleName;
    String Id;
    //列表item的个数
    int lvcount = 0;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    //装载adapter
                    adapter = new ManHuaXiangQingAdapter(MH.getComicSrc());
                    recyclerView.setAdapter(adapter);
                    collapsingToolbarLayout.setTitle(MH.getTitle());
                    //通过CollapsingToolbarLayout修改字体颜色
                    collapsingToolbarLayout.setBackgroundColor(Color.WHITE);
                    collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);//设置还没收缩时状态下字体颜色
                    collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后Toolbar上字体的颜色
                    //漫画图片
                    Picasso.with(ManHuaXiangQingActivity.this).load(MH.getThumb()).into(mhxqImage);
                    adapter.setOnItemClickLitener(new ManHuaXiangQingAdapter.OnItemClickLitener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            window=new SelectPicPopupWindow(ManHuaXiangQingActivity.this,MH.getComicSrc().get(position).getId(),TitleName,Id);
                            window.showAtLocation((ManHuaXiangQingActivity.this.findViewById(R.id.mains)), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
                        }

                        @Override
                        public void onItemLongClick(View view, int position) {

                        }
                    });
                    //设置标题
//                    title.setText(MH.getTitle());
////                    tv_pingluncount.setText(MH.getTucaos());
//                    //作者
//                    zuozhe.setText(MH.getAuthorName());
//                    //类型
//                    leixing.setText(MH.getComicType());
//                    //地区
//                    diqi.setText(MH.getAreaName());
//                    //简介
//                    jianjie.setText(MH.getIntro());
                    //Item点击事件
//                    lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            //把单击的item的值存储
//                            lvcount = position;
//                            //点击携带相应数据跳转到漫画页面
//                            Intent intent = new Intent(ManHuaXiangQingActivity.this, ManHuaZhangJieActivity.class);
//                            intent.putExtra("id", MH.getComicSrc().get(position).getId());
//                            intent.putExtra("titlebar", TitleName);
//                            intent.putExtra("comIcid", Id);
////                            System.out.println("--->"+MH.getComicSrc().get(position).getId()+"--->"+TitleName+"--->"+Id);
//                            startActivity(intent);
//                        }
//                    });
                    break;
                case 2:
                    Toast.makeText(ManHuaXiangQingActivity.this, "网络获取失败", Toast.LENGTH_SHORT).show();
                    break;
                default:

                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hua_xiang_qing);
        ButterKnife.bind(this);
        initview();
        initdata();
        initviewoper();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.pinglun:
                //携带数据跳转到评论页面
                Intent intent = new Intent(ManHuaXiangQingActivity.this, PingLunListActivity.class);
                //如果标题和Id不为空则可以跳转到评论页面
                if (!(MH.getTitle() == null || MH.getTitle().equals("") || Id.equals(""))) {
                    intent.putExtra("titlebar", MH.getTitle());
                    intent.putExtra("comIcid", Id);
                    startActivity(intent);
                }
                break;
            case R.id.jianjie:
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle(MH.getTitle());
                dialog.setMessage(MH.getIntro());
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //关闭dialog
                        dialog.dismiss();
                    }
                });
                dialog.create().show();
                break;
            default:

                break;
        }
        return false;
    }

    /*
    初始化控件
     */
    @Override
    public void initview() {
        Intent intent = getIntent();
        //获取漫画id和名字
        TitleName = intent.getStringExtra("title");
        Id = intent.getStringExtra("comicId");
        //对应ID的路径
        path = "http://csapi.dm300.com:21889/android/comic/info?comicsrcid=0&comicid=" + Id;
        mhxqImage = (ImageView) findViewById(R.id.mhxq_image);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        fabDetail = (FloatingActionButton) findViewById(R.id.fab_detail);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mhxqImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(ManHuaXiangQingActivity.this,ToolActivity.class);
                startActivity(intent1);
            }
        });
    }

    @Override
    public void initdata() {
        setSupportActionBar(toolbar);
        //回执返回图标
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //单击返回
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //修改背景颜色
        collapsingToolbarLayout.setBackgroundColor(Color.WHITE);
        //设置分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        //布局为线性
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void initviewoper() {
        //访问网络数据的方法
        run(path);
        //收藏
        fabDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //收藏漫画
                try {
                    //设置标记位
                    boolean flag = true;
                    //获取数据库的内容
                    List<ShouCang> SClist = MainActivity.dbUtils.findAll(ShouCang.class);
                    //如果不为空则挨个偏离数据库中的内容是否与要收藏的内容重复，如果重复则标志位flag为false不能收藏否则可以收藏
                    if (SClist != null) {
                        for (ShouCang SC : SClist) {
                            if (SC.getTitle().equals(MH.getTitle())) {
                                Toast.makeText(ManHuaXiangQingActivity.this, "该漫画已收藏", Toast.LENGTH_SHORT).show();
                                flag = false;
                            }
                        }
                        if (flag) {
                            ShouCang shoucang = new ShouCang(Id, MH.getTitle(), MH.getUpdateTime(), MH.getThumb(), MH.getComicSrc().get(lvcount).getLastCharpterTitle());
                            MainActivity.dbUtils.save(shoucang);
                            Toast.makeText(ManHuaXiangQingActivity.this, "已收藏", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        ShouCang shoucang = new ShouCang(Id, MH.getTitle(), MH.getUpdateTime(), MH.getThumb(), MH.getComicSrc().get(lvcount).getLastCharpterTitle());
                        MainActivity.dbUtils.save(shoucang);
                        Toast.makeText(ManHuaXiangQingActivity.this, "已收藏", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    //加载网络数据
    void run(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(2);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                try {
                    //json解析操作
                    JSONObject jsonobj = new JSONObject(json);
                    JSONObject jsonobj2 = jsonobj.getJSONObject("data");
                    MH = JSON.parseObject(jsonobj2.toString(), ManHuaXiangQing.DataBean.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(1);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

}
