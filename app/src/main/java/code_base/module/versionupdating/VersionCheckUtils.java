package code_base.module.versionupdating;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import code_base.util.LogUtils;
import code_base.util.Utils;
import code_base.util.model.IOUtils;


/**
 * Created by data on 2017/9/25.
 * 获取版本号，联网校验版本工具类
 */

public class VersionCheckUtils {

    private static final String tag = "VersionCheckUtils";
    public static final int CODE_UPDATE_DIALOG = 61;
    public static final int CODE_NO_UPDATE = 62;
    public static final int CODE_URL_ERROR = 30;
    public static final int CODE_NET_ERROR = 31;
    public static final int CODE_SERVER_NORESULT = 32;

    private Handler mHandler;
    private VersionResultListener mListener;

    public interface VersionResultListener {
        void onResultFlag(String apkUrl);
    }

    public void registerVersionResultListener(VersionResultListener listener) {
        mListener = listener;
    }

    public void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case CODE_URL_ERROR:
                        LogUtils.e(tag, "handleMessage","url异常");
                        break;
                    case CODE_NET_ERROR:
                        LogUtils.e(tag, "handleMessage","网络异常");
                        break;
                    case CODE_SERVER_NORESULT:
                        LogUtils.e(tag, "handleMessage","服务器无数据返回");
                        break;
                    case CODE_UPDATE_DIALOG:
                        if (mListener != null) {
                            mListener.onResultFlag((String) msg.obj);
                        }
                        break;
                }
            }
        };
    }

    /**
     * 获取版本名称
     *
     * @return
     */
    public static String getVersionName() {
        PackageManager packageManager = Utils.getContext().getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    Utils.getContext().getPackageName(), 0);// 获取包的信息

            int versionCode = packageInfo.versionCode;
            String versionName = packageInfo.versionName;

            LogUtils.i(tag, "versionName=" + versionName + ";versionCode="
                    + versionCode);

            return versionName;
        } catch (Exception e) {
            // 没有找到包名的时候会走此异常
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 获取本地app的版本号
     *
     * @return
     */
    public static int getVersionCode() {
        PackageManager packageManager = Utils.getContext().getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    Utils.getContext().getPackageName(), 0);// 获取包的信息

            int versionCode = packageInfo.versionCode;
            LogUtils.i(tag, "versionCode="
                    + versionCode);
            return versionCode;
        } catch (Exception e) {
            // 没有找到包名的时候会走此异常
            e.printStackTrace();
        }

        return -1;
    }

    /**
     * 从服务器获取版本信息进行校验
     */
    public void checkVerson(final String httpurl, final String apkHost) {

        // 启动子线程异步加载数据
        new Thread() {

            @Override
            public void run() {
                LogUtils.i(tag, "checkVerson thread start");
                Message msg = Message.obtain();
                HttpURLConnection conn = null;
                try {
                    // 本机地址用localhost, 但是如果用模拟器加载本机的地址时,可以用ip(10.0.2.2)来替换
                    URL url = new URL(httpurl);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");// 设置请求方法
                    conn.setConnectTimeout(15000);// 设置连接超时
                    conn.setReadTimeout(15000);// 设置响应超时, 连接上了,但服务器迟迟不给响应
                    conn.connect();// 连接服务器

                    int responseCode = conn.getResponseCode();// 获取响应码
                    LogUtils.i(tag, "checkVerson","responseCode " + responseCode);
                    if (responseCode == 200) {
                        InputStream inputStream = conn.getInputStream();
                        String result = IOUtils.readFromStream(inputStream);
                        if (TextUtils.isEmpty(result)) {
                            msg.what = CODE_SERVER_NORESULT;
                        } else {
                            int mVersionCode = Integer.parseInt(result);
                            if (mVersionCode > getVersionCode()) {// 判断是否有更新
                                // 服务器的VersionCode大于本地的VersionCode
                                // 说明有更新, 弹出升级对话框
                                msg.what = CODE_UPDATE_DIALOG;
                                msg.obj = apkHost;
                            } else {
                                // 没有版本更新
                                msg.what = CODE_NO_UPDATE;
                            }
//                            try {
//                                JSONObject jsonObject = new JSONObject(result);
//                                String name = jsonObject.getString("name");
//                                String version = jsonObject.getString("version");
//                                int mVersionCode = Integer.parseInt(version);
//                                if (mVersionCode > getVersionCode()) {// 判断是否有更新
//                                    // 服务器的VersionCode大于本地的VersionCode
//                                    // 说明有更新, 弹出升级对话框
//                                    msg.what = CODE_UPDATE_DIALOG;
//                                    msg.obj = apkHost + "/" + name;
//                                } else {
//                                    // 没有版本更新
//                                    msg.what = CODE_NO_UPDATE;
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
                            LogUtils.i(tag, "checkVerson","server apk version: " + result);

                        }
                    }
                } catch (MalformedURLException e) {
                    // url错误的异常
                    msg.what = CODE_URL_ERROR;
                    e.printStackTrace();
                } catch (IOException e) {
                    // 网络错误异常
                    msg.what = CODE_NET_ERROR;
                    e.printStackTrace();
                } finally {
                    mHandler.sendMessage(msg);
                    if (conn != null) {
                        conn.disconnect();// 关闭网络连接
                    }
                }
            }
        }.start();
    }
}
