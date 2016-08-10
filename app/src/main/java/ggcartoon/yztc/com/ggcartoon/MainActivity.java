package ggcartoon.yztc.com.ggcartoon;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.RadioGroup;

import com.lidroid.xutils.DbUtils;

import ggcartoon.yztc.com.fragment.FenleiFragment;
import ggcartoon.yztc.com.fragment.GengduoFragment;
import ggcartoon.yztc.com.fragment.ShujiaFragment;
import ggcartoon.yztc.com.fragment.TuijianFragment;
import ggcartoon.yztc.com.initerface.Initerface;

public class MainActivity extends FragmentActivity implements Initerface,RadioGroup.OnCheckedChangeListener{
    //存放四个按钮的RadioGroup
    private RadioGroup rgbottom;
    //四个Fragment
    private TuijianFragment tuijianFragment;
    private ShujiaFragment shujiaFragment;
    private FenleiFragment fenleiFragment;
    private GengduoFragment gengduoFragment;

    //存储漫画
    public static DbUtils dbUtils;
    //fragment管理器
    FragmentManager Manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
        initdata();
        initviewoper();
    }
    //初始化控件
    @Override
    public void initview() {
//        sp = getSharedPreferences("user", MODE_PRIVATE);
        //初始化数据库
        dbUtils = DbUtils.create(this, "shoucang.db");
        rgbottom= (RadioGroup) findViewById(R.id.rg_bottom);
        rgbottom.setOnCheckedChangeListener(this);
        Manager=getSupportFragmentManager();
        //demo
    }


    @Override
    public void initdata() {
        Setselection(0);
    }

    @Override
    public void initviewoper() {

    }

    private void Setselection(int index) {
        //FragmentTransaction对fragment进行添加，删除，替换，等其他执行操作
        FragmentTransaction ft=Manager.beginTransaction();
        hindFragment(ft);
        //需要加载的页面
        switch (index){
            case 0:
                    if (tuijianFragment==null){
                        tuijianFragment=new TuijianFragment();
                        ft.add(R.id.frameLayout1,tuijianFragment);
                    }else{
                        ft.show(tuijianFragment);
                    }
                break;
            case 1:
                if (shujiaFragment==null){
                 shujiaFragment=new ShujiaFragment();
                    ft.add(R.id.frameLayout1,shujiaFragment);
                }else{
                    ft.show(shujiaFragment);
                }
                break;
            case 2:
                if (fenleiFragment==null){
                    fenleiFragment=new FenleiFragment();
                    ft.add(R.id.frameLayout1,fenleiFragment);
                }else{
                    ft.show(fenleiFragment);
                }
                break;
            case 3:
                if (gengduoFragment==null){
                    gengduoFragment=new GengduoFragment();
                    ft.add(R.id.frameLayout1,gengduoFragment);
                }else{
                    ft.show(gengduoFragment);
                }
                break;
        }
        ft.commit();
    }

    private void hindFragment(FragmentTransaction ft) {
        //如果fragment不为空则保存当前fragment状态
        if (tuijianFragment!=null){
            ft.hide(tuijianFragment);
        }
        if (shujiaFragment!=null){
            ft.hide(shujiaFragment);
        }
        if (fenleiFragment!=null){
            ft.hide(fenleiFragment);
        }
        if (gengduoFragment!=null){
            ft.hide(gengduoFragment);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            //推荐
            case R.id.rb_tuijian:
                Setselection(0);
                break;
            //书架
            case R.id.rb_shujia:
                Setselection(1);
                break;
            //分类
            case R.id.rb_fenlei:
                Setselection(2);
                break;
            //更多
            case R.id.rb_gengduo:
                Setselection(3);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //退出窗口
        if (keyCode==KeyEvent.KEYCODE_BACK){
            AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("确认退出？");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MainActivity.this.finish();
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();
        }
        return super.onKeyDown(keyCode, event);
    }

}
