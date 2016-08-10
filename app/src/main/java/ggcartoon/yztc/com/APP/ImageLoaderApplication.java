package ggcartoon.yztc.com.APP;

import android.app.Application;
import android.graphics.Bitmap.Config;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import ggcartoon.yztc.com.ggcartoon.R;

public class ImageLoaderApplication extends Application {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		//ImageLoader是具体下载图片，缓存图片，显示图片的具体执行类
		ImageLoader loader = ImageLoader.getInstance();
		//ImageLoaderConfiguration是针对图片缓存的全局配置，主要有线程类、缓存大小、磁盘大小、图片下载与解析、日志方面的配置。
		ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(getApplicationContext());
		//DisplayImageOptions用于指导每一个Imageloader根据网络图片的状态（空白、下载错误、正在下载）显示对应的图片，是否将缓存加载到磁盘上，下载完后对图片进行怎么样的处理。
		DisplayImageOptions.Builder optionBuilder = new DisplayImageOptions.Builder();
		optionBuilder.showImageOnFail(R.drawable.icon_error_warn);
		optionBuilder.showImageOnLoading(R.drawable.loading_1);
		optionBuilder.imageScaleType(ImageScaleType.IN_SAMPLE_INT);//图片的缩放方式：图像将被二次采样的整数倍
		optionBuilder.bitmapConfig(Config.RGB_565);//解码的类型
		builder.defaultDisplayImageOptions(optionBuilder.build());//默认
		builder.diskCacheSize(100);//本地缓冲的最大值
		builder.threadPoolSize(3);//线程池内加载的数量
		builder.threadPriority(Thread.MAX_PRIORITY);//设置当前线程的优先级
		builder.memoryCache(new WeakMemoryCache());//使用弱引用来缓存图片
		loader.init(builder.build());//开始构建

		Fresco.initialize(getApplicationContext());
	}
	
}
