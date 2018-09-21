package code_base.util.model;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * 文件创建重命名等工具类
 */
public class FileUtils {
	static String tag = "FileUtils";

	/**
	 * 获取sd卡的保存位置
	 * @param path:
	 */
	public static String getDir(Context context, String path) {
		File dir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//		File dir =Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		path=dir.getAbsolutePath()+"/"+path;
		return path;
	}
	
	/**
	 * 修改本地任意文件名称
	 * @param context
	 * @param oldName
	 * @param newName
	 */
	public static void renameFileName(Context context, String oldName, String newName){
		String dir = getDir(context, oldName);
		File oldFile=new File(dir);
		dir=getDir(context, newName);
		File newFile=new File(dir);
		oldFile.renameTo(newFile);
	}

	public static File createFile(String path){
		File file=new File(path);

		if(!file.getParentFile().exists()){//若不存在目录，则创建
			boolean isSuccess = file.getParentFile().mkdirs();
			if(!isSuccess){//若文件所在目录创建失败，则返回
				return null;
			}
		}
		return file;
	}
	/**
	 * 将文件转成字符数组
	 */
	public static byte[] getBytesFromFile(File file){
		try {
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			byte[] data = bos.toByteArray();
			bos.close();
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static File writeFile(InputStream is, String filepath){
		File file = null;
		try {
			file = new File(filepath);
			FileOutputStream fos = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			int byteCount = 0;
			while ((byteCount = is.read(buffer)) != -1) {//循环从输入流读取 buffer字节
				fos.write(buffer, 0, byteCount);//将读取的输入流写入到输出流
			}
			fos.flush();//刷新缓冲区
			is.close();
			fos.close();
		}catch (Exception e){
			Log.e(tag,"writeFile: "+e.getMessage());
		}
		return file;
	}
}
