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
import android.provider.Settings;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;

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
import butterknife.OnClick;
import ggcartoon.yztc.com.Adapter.ManHuaAdapter;
import ggcartoon.yztc.com.Bean.ManHuaZhangJieBean;
import ggcartoon.yztc.com.initerface.Initerface;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ManHuaActivity extends Activity implements Initerface {

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
    @Bind(R.id.manhua_recyclerView)
    RecyclerView manhuaRecyclerView;
    @Bind(R.id.next_and_up)
    TextView nextAndUp;
    @Bind(R.id.ll)
    LinearLayout ll;
    @Bind(R.id.Btn_MS)
    ImageButton BtnMS;
    @Bind(R.id.rigit_ll)
    RelativeLayout rigitLl;
    @Bind(R.id.MH_Title)
    TextView MHTitle;
    @Bind(R.id.Btn_LD)
    ImageButton BtnLD;
    @Bind(R.id.seekbar_LD)
    SeekBar seekbarLD;
    @Bind(R.id.seekbar_yeshu)
    SeekBar seekbarYeshu;
    @Bind(R.id.yeshu_txt)
    TextView yeshuTxt;
    @Bind(R.id.yeshu_seebar_ll)
    LinearLayout yeshuSeebarLl;
    //网络请求
    private HttpUtils mhttpUtils;
    //电量
    private BatteryReceiver receiver;
    //bean
    private List<String> MH;
    //上个页面获取的ID
    int id;
    //adapter
    ManHuaAdapter manHuaAdapter;
    //切换时用来记录当前是第几个Item
    int yeshuCount = 0;
    private LinearLayoutManager linearLayoutManager;
    AlertDialog.Builder dialog;
    AlertDialog build;
    private List<ManHuaZhangJieBean.DataBean> MHZJ;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    /*设置图片到PhotoView（旧方法，已弃用）
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
                            yeshu.setText("1" + "/" + MH.size());
                            mydaapter.notifyDataSetChanged();
                            //关闭dialog
                            build.dismiss();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    */
                    manHuaAdapter = new ManHuaAdapter(MH);
                    manhuaRecyclerView.setAdapter(manHuaAdapter);
                    yeshu.setText("1" + "/" + MH.size());
                    //设置页数进度条
                    yeshuseekbar_check();
                    //横竖屏切换后跳到上一次阅读的Item处
                    linearLayoutManager.scrollToPosition(yeshuCount);
                    //列表单击事件
                    manHuaAdapter.setOnItemClickLitener(new ManHuaAdapter.OnItemClickLitener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            //用来显示右边菜单栏显示还是隐藏
                            if (rigitLl.getVisibility() == View.GONE) {
                                rigitLl.setVisibility(View.VISIBLE);
                            } else {
                                rigitLl.setVisibility(View.GONE);
                            }
                            //判断亮度进度条是否显示，是则隐藏
                            if (seekbarLD.getVisibility() == View.VISIBLE) {
                                seekbarLD.setVisibility(View.GONE);
                            }
                            //判断页数进度条是否隐藏，是则显示
                            if (yeshuSeebarLl.getVisibility() == View.GONE) {
                                yeshuSeebarLl.setVisibility(View.VISIBLE);
                                yeshuseekbar_check();
                            } else {
                                yeshuSeebarLl.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onItemLongClick(View view, int position) {

                        }
                    });
                    //关闭dialog
                    build.dismiss();
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

    /**********************
     * 设置屏幕亮度
     ******************************/
    private void screenBrightness_check() {
        //先关闭系统的亮度自动调节
        try {
            if (Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC) {
                Settings.System.putInt(getContentResolver(),
                        Settings.System.SCREEN_BRIGHTNESS_MODE,
                        Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
            }
        } catch (Settings.SettingNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //获取当前亮度,获取失败则返回255
        int intScreenBrightness = 0;
        try {
            intScreenBrightness = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
            //进度条显示
            seekbarLD.setProgress(intScreenBrightness);
            seekbarLD.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    setScreenBritness(progress);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }


    }

    //屏幕亮度
    private void setScreenBritness(int brightness) {
        //不让屏幕全暗
        if (brightness <= 1) {
            brightness = 1;
        }
        Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, brightness);
        Window localWindow = getWindow();
        WindowManager.LayoutParams localLayoutParams = localWindow.getAttributes();
        float f = brightness / 255.0F;
        localLayoutParams.screenBrightness = f;
        localWindow.setAttributes(localLayoutParams);


        //保存为系统亮度方法2
//        Uri uri = android.provider.Settings.System.getUriFor("screen_brightness");
//        android.provider.Settings.System.putInt(getContentResolver(), "screen_brightness", brightness);
//        // resolver.registerContentObserver(uri, true, myContentObserver);
//        getContentResolver().notifyChange(uri, null);
    }

    /**********************
     * 设置屏幕亮度
     ******************************/

    //初始化控件
    @Override
    public void initview() {
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
        //RecyclerView初始化
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        manhuaRecyclerView.setLayoutManager(linearLayoutManager);
        //加载提示
        dialog = new AlertDialog.Builder(this);
        dialog.setTitle("提示");
        dialog.setMessage("正在加载...");
        build = dialog.show();
        //亮度进度条设置最大值为255
        seekbarLD.setMax(255);
        screenBrightness_check();
    }

    private void yeshuseekbar_check() {
        seekbarYeshu.setMax(MH.size() - 1);
        seekbarYeshu.setProgress(yeshuCount + 1);
        yeshuTxt.setText(yeshuCount + 1 + "/" + MH.size());
        seekbarYeshu.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                yeshuTxt.setText(progress + 1 + "/" + MH.size());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                yeshuCount = seekBar.getProgress();
                yeshuTxt.setText(seekBar.getProgress() + 1 + "/" + MH.size());
                linearLayoutManager.scrollToPosition(seekBar.getProgress());
            }
        });
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
        //RecyclerView Item监听事件，判断当前如果是第一个Item则显示加载上一章，如果是最后一个Item显示加载下一章
        manhuaRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (yeshuSeebarLl.getVisibility() == View.VISIBLE) {
                    yeshuseekbar_check();
                }
                //如果当前调节亮度是显示状态，那么滑动时就隐藏它
                if (seekbarLD.getVisibility() == View.VISIBLE) {
                    seekbarLD.setVisibility(View.GONE);
                }
                //记录当前是第几个Item
                yeshuCount = linearLayoutManager.findFirstVisibleItemPosition();
                yeshu.setText(linearLayoutManager.findFirstVisibleItemPosition() + 1 + "/" + MH.size());
                if (linearLayoutManager.findFirstVisibleItemPosition() == MH.size() - 1) {
                    nextAndUp.setText("加载下一章");
                    nextAndUp.setVisibility(View.VISIBLE);
                } else if (linearLayoutManager.findFirstVisibleItemPosition() == 0) {
                    nextAndUp.setText("加载上一章");
                    nextAndUp.setVisibility(View.VISIBLE);
                } else {
                    nextAndUp.setVisibility(View.GONE);
                }
            }
        });
        //nextanup单击事件
        nextAndUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nextAndUp.getText().toString().equals("加载下一章")) {
                    for (int i = 0; i < MHZJ.size(); i++) {
                        //先判断到当前id所对应的章节ID的位置
                        if (MHZJ.get(i).getId() == id) {
                            if (i != 0) {
                                if (!(i - 1 < 0)) {
                                    id = MHZJ.get(i - 1).getId();
                                    yeshuCount = 0;
                                    initviewoper();
                                }
                            } else {
                                Toast.makeText(ManHuaActivity.this, "您已经看完了", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                } else if (nextAndUp.getText().toString().equals("加载上一章")) {
                    boolean flag = true;
                    for (int i = 0; i < MHZJ.size(); i++) {
                        //先判断到当前id所对应的章节ID的位置
                        if (MHZJ.get(i).getId() == id) {
                            System.out.println("------>i:" + i + " Mhzj:" + MHZJ.size());
                            if (!(i >= MHZJ.size() - 1)) {
                                if (i + 1 <= MHZJ.size()) {
                                    if (flag) {
                                        id = MHZJ.get(i + 1).getId();
                                        yeshuCount = 0;
                                        initviewoper();
                                        flag = false;
                                    }
                                }
                            } else {
                                Toast.makeText(ManHuaActivity.this, "前面没有了", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                } else {

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
        run(path);
        //调节屏幕亮度
        BtnLD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekbarLD.setVisibility(View.VISIBLE);
                screenBrightness_check();

            }
        });

        //downLoad();
        /*通过监听滑动事件，判断是否到达最后一页，如果继续滑动则判断左还是右，然后加载对应的上一话或者下一话（旧方法，已弃用）
        mydaapter = new MyAdapter();
        vp.setAdapter(mydaapter);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int pos = 0;
            int maxpos = 0;
            int currentPageScrollStatus;
            boolean flag = true;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                System.out.println("------>pos:" + pos + "  ------->maxpos:" + maxpos);
                if (pos == 0) {
                    //如果offsetPixels是0页面也被滑动了，代表在第一页还要往左划
                    if (positionOffsetPixels == 0 && currentPageScrollStatus == 1) {
                        for (int i = 0; i < MHZJ.size(); i++) {
                            //先判断到当前id所对应的章节ID的位置
                            if (MHZJ.get(i).getId() == id) {
                                System.out.println("------>i:" + i + " Mhzj:" + MHZJ.size());
                                if (!(i >= MHZJ.size() - 1)) {
                                    if (i + 1 <= MHZJ.size()) {
                                        if (flag) {
                                            //MHZJ.get(i - 1).getId() > MHZJ.get(i).getId()
                                            id = MHZJ.get(i + 1).getId();
                                            System.out.println("------>当前ID:" + MHZJ.get(i).getId() + "------->上一章id:" + id);
                                            initviewoper();
                                            flag = false;
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
        */

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

    //漫画横竖切换
    @OnClick(R.id.Btn_MS)
    public void onClick() {
        if (linearLayoutManager.getOrientation() == LinearLayoutManager.VERTICAL) {
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            manhuaRecyclerView.setLayoutManager(linearLayoutManager);
            handler.sendEmptyMessage(0);
            rigitLl.setVisibility(View.GONE);
            yeshuSeebarLl.setVisibility(View.GONE);
        } else if (linearLayoutManager.getOrientation() == LinearLayoutManager.HORIZONTAL) {
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            manhuaRecyclerView.setLayoutManager(linearLayoutManager);
            handler.sendEmptyMessage(0);
            rigitLl.setVisibility(View.GONE);
            yeshuSeebarLl.setVisibility(View.GONE);
        }
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

    /*
     * viewpage（旧）

    class MyAdapter extends PagerAdapter {
        //覆盖getItemPosition()方法，当调用notifyDataSetChanged时，
        //让getItemPosition方法人为的返回POSITION_NONE，从而达到强迫viewpager重绘所有item的目的。
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
*/
}
