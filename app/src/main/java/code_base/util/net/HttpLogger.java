package code_base.util.net;

import android.util.Log;

import okhttp3.logging.HttpLoggingInterceptor;
/**
 * 网络log器
 */
public class HttpLogger implements HttpLoggingInterceptor.Logger {
        @Override
        public void log(String message) {
            Log.i("HttpLogInfo", message);
        }
}

