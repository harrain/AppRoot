package code_base.ui.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


import com.damon.approot.R;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import code_base.AppConstants;
import code_base.util.LogUtils;
import code_base.util.NetworkUtils;
import code_base.util.ToastUtil;
import code_base.util.view.AlertDialogUtil;
import code_base.util.view.PermissionsUtil;
import io.reactivex.functions.Consumer;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * Created by stephen on 2017/7/25.
 * 所有Activity的父类
 */

public class BaseActivity extends AppCompatActivity {

    // 管理运行的所有的activity
    public final static List<BaseActivity> mActivities = new LinkedList<BaseActivity>();
    public static BaseActivity activity;

	private KillAllReceiver receiver;
	private class KillAllReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context mContext, Intent intent) {
			finish();
		}
	}

    public Context mContext;
    public boolean first;
    public String tag;

    private RxPermissions rxPermissions;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		receiver=new KillAllReceiver();
		IntentFilter filter=new IntentFilter("app.killall");
		registerReceiver(receiver, filter);
        mContext = this;
        tag = getLocalClassName();

        synchronized (mActivities) {
            mActivities.add(this);
        }
        internationalizationInitial();

        requestStoragePermission();

    }
    /**
     * 请求存储空间权限，自动过滤6.0
     */
    private void requestStoragePermission(){
        getRxpermissions().requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted){//权限被允许
                            init();
                            initView();
                            notifyData();
                        }else if (permission.shouldShowRequestPermissionRationale){//本次被拒绝
                            killAll();
                        }else {//禁止
                            PermissionsUtil.showStoragePermissionTip(mContext);
                        }
                    }
                });
    }

    public RxPermissions getRxpermissions(){
        if (rxPermissions == null){
            rxPermissions = new RxPermissions(this);
        }
        return rxPermissions;
    }


    @Override
    public void onResume() {
        super.onResume();
        activity = this;

    }

    @Override
    public void onPause() {
        super.onPause();
        activity = null;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (AppConstants.LOCALE_CHANGE) {
            LogUtils.i(tag, "onRestart");
            AppConstants.LOCALE_CHANGE = false;
            restartMain();
        }
    }
    /**
     * 重启APP操作
     */
    public void restartMain() {
        //从后台返回前台重启MainActivity
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    /**
     * 监听系统语言的改变，更新语言
     */
    public void internationalizationInitial() {
//        Resources resources = getApplicationContext().getResources();//不能用applicationContext
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        String lang = config.locale.getLanguage();
        Log.i(tag, "lang: " + config.locale.getLanguage());
        if (!lang.equals("zh")) {
            config.locale = new Locale("en");
        }
        resources.updateConfiguration(config, dm);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            String lang = config.getLocales().get(0).getLanguage();
//            if (!lang.equals("zh")){
//
//            }
//
//            if (!lang.equals("zh") && !lang.equals("en")){
//                config.setLocale(Locale.ENGLISH);
//                resources.updateConfiguration(config, dm);
//            }
//        }else {
//            String lang = config.locale.getLanguage();
//
//            if (!lang.equals("zh") && !lang.equals("en")){
//                config.setLocale(Locale.ENGLISH);
//                resources.updateConfiguration(config, dm);
//            }
//        }


    }

    public <T extends View> T getView(@IdRes int viewId) {
        return (T) findViewById(viewId);
    }

    public static void killAll() {
        // 复制了一份mActivities 集合
        List<BaseActivity> copy;
        synchronized (mActivities) {
            copy = new LinkedList<BaseActivity>(mActivities);
        }
        for (BaseActivity activity : copy) {
            if (!activity.isDestroyed()) activity.finish();
        }
        // 杀死当前的进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }


    public void initView() {
    }

    public void init() {
    }

    public void notifyData() {
    }

    public void refreshLocale() {
    }

    /**
     * 都是检查网络
     */
    public boolean netStatusToast() {
        if (NetworkUtils.isConnected()) return true;
        ToastUtil.showLongToast(getResources().getString(R.string.net_cannotuse));
        return false;
    }




    @Override
    public void onBackPressed() {
        onBack();
    }

    public void onBack() {
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        synchronized (mActivities) {
            mActivities.remove(this);
        }
        fixInputMethodManagerLeak(this);
		if(receiver!=null){
			unregisterReceiver(receiver);
			receiver=null;
		}
    }

    /**
     * 修复输入法导致的内存泄漏问题
     */
    public void fixInputMethodManagerLeak(Context destContext) {
        if (destContext == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) destContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }

        String[] arr = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
        Field f = null;
        Object obj_get = null;
        for (int i = 0; i < arr.length; i++) {
            String param = arr[i];
            try {
                f = imm.getClass().getDeclaredField(param);
                if (f.isAccessible() == false) {
                    f.setAccessible(true);
                } // author: sodino mail:sodino@qq.com
                obj_get = f.get(imm);
                if (obj_get != null && obj_get instanceof View) {
                    View v_get = (View) obj_get;
                    if (v_get.getContext() == destContext) { // 被InputMethodManager持有引用的context是想要目标销毁的
                        f.set(imm, null); // 置空，破坏掉path to gc节点
                    } else {
                        // 不是想要目标销毁的，即为又进了另一层界面了，不要处理，避免影响原逻辑,也就不用继续for循环了

                        Log.i(tag, "fixInputMethodManagerLeak break, mContext is not suitable, get_context=" + v_get.getContext() + " dest_context=" + destContext);

                        break;
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

}
