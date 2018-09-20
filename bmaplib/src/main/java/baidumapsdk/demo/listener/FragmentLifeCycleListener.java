package baidumapsdk.demo.listener;

import android.view.View;

/**
 * Created by net on 2018/1/3.
 * 用于MapFragment的生命周期函数回调的接口
 */

public interface FragmentLifeCycleListener {
    void onAttach();
    void onDetach();
    void onCreate();
    void onViewCreated();
    void onStart();
    void onResume();
    void onPause();
    void onStop();
    void onDestroyView();
    void onDestroy();

    void onHiddenChanged(boolean hidden); //show和hide时回调
}
