package code_base.listener;

/**
 * Created by stephen on 2017/7/25.
 * 网络请求完成接口
 */

public interface OnCompleteListener<T> {
    void onSuccess(T result);

    void onError(String error);
}
