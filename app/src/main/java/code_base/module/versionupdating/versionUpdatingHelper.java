package code_base.module.versionupdating;

import android.content.Context;
import android.content.DialogInterface;


import code_base.module.downloadmanager.utils.UpdateAppManager;
import code_base.util.LogUtils;
import code_base.util.ToastUtil;
import code_base.util.Utils;
import code_base.util.view.AlertDialogUtil;

/**
 * 检查版本更新，调用下载器
 */

public class versionUpdatingHelper {

    private String tag = "versionUpdatingHelper";
    Context mContext;

    public versionUpdatingHelper(Context context) {

        mContext = context;
    }
    /**
     * 检查服务器是否有新的安装包，有则弹窗提示更新
     */
    public void antoUpdateVersion(String vertionUrl, final String url, final String appName){
        if (Utils.isDebug()) return;//只有release版本，才会检查更新
        LogUtils.i(tag,"antoUpdateVersion","---");
        VersionCheckUtils versionUtils = new VersionCheckUtils();
        versionUtils.initHandler();
        versionUtils.registerVersionResultListener(new VersionCheckUtils.VersionResultListener() {
            @Override
            public void onResultFlag(final String apkUrl) {
                AlertDialogUtil.showAlertDialogTwo(mContext,"提示更新","检测到应用有新版本，是否需要更新?", new AlertDialogUtil.AlertListener() {
                    @Override
                    public void positiveResult(DialogInterface dialog, int which) {
                        downloadapk(apkUrl, appName);
                        ToastUtil.showShortToast("正在后台下载最新版本");
                    }
                });
            }
        });
        versionUtils.checkVerson(vertionUrl,url);
    }

    /**
     * 使用系统downloadManager下载安装包
     */
    private void downloadapk(String url, String appName) {
        UpdateAppManager.downloadApk(mContext,url,"应用更新",appName);
    }
}
