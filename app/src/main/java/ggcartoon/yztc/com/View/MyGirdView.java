package ggcartoon.yztc.com.View;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * * @author 作者 E-mail: * @date 创建时间：2015年10月30日 下午7:46:23 * @version 1.0
 * * @parameter * @since * @return
 */
public class MyGirdView extends GridView {

	public MyGirdView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MyGirdView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MyGirdView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);

	}

}
