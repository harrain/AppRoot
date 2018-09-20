package baidumapsdk.demo.widget;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.SupportMapFragment;

import baidumapsdk.demo.listener.FragmentLifeCycleListener;

/**
 * Created by net on 2018/5/17.
 * 继承百度地图的SupportMapFragment，各个生命周期可回调；并提供基layout，可在MapView之上添加各种控件；方便创建移植
 * 配合MapFragmentHelper使用，可快速在地图上显示定位点、marker、线，比例尺等
 */

public class MapFragment extends SupportMapFragment {
    RelativeLayout rootLayout;//该Fragment显示的layout

    private FragmentLifeCycleListener fragmentLifeCycleListener;
    private String tag = "MapFragment";

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (fragmentLifeCycleListener != null) fragmentLifeCycleListener.onCreate();
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        try {

            MapView mapView = (MapView) super.onCreateView(layoutInflater, viewGroup, bundle);
            rootLayout = new RelativeLayout(getActivity());
            rootLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            rootLayout.addView(mapView, params);//该Fragment显示的是包含mapView的rootlayout
            return rootLayout;
        }catch (Exception e){ Log.e(tag,"onCreateView: "+e.getMessage());}
        return null;
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (fragmentLifeCycleListener != null) fragmentLifeCycleListener.onViewCreated();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (fragmentLifeCycleListener != null) fragmentLifeCycleListener.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (fragmentLifeCycleListener != null) fragmentLifeCycleListener.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (fragmentLifeCycleListener != null) fragmentLifeCycleListener.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (fragmentLifeCycleListener != null) fragmentLifeCycleListener.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (fragmentLifeCycleListener != null) fragmentLifeCycleListener.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (fragmentLifeCycleListener != null) fragmentLifeCycleListener.onDestroy();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (fragmentLifeCycleListener != null) fragmentLifeCycleListener.onAttach();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (fragmentLifeCycleListener != null) fragmentLifeCycleListener.onDetach();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (fragmentLifeCycleListener != null) fragmentLifeCycleListener.onHiddenChanged(hidden);
    }
    /**
     * 通过 mapfragment.setFragmentLifeCycleListener(new FragmentLifeCycle(){}) 可只写某一特定回调生命周期函数
     */
    public void setFragmentLifeCycleListener(FragmentLifeCycleListener lifeCycleListener) {
        fragmentLifeCycleListener = lifeCycleListener;
    }

    public RelativeLayout getRootLayout() {
        return rootLayout;
    }
}
