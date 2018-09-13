package code_base.util.view;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 监听图片是否加载成功
 */
public class GlideRequestLoad implements RequestListener<String,GlideDrawable> {
    String tag = "GlideRequestLoad";
    SimpleDateFormat sdf;
    String jdcode;
    ImageView productIv;
    Context context;
    Date date;

    public GlideRequestLoad(){}

    public GlideRequestLoad(Context context,String jDCode,ImageView imageView,Date date){
        jdcode = jDCode;
        productIv = imageView;
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.context = context;
        this.date = date;
    }

    /**
     * 加载失败，如本地图片被用户删除的情况
     */
    @Override
    public boolean onException(Exception e, String model, Target target, boolean isFirstResource) {
//        requestNet(context,jdcode,productIv,date);//重新联网获取图片
        return true;
    }

    @Override
    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
        return false;
    }

    /**
     * 加载成功
     */


    public void setParam(Context context,String jDCode,ImageView imageView,Date date){
        this.context = context;
        jdcode = jDCode;
        productIv = imageView;
        this.date = date;
    }

    private void requestNet(final Context context, final String jdCode, final ImageView productIv, final Date date) {


    }
}


