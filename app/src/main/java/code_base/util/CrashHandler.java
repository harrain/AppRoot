package code_base.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import code_base.util.model.IOUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by net on 2018/4/18.
 * crash的捕捉器
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = "CrashHandler";
    private static final String PATH = Environment.getExternalStorageDirectory().getPath() + "/crash.txt";
    private Thread.UncaughtExceptionHandler mDefaultCrashHandler;
    private Context mContext;
    private static CrashHandler mCrashHandler;
    public static final String CRASH_HEAD;
    private static String versionName;
    private static int versionCode;

    private static final String FILE_SEP = System.getProperty("file.separator");
    private static final Format FORMAT = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss", Locale.getDefault());
    private static String fullPath;
    private String mCrashDir;
    private static String mUrl = null;

    static {
        try {
            PackageInfo pi = Utils.getContext().getPackageManager().getPackageInfo(Utils.getContext().getPackageName(), 0);
            if (pi != null) {
                versionName = pi.versionName;
                versionCode = pi.versionCode;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Date now = new Date(System.currentTimeMillis());

        CRASH_HEAD = "<br />************* 程序出现异常错误 "+FORMAT.format(now)+ "****************" +
                "<br /> Device Manufacturer: " + Build.MANUFACTURER +// 设备厂商
                "<br /> Device Model       : " + Build.MODEL +// 设备型号
                "<br /> Android Version    : " + Build.VERSION.RELEASE +// 系统版本
                "<br /> Android SDK        : " + Build.VERSION.SDK_INT +// SDK版本
                "<br /> CPU ABI            : " + Build.CPU_ABI +
                "<br /> App VersionName    : " + versionName +
                "<br /> App VersionCode    : " + versionCode +
                "<br /> ************* Crash Cause **************** <br />";
    }


    public void init(Context context, String crashDir,String url) {
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        mContext = context;
        mCrashDir = crashDir;
        mUrl = url;
    }


    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Log.e(TAG, "uncaughtException: " + e.getMessage());

        writeToExternalStorage(e);

//                if (mDefaultCrashHandler != null) { //不加这段话，APP崩溃不会出现闪退的情况
//                    mDefaultCrashHandler.uncaughtException(t, e);
//                }

    }


    private boolean writeToExternalStorage(final Throwable e) {
        Date now = new Date(System.currentTimeMillis());
        String fileName = FORMAT.format(now) + ".txt";
        fullPath = mCrashDir + FILE_SEP + fileName;
        Log.i(TAG, "writeToExternalStorage: " + fileName);
        if (!createOrExistsFile(fullPath)) return true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "writeToExternalStorage: write");
                PrintWriter pw = null;
                try {
                    pw = new PrintWriter(new FileWriter(fullPath, false));
                    pw.write(CRASH_HEAD);
                    e.printStackTrace(pw);
                    Throwable cause = e.getCause();
                    while (cause != null) {
                        cause.printStackTrace(pw);
                        cause = cause.getCause();
                    }
                } catch (IOException e) {
                    Log.e(TAG, "writeToExternalStorage: " + e.getMessage());
                } finally {
                    if (pw != null) {
                        pw.close();
                    }
                }
                uploadToServer(IOUtils.readFile(fullPath));//必须等文件写完了 pw或其他writer、os关闭 才可以读文件

            }
        }).start();
        return false;
    }

    private void uploadToServer(String content) {
        if (mUrl == null) return;
        URL url = null;
        try {
            url = new URL(mUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");// 提交模式
            httpURLConnection.setConnectTimeout(30000);//连接超时 单位毫秒
            httpURLConnection.setReadTimeout(30000);//读取超时 单位毫秒

            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setUseCaches(false);

            //设置请求属性
//            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
//            httpURLConnection.setRequestProperty("Charset", "UTF-8");

            // 获取URLConnection对象对应的输出流
            PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            // 发送请求参数
            printWriter.write("data="+ content);//post的参数 xx=xx&yy=yy
            // flush输出流的缓冲
            printWriter.flush();
            printWriter.close();
            //开始获取响应
            BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int len;
            byte[] arr = new byte[1024];
            while((len=bis.read(arr))!= -1){
                bos.write(arr,0,len);
                bos.flush();
            }
            bos.close();
            Log.i(TAG,"uploadToServer: "+bos.toString("utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean createOrExistsFile(final String filePath) {
        File file = new File(filePath);
        if (file.exists()) return file.isFile();
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean createOrExistsDir(final File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    public static CrashHandler getCrashHandler() {
        if (mCrashHandler == null) mCrashHandler = new CrashHandler();
        return mCrashHandler;
    }
}
