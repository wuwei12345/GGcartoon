package ggcartoon.yztc.com.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import ggcartoon.yztc.com.Adapter.ManHuaZhangJieAdapter;
import ggcartoon.yztc.com.Bean.ManHuaZhangJieBean;
import ggcartoon.yztc.com.ggcartoon.ManHuaActivity;
import ggcartoon.yztc.com.ggcartoon.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/6/17.
 */
public class SelectPicPopupWindow extends PopupWindow {
    private View mMenuView;
    private RecyclerView view;
    private ManHuaZhangJieAdapter adapter;
    private List<ManHuaZhangJieBean.DataBean> MHZJ;
    Context context;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 0:
                    //设置adapter
                    adapter=new ManHuaZhangJieAdapter(MHZJ);
                    view.setAdapter(adapter);
                    //item单击事件
                    adapter.setOnItemClickLitener(new ManHuaZhangJieAdapter.OnItemClickLitener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent intent=new Intent(context,ManHuaActivity.class);
                            intent.putExtra("bid",MHZJ.get(position).getId());
                            Bundle bundle=new Bundle();
                            bundle.putSerializable("MHZJ", (Serializable) MHZJ);
                            intent.putExtras(bundle);
                            context.startActivity(intent);
                        }

                        @Override
                        public void onItemLongClick(View view, int position) {

                        }
                    });
                    break;
                case 1:
                    Toast.makeText(context, "网络获取失败", Toast.LENGTH_SHORT).show();
                    break;
                default:

                    break;
            }
        }
    };
    public SelectPicPopupWindow(Activity context, String ID, String TitleName, String comIcid) {
        super(context);
        this.context=context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.alert_dialog, null);
        //初始化控件
        initview();
        //初始化参数
        initdata();
        //加载数据
        initviewoper(ID,comIcid);
    }

    private void initviewoper(String id,String comicId) {
        String path ="http://csapi.dm300.com:21889/android/comic/charpterlist?comicsrcid=" + id + "&comicid=" + comicId;
        //网络访问
        run(path);
    }

    private void run(String path) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(path).build();
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
                    JSONObject jsonobj = new JSONObject(json);
                    JSONArray array = jsonobj.getJSONArray("data");
                    MHZJ= JSON.parseArray(array.toString(),ManHuaZhangJieBean.DataBean.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(0);
            }
        });
    }

    private void initdata() {
        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.FILL_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.PopupAnimation);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    private void initview() {
        view = (RecyclerView) mMenuView.findViewById(R.id.ZJ_recylerView);
        view.setLayoutManager(new GridLayoutManager(mMenuView.getContext(), 4));
        view.addItemDecoration(new DividerGridItemDecoration(mMenuView.getContext()));
    }

}
