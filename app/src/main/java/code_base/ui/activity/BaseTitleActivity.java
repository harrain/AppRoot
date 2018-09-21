package code_base.ui.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.damon.approot.R;


/**
 * Created by stephen on 2017/7/25.
 * 封装了titlebar的绑定和标题显示、返回操作
 */

public class BaseTitleActivity extends BaseActivity {


    public Toolbar mTitlebar;

    public TextView mTTitle;
    public ImageView mTBack;
    public ImageView mTMore;
    public TextView leftTv;
    public View backDecor;
    public ProgressBar progressBar;
    private FrameLayout tBack;

    @Override
    public void initView() {
        super.initView();
        initTitleBar();
    }

    public void initTitleBar() {
        mTitlebar = (Toolbar) findViewById(R.id.titlebar);
        setSupportActionBar(mTitlebar);
        mTTitle = (TextView) findViewById(R.id.toolbar_title);
        mTBack = (ImageView) findViewById(R.id.toolbar_back);
        mTMore = (ImageView) findViewById(R.id.toolbar_more);
        leftTv = (TextView) findViewById(R.id.toolbar_left_tv);
        progressBar = (ProgressBar) findViewById(R.id.toolbar_pb);

//        mTBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBack();
//            }
//        });
        mTMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moreAction();
            }
        });

        tBack = (FrameLayout) findViewById(R.id.toolbar_back_fl);
        tBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBack();
            }
        });
        backDecor = findViewById(R.id.back_fl_decor);
    }


    public void moreAction() {}

    public void setmTTitle(String text){
        mTTitle.setText(text);
    }

    public void setTitlebarBackgroundColor(int color){
//        mTitlebar.setBackgroundColor(color);
    }

    public void setTitleMoreIcon(int resId){
        mTMore.setImageResource(resId);
    }

    public void setmTBackHide(){
        tBack.setVisibility(View.GONE);
        backDecor.setVisibility(View.GONE);
    }

    public void setmTMoreHide(){
        mTMore.setVisibility(View.GONE);

    }

    public void setMoreTvVisible(){
        mTMore.setVisibility(View.GONE);
    }
    public void setTMoreVisible(){
        mTMore.setVisibility(View.VISIBLE);
    }



//    public void setMoreTvBackground(@DrawableRes int drawableId){
//        more_ib.setPadding(UIConvertUtils.dp2px(5),UIConvertUtils.dp2px(5),UIConvertUtils.dp2px(5),UIConvertUtils.dp2px(5));
//        more_ib.setBackgroundResource(drawableId);
//    }

    public ImageView getmTMore() {
        return mTMore;
    }
}
