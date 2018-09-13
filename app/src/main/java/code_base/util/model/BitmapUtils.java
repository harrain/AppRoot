package code_base.util.model;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import code_base.util.LogUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 图片读取保存工具类
 */
public final class BitmapUtils {
	private static String tag = "BitmapUtils";

	/**
	 * 按指定尺寸转换图片
	 * @param data：图片的二进制数据
	 * @param width：图片的预期宽度
	 * @param height：图片的预期高度
	 * @return Bitmap类型
	 */
	public static Bitmap getBitmap(byte[] data, int width, int height){
		Options options=new Options();
		options.inJustDecodeBounds=true;
		//只获取图片的宽和高
		BitmapFactory.decodeByteArray(data, 0, data.length, options);
		int scaleX=1;
		if(width>0 && width<options.outWidth){
			scaleX=options.outWidth/width;
		}
		int scaleY=1;
		if (height > 0 && height<options.outHeight) {
			scaleY=options.outHeight/height;
		}
		int scale=scaleX;
		if(scale<scaleY){
			scale=scaleY;
		}
		options.inJustDecodeBounds=false;
		options.inSampleSize=scale;
		//使用Bitmap.Config.RGB_565比默认的Bitmap.Config.RGB_8888节省一半的内存。
		options.inPreferredConfig= Bitmap.Config.RGB_565;
		Bitmap bitmap= BitmapFactory.decodeByteArray(data, 0, data.length,options);
		return bitmap;
	}
	/**
	 * 从本地文件读取图片
	 * @param path：图片文件的本地路径
	 * @return 图片的Bitmap类型
	 */
	public static Bitmap getBitmap(String path){
		File file=new File(path);
		if(!file.exists()){
			return null;
		}
		if(file.length()==0){
			file.delete();
			return null;
		}
		Bitmap bitmap= BitmapFactory.decodeFile(path);
		return bitmap;
	}

	public static Bitmap getBitmap(InputStream stream){
		return BitmapFactory.decodeStream(stream);
	}
	/**
	 *  将图片保存至本地png
	 * @param bitmap：图片
	 * @param path：保存的路径
	 */
	public static File saveBitmap(Bitmap bitmap, String path) {
		if (bitmap == null || TextUtils.isEmpty(path)) return null;
		File file=new File(path);
		if (file.exists()) {
			boolean delete = file.delete();
			LogUtils.i(tag,"saveBitmap","删除"+delete);
		}
		if(!file.getParentFile().exists()){//若不存在目录，则创建
			boolean isSuccess = file.getParentFile().mkdirs();
			if(!isSuccess){//若文件所在目录创建失败，则返回
				return null ;
			}
		}
		try {
			FileOutputStream out=new FileOutputStream(file);
			bitmap.compress(CompressFormat.PNG, 100, out);
			return file;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 将图片保存至本地jpg
	 * @param bitmap：图片
	 * @param path：保存的路径
	 */
	public static void saveBitmapJPG(Bitmap bitmap, String path) {
		File file=new File(path);
		if (file.exists()) {
			boolean delete = file.delete();
			LogUtils.i(tag,"saveBitmap","删除"+delete);
		}
		if(!file.getParentFile().exists()){//若不存在目录，则创建
			boolean isSuccess = file.getParentFile().mkdirs();
			if(!isSuccess){//若文件所在目录创建失败，则返回
				return ;
			}
		}
		try {
			FileOutputStream out=new FileOutputStream(file);
			bitmap.compress(CompressFormat.JPEG, 100, out);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//base64字符串转化成图片
	public static boolean decodeFromBase64(String imgStr,String picPath)
	{   //对字节数组字符串进行Base64解码并生成图片
		if (imgStr == null) //图像数据为空
			return false;

		try
		{
			//Base64解码
			byte[] b = Base64.decode(imgStr,Base64.NO_WRAP);
			for(int i=0;i<b.length;++i)
			{
				if(b[i]<0)
				{//调整异常数据
					b[i]+=256;
				}
			}
			//生成jpeg图片
			OutputStream out = new FileOutputStream(picPath);
			out.write(b);
			out.flush();
			out.close();
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}

	/**
	 * view转Bitmap
	 *
	 * @param view 视图
	 * @return bitmap
	 */
	public static Bitmap view2Bitmap(final View view) {
		if (view == null) return null;
		Bitmap ret = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(ret);
		Drawable bgDrawable = view.getBackground();
		if (bgDrawable != null) {
			bgDrawable.draw(canvas);
		} else {
			canvas.drawColor(Color.WHITE);
		}
		view.draw(canvas);
		return ret;
	}

	public static byte[] getBytes(Bitmap bitmap){
		if (bitmap == null){
			Log.e(tag,"[getBytes] bitmap is null");
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.JPEG, 100, baos);
		return baos.toByteArray();
	}
}



