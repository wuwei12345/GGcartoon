package ggcartoon.yztc.com.ggcartoon;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bm.library.PhotoView;
import com.lidroid.xutils.HttpUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ggcartoon.yztc.com.Bean.ManHuaZhangJieBean;
import ggcartoon.yztc.com.initerface.Initerface;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ManHuaActivity extends Activity implements Initerface {

    @Bind(R.id.vp)
    ViewPager vp;
    @Bind(R.id.tm)
    TextView tm;
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.yeshu)
    TextView yeshu;
    @Bind(R.id.pw)
    TextView pw;
    @Bind(R.id.pow)
    TextView pow;
    TextView MH_title;
    //用来存放图片的列表
    List<PhotoView> list;
    MyAdapter mydaapter;
    //网络请求
    private HttpUtils mhttpUtils;
    //电量
    private BatteryReceiver receiver;
    //bean
    private List<String> MH;
    //上个页面获取的ID
    int id;
    AlertDialog.Builder dialog;
    AlertDialog build;
    PhotoView iv;
    private List<ManHuaZhangJieBean.DataBean> MHZJ;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    list = new ArrayList<>();
                    for (int i = 0; i < MH.size(); i++) {
                        iv = new PhotoView(ManHuaActivity.this);
                        //启动放大缩小
                        iv.enable();
                        //充满全屏
                        iv.setScaleType(ImageView.ScaleType.FIT_XY);
                        try {
                            Picasso.with(ManHuaActivity.this).load(MH.get(i)).into(iv);
                            Thread.sleep(100);
                            list.add(iv);
                            yeshu.setText("1" + "/" + list.size());
                            mydaapter.notifyDataSetChanged();
                            //关闭dialog
                            build.dismiss();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 1:
                    Toast.makeText(ManHuaActivity.this, "网络获取失败", Toast.LENGTH_SHORT).show();
                    //关闭dialog
                    build.dismiss();
                    break;
                default:

                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manhua);
        ButterKnife.bind(this);
        initview();
        initdata();
        initviewoper();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销广播
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
    }

    //初始化控件
    @Override
    public void initview() {
        vp = (ViewPager) findViewById(R.id.vp);
        tm = (TextView) findViewById(R.id.tm);
        time = (TextView) findViewById(R.id.time);
        yeshu = (TextView) findViewById(R.id.yeshu);
        pw = (TextView) findViewById(R.id.pw);
        pow = (TextView) findViewById(R.id.pow);
        MH_title = (TextView) findViewById(R.id.MH_Title);
        //初始化电量监听器
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        receiver = new BatteryReceiver();
        //注册广播接收器
        registerReceiver(receiver, filter);
        //加载提示
        dialog = new AlertDialog.Builder(this);
        dialog.setTitle("提示");
        dialog.setMessage("正在加载...");
        build = dialog.show();
    }

    @Override
    public void initdata() {
        //获取传过来的id
        Intent intent = getIntent();
        //获得选中漫画的ID
        id = intent.getIntExtra("bid", -1);
        System.out.println("-->该漫画ID：" + id);
        Bundle bundle = intent.getExtras();
        MHZJ = (List<ManHuaZhangJieBean.DataBean>) bundle.getSerializable("MHZJ");
    }

    @Override
    public void initviewoper() {
        build.show();
        //接口地址
        String path = "http://csapi.dm300.com:21889/android/comic/charpterinfo?charpterid=" + id;
        //加载系统时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        time.setText(format.format(date));
        //设置漫画标题
        for (int i = 0; i < MHZJ.size(); i++) {
            if (id == MHZJ.get(i).getId()) {
                MH_title.setText(MHZJ.get(i).getTitle());
            }
        }
        //通过接口获取漫画内容
//        downLoad();
        run(path);
        mydaapter = new MyAdapter();
        vp.setAdapter(mydaapter);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int pos = 0;
            int maxpos = 0;
            int currentPageScrollStatus;
            boolean flag=true;
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                System.out.println("------>pos:" + pos + "  ------->maxpos:" + maxpos);
                if (pos == 0) {
                    //如果offsetPixels是0页面也被滑动了，代表在第一页还要往左划
                    if (positionOffsetPixels == 0 && currentPageScrollStatus == 1) {
                        for (int i = 0; i < MHZJ.size(); i++) {
                            //先判断到当前id所对应的章节ID的位置
                            if (MHZJ.get(i).getId() == id) {
                                System.out.println("------>i:"+i+" Mhzj:"+MHZJ.size());
                                if (!(i>=MHZJ.size()-1)) {
                                    if (i + 1 <= MHZJ.size()) {
                                        if (flag) {
                                            //MHZJ.get(i - 1).getId() > MHZJ.get(i).getId()
                                            id = MHZJ.get(i + 1).getId();
                                            System.out.println("------>当前ID:" + MHZJ.get(i).getId() + "------->上一章id:" + id);
                                            initviewoper();
                                            flag=false;
                                        }
                                    }
                                } else {
                                    Toast.makeText(ManHuaActivity.this, "前面没有了", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                } else if (pos == maxpos) {
                    if (positionOffsetPixels == 0 && currentPageScrollStatus == 1) {
                        for (int i = 0; i < MHZJ.size(); i++) {
                            //先判断到当前id所对应的章节ID的位置
                            if (MHZJ.get(i).getId() == id) {
                                if (i != 0) {
                                    if (!(i - 1 < 0)) {
                                        //MHZJ.get(i - 1).getId() > MHZJ.get(i).getId()
                                        System.out.println("------>当前ID:" + MHZJ.get(i).getId() + "------->下一章id:" + MHZJ.get(i - 1).getId());
                                        id = MHZJ.get(i - 1).getId();
                                        initviewoper();
                                    }
                                } else {
                                    Toast.makeText(ManHuaActivity.this, "您已经看完了", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {
                setCurrentPos(position);
                if (position == list.size() - 1) {
                    //设置最后一页的position值
                    maxpos = position;
                }
                yeshu.setText(position + 1 + "/" + list.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //记录page滑动状态，如果滑动了state就是1
                currentPageScrollStatus = state;
            }

            public void setCurrentPos(int position) {
                //设置当前页的position值
                pos = position;
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
                handler.sendEmptyMessage(1);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                try {
                    MH = new ArrayList<>();
                    //json解析
                    JSONObject obj = new JSONObject(json);
                    JSONObject obj2 = obj.getJSONObject("data");
                    JSONArray array = obj2.getJSONArray("addrs");
                    for (int i = 0; i <= array.length() - 1; i++) {
                        MH.add(array.getString(i));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(0);
            }
        });

    }


    private class BatteryReceiver extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {
            int current = intent.getExtras().getInt("level");// 获得当前电量
            int total = intent.getExtras().getInt("scale");// 获得总电量
            int percent = current * 100 / total;
            pow.setText(percent + "%");
        }
    }

    class MyAdapter extends PagerAdapter {
        /*覆盖getItemPosition()方法，当调用notifyDataSetChanged时，
        让getItemPosition方法人为的返回POSITION_NONE，从而达到强迫viewpager重绘所有item的目的。*/
        private int mChildCount = 0;

        @Override
        public void notifyDataSetChanged() {
            mChildCount = getCount();
            super.notifyDataSetChanged();
        }

        @Override
        public int getItemPosition(Object object) {
            if (mChildCount > 0) {
                mChildCount--;
                return POSITION_NONE;
            }
            return super.getItemPosition(object);
        }

        @Override
        public int getCount() {
            return list != null ? list.size() : 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(list.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // 将集合中的View填充到ViewPager中.
            container.addView(list.get(position));
            return list.get(position);
        }

    }
}
