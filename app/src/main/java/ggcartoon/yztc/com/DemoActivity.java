package ggcartoon.yztc.com;

import android.os.Bundle;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;

import java.util.List;

import ggcartoon.yztc.com.Bean.ShouCang;
import ggcartoon.yztc.com.ggcartoon.R;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;


public class DemoActivity extends SwipeBackActivity {
static DbUtils dbUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        //初始化数据库
        dbUtils=DbUtils.create(this,"shoucang.db");
        try {
            //数据库存储数据
            DemoActivity.dbUtils.save(ShouCang.class);
            //从数据库取数据
            List<ShouCang> list=DemoActivity.dbUtils.findAll(ShouCang.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

}
