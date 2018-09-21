package code_base.util.model;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by data on 2017/9/1.
 * IO流处理工具类
 */

public class IOUtils {
    static String tag = "IOUtils";

    /**
     * 功能：Java读取txt文件的内容 步骤：1：先获得文件句柄 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
     * 3：读取到输入流后，需要读取生成字节流 4：一行一行的输出。readline()。 备注：需要考虑的是异常情况
     *
     * @param filePath
     *            文件路径[到达文件:如： D:\aa.txt]
     * @return 将这个文件按照每一行切割成数组存放到list中。
     */
    public static List<String> readTxtFileIntoStringArrList(String filePath) {
        List<String> list = new ArrayList<String>();
        try {
//            String encoding = "GBK";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file));// 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    list.add(lineTxt+"\n");
                }
                bufferedReader.close();
                read.close();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return list;
    }

    public static String readFile(String filePath){
        StringBuilder sb = new StringBuilder();

        try {
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file));// 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    sb.append(lineTxt+"\n");
                }
                bufferedReader.close();
                read.close();
            } else {
                Log.e(tag,"找不到指定的文件");
            }
        } catch (Exception e) {
            Log.e(tag,"读取文件内容出错");
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String readLastStr(String path) {
        try {
            RandomAccessFile raf = new RandomAccessFile(path, "r");
            long len = raf.length();
            String lastLine = "";
            if (len != 0L) {
                long pos = len - 1;
                while (pos > 0) {
                    pos--;
                    raf.seek(pos);
                    if (raf.readByte() == '\n') {
                        lastLine = raf.readLine();
                        break;
                    }
                }
            }
            raf.close();
            return lastLine;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从网络输入流中读取字符串
     */
    public static String readFromStream(InputStream inputStream) {
        try {
            StringBuilder sb = new StringBuilder();
            InputStreamReader read = new InputStreamReader(inputStream);// 考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineText = null;
            while ((lineText = bufferedReader.readLine()) != null) {
                sb.append(lineText);
            }
            bufferedReader.close();
            read.close();
            return sb.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 读流字节，可作为进度展示
     */
    public static String readBytesFromStream(InputStream inputStream){
        try {

            BufferedInputStream bis = new BufferedInputStream(inputStream);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int len;
            byte[] arr = new byte[1024];
            while ((len = bis.read(arr)) != -1) {
                bos.write(arr, 0, len);
                bos.flush();
            }
            bos.close();
            return bos.toString("utf-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void closeStream(Closeable steam){
        try {
            steam.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
