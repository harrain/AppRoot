package code_base.util.model;

import android.content.Context;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * 文件创建重命名等工具类
 */
public class FileUtils {

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
}
