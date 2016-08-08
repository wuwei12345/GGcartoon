package ggcartoon.yztc.com.View;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.view.WindowManager;
import android.widget.SeekBar;

import ggcartoon.yztc.com.ggcartoon.R;

/**
 * Created by Administrator on 2016/7/20.
 */
public class CustomDialog extends Dialog {
    int layoutRes;//布局文件
    Context context;
    SeekBar mSeekBar_light;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSeekBar_light= (SeekBar) findViewById(R.id.seekbars);

    }

    public CustomDialog(Context context) {
        super(context);
        this.context = context;
    }

    public CustomDialog(final Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
        this.layoutRes = themeResId;
        mSeekBar_light.setMax(255);
        // 取得当前亮度
        int normal = Settings.System.getInt(context.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS, 255);
        // 进度条绑定当前亮度
        mSeekBar_light.setProgress(normal);
        mSeekBar_light.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // 取得当前进度
                int tmpInt = seekBar.getProgress();

                // 当进度小于80时，设置成80，防止太黑看不见的后果。
                if (tmpInt < 80) {
                    tmpInt = 80;
                }

                // 根据当前进度改变亮度
                Settings.System.putInt(context.getContentResolver(),
                        Settings.System.SCREEN_BRIGHTNESS, tmpInt);
                tmpInt = Settings.System.getInt(context.getContentResolver(),
                        Settings.System.SCREEN_BRIGHTNESS, -1);
                WindowManager.LayoutParams wl = getWindow().getAttributes();

                float tmpFloat = (float) tmpInt / 255;
                if (tmpFloat > 0 && tmpFloat <= 1) {
                    wl.screenBrightness = tmpFloat;
                }
                getWindow().setAttributes(wl);
            }
        });

    }


    protected CustomDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.setContentView(layoutRes);
    }



    public class SeekBarChangeListenerImp implements SeekBar.OnSeekBarChangeListener {


        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
//        TODO Auto-generated method stub
//            int cur=seekBar.getProgress();
//            ManHuaXiangQingActivity.this.setScreenBrightness(cur/100);
//            ManHuaXiangQingActivity.this.light.setText("当前屏幕亮度:"+cur/100);
        }


        public void onStartTrackingTouch(SeekBar seekBar) {
// TODO Auto-generated method stub
        }


        public void onStopTrackingTouch(SeekBar seekBar) {
// TODO Auto-generated method stub
        }
    }

    //设置屏幕亮度的函数
    private void setScreenBrightness(float num) {
        WindowManager.LayoutParams layoutParams = super.getWindow().getAttributes();
        layoutParams.screenBrightness = num;//设置屏幕的亮度
        super.getWindow().setAttributes(layoutParams);
    }

}
