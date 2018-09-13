package code_base.util.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.damon.approot.R;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by data on 2017/9/28.
 * 显示图片Glide工具类
 */

public class GlideUtil {

    public static void showImage(Context context, @DrawableRes int resId, ImageView imageView){
        Glide.with(context).load(resId).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);
    }

    public static void showImage(Context context, @DrawableRes int resId, ImageView imageView,boolean isScrolling){
        if (!isScrolling)
        Glide.with(context).load(resId).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);
    }

    public static void showImage(Context context, String imgpath, ImageView imageView){
        Glide.with(context).load(imgpath).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }

    public static void showImage(boolean cache,Context context, String imgpath, ImageView imageView){
        if (cache) showImage(context, imgpath, imageView);
        else Glide.with(context).load(imgpath).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);
    }

    public static void showImageFitCenter(boolean cache,Context context, String imgpath, ImageView imageView){
        if (cache) showImage(context, imgpath, imageView);
        else Glide.with(context).load(imgpath).fitCenter().into(imageView);
    }

    public static void showImage(Context context, String imgpath, ImageView imageView,int width,int height){
        Glide.with(context).load(imgpath).override(width,height).into(imageView);
    }

    public static void showImage(Context context, File img, ImageView imageView, int width, int height){
        Glide.with(context).load(img).override(width,height).into(imageView);
    }

    public static void showImage(Context context, int imgId, ImageView imageView,int width,int height){
        Glide.with(context).load(imgId).override(width,height).into(imageView);
    }

    public static void showImage(Context context, String imgpath, ImageView imageView,boolean isScrolling){
        if (!isScrolling)
            Glide.with(context).load(imgpath).into(imageView);
    }

    public static void showImage(Context context, Uri uri, ImageView view, int width,int height) {
        Glide.with(context).load(uri).override(width,height).into(view);
    }

    public static void showImage(Context context, Bitmap bitmap, ImageView view) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes=baos.toByteArray();

        Glide.with(context).load(bytes).diskCacheStrategy(DiskCacheStrategy.NONE).into(view);
    }

    public static void showImage(Context context, Bitmap bitmap, ImageView view, int width,int height) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes=baos.toByteArray();

        Glide.with(context).load(bytes).override(width,height).into(view);
    }
}
