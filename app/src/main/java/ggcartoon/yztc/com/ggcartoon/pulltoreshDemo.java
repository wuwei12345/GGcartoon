package ggcartoon.yztc.com.ggcartoon;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ScrollView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

public class pulltoreshDemo extends Activity {
    PullToRefreshScrollView toRefreshScrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulltoresh_demo);
        toRefreshScrollView= (PullToRefreshScrollView) findViewById(R.id.pulldemo);
        //监听刷新事件
        toRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                //刷新功能
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                //加载功能
            }
        });
    }

}
