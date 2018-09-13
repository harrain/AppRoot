package code_base.ui.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import code_base.util.NetworkUtils;

/**
 * Created by data on 2017/10/19.
 * 6.0可用 7以上不可用
 */

public class NetStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (NetworkUtils.isConnected()){
        }else {

        }
    }
}
