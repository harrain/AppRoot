package code_base.ui.activity;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import code_base.util.LogUtils;
import code_base.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 所有Fragment的父类，封装了一些相同操作
 */
public  class BaseSimpleFragment<E> extends Fragment {


    private static final String ARG_PARAM1 = "param";
    private String mParam1;
    public List<E> datas;
    public Context mContext;
    public String tag = "BaseSimpleFragment";

    public BaseSimpleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BaseSimpleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BaseSimpleFragment newInstance(String param1, String param2) {
        BaseSimpleFragment fragment = new BaseSimpleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        datas = new ArrayList<>();
        mContext = getActivity();
        View view = initView(inflater,container);
        return view;
    }

    public  View initView(LayoutInflater inflater, ViewGroup container){return null;}

    /**
     * 更新adapter，或其他view
     */
    public void refreshUI(){}

    public void setDataAndRefresh(List<E> list){
        if (!isVisible()) return;
        if (list == null || list.size() == 0 ) {
            ToastUtil.showShortToast("获取数据为空");
            LogUtils.i(list.toString());
            return;
        }
        refreshUI();
    }

}
