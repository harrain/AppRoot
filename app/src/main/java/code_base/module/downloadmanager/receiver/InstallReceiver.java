package code_base.module.downloadmanager.receiver;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.util.Log;


import java.io.File;

import code_base.AppConstants;
import code_base.module.downloadmanager.common.CommonCons;


/**
 * 安装下载接收器
 *
 */

public class InstallReceiver extends BroadcastReceiver {

    private static final String TAG = "InstallReceiver";


    // 安装下载接收器
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
         long downloadApkId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

            installApk(context,downloadApkId);
        }
    }

    // 安装Apk
    private void installApk(Context context, long downloadApkId) {

        try {
            Intent i = new Intent(Intent.ACTION_VIEW);
            String filePath = CommonCons.APP_FILE_NAME;
            Uri data;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                data = FileProvider.getUriForFile(context, AppConstants.FILEPROVIDER_AUTHORITY,new File(filePath));
                i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }else {
                data = Uri.parse("file://" + filePath);
            }
            i.setDataAndType(data, "application/vnd.android.package-archive");
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }catch (Exception e){
            Log.e(TAG,"安装失败");
            e.printStackTrace();
        }

    }
}