package ggcartoon.yztc.com.ggcartoon;

import android.app.Activity;
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
import ggcartoon.yztc.com.initerface.Initerface;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ManHuaActivity extends Activity implements Initerface, View.OnClickListener {

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
    //用来存放图片的列表
    List<PhotoView> list;
    MyAdapter mydaapter;
    //网络请求
    private HttpUtils mhttpUtils;
    //电量
    private BatteryReceiver receiver;
    //bean
    private List<String> MH=new ArrayList<>();
    //上个页面获取的ID
    String id;
    //接口地址
    String path;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 0:
                list=new ArrayList<>();
                    for (int i=0;i<MH.size();i++){
                        PhotoView iv=new PhotoView(ManHuaActivity.this);
                        //启动放大缩小
                        iv.enable();
                        //充满全屏
                        iv.setScaleType(ImageView.ScaleType.FIT_XY);
                        //图片单击事件
                        iv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                            }
                        });
                        try {
                            Picasso.with(ManHuaActivity.this).load(MH.get(i)).into(iv);
                            Thread.sleep(100);
                            list.add(iv);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    mydaapter.notifyDataSetChanged();
                    yeshu.setText("1" + "/" + list.size());
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
        //初始化电量监听器
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        receiver = new BatteryReceiver();
        //注册广播接收器
        registerReceiver(receiver, filter);

    }

    @Override
    public void initdata() {
        mhttpUtils = new HttpUtils();
        //获取传过来的id
        Intent intent = getIntent();
        id = intent.getStringExtra("bid");
        path = "http://csapi.dm300.com:21889/android/comic/charpterinfo?charpterid=" + id;
    }

    @Override
    public void initviewoper() {
        //加载系统时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        time.setText(format.format(date));
        //通过接口获取漫画内容
//        downLoad();
        run(path);
        mydaapter=new MyAdapter();
        vp.setAdapter(mydaapter);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position==list.size()-1){
                    Toast.makeText(ManHuaActivity.this, "已到最后一张", Toast.LENGTH_SHORT).show();
                }
                yeshu.setText(position + 1 + "/" + list.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

//    private void downLoad() {
//        mhttpUtils.send(HttpRequest.HttpMethod.GET, path, new RequestCallBack<String>() {
//            @Override
//            public void onSuccess(ResponseInfo<String> responseInfo) {
//                try {
//                    //json解析
//                    String json = responseInfo.result;
//                    JSONObject obj = new JSONObject(json);
//                    JSONObject obj2 = obj.getJSONObject("data");
//                    JSONArray array=obj2.getJSONArray("addrs");
//                   for (int i=0;i<=array.length()-1;i++){
//                            MH.add(array.getString(i));
//                   }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                handler.sendEmptyMessage(0);
//            }
//
//            @Override
//            public void onFailure(HttpException e, String s) {
//                Toast.makeText(ManHuaActivity.this, "获取内容失败，请检查网络", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
    //加载网络数据
    void run(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(ManHuaActivity.this, "网络获取失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                try {
                    //json解析
                    JSONObject obj = new JSONObject(json);
                    JSONObject obj2 = obj.getJSONObject("data");
                    JSONArray array=obj2.getJSONArray("addrs");
                    for (int i=0;i<=array.length()-1;i++){
                        MH.add(array.getString(i));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(0);
            }
        });

    }
    @Override
    public void onClick(View v) {

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
    class MyAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return list!=null?list.size():0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
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
