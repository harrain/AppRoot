package code_base.ui.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.ShapeBadgeItem;
import code_base.util.LogUtils;
import com.damon.approot.R;

import code_base.listener.PermissionResultListener;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import code_base.util.view.UIConvertUtils;
import io.reactivex.functions.Consumer;

public class MainActivity extends BaseTitleActivity {


    public RxPermissions rxPermissions;

    public boolean firstEnter = true;
    public RadioGroup mainGroup;
    public BottomNavigationBar bottomNavigationBar;
    public ShapeBadgeItem shapeBadgeItem;
    public BottomNavigationItem profileBNI;
    protected RadioButton frontRb;

    @Override
    public void init() {
        super.init();

        rxPermissions = new RxPermissions(this);
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_main);
        super.initView();
        mainGroup = (RadioGroup) findViewById(R.id.main_bottom);
        mTBack.setVisibility(View.GONE);
        backDecor.setVisibility(View.GONE);
        leftTv.setVisibility(View.VISIBLE);
        leftTv.setText(getResources().getString(R.string.app_name));

        initMainGroup();

//        TextBadgeItem numberBadgeItem = new TextBadgeItem();
//        shapeBadgeItem = new ShapeBadgeItem();
//        profileBNI = new BottomNavigationItem(R.drawable.profile_pressed, getResources().getString(R.string.navig_me));
//        profileBNI.setActiveColorResource(R.color.wechat_green);
//
//        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
////        bottomNavigationBar.setBackgroundColor(getResources().getColor(R.color.white));
//        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
//        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
//        bottomNavigationBar
//
//                .addItem(new BottomNavigationItem(R.drawable.weixin_pressed, getResources().getString(R.string.navig_evalua)).setActiveColorResource(R.color.wechat_green))
//                .addItem(new BottomNavigationItem(R.drawable.map_pressed, getResources().getString(R.string.navig_second)).setActiveColorResource(R.color.wechat_green))
//                .addItem(new BottomNavigationItem(R.drawable.find_pressed, getResources().getString(R.string.navig_find)).setActiveColorResource(R.color.wechat_green))
//                .addItem(profileBNI)
//                .initialise();
//
//        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener(){
//            @Override
//            public void onTabSelected(int position) {
//                bottomTabSelected(position);
//            }
//            @Override
//            public void onTabUnselected(int position) {
//            }
//            @Override
//            public void onTabReselected(int position) {
//            }
//        });

//        numberBadgeItem.setText("1");
//        numberBadgeItem.setBorderWidth(2);
//        shapeBadgeItem.setShape(ShapeBadgeItem.SHAPE_OVAL);
//        shapeBadgeItem.setSizeInDp(this,8,8);

    }

    private void initMainGroup(){
        mainGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int position = 0;
                switch (checkedId){
                    case R.id.menu_item_frontpage:
                        position = 0;
                        break;
                    case R.id.menu_item_two:
                        position = 1;
                        break;
                    case R.id.menu_item_three:
                        position = 2;
                        break;
                    case R.id.menu_personal_center:
                        position = 3;
                        break;
                }
                bottomTabSelected(position);
            }
        });
        frontRb = findViewById(R.id.menu_item_frontpage);
        RadioButton two = findViewById(R.id.menu_item_two);
        RadioButton three = findViewById(R.id.menu_item_three);
        RadioButton me = findViewById(R.id.menu_personal_center);
        int tabPicL =  UIConvertUtils.dp2px(25);
        Rect rect = new Rect(0,0, tabPicL,tabPicL);

        Drawable[] fdrawables = frontRb.getCompoundDrawables();
        fdrawables[1].setBounds(rect);
        frontRb.setCompoundDrawables(null,fdrawables[1],null,null);

        Drawable[] twoDr = two.getCompoundDrawables();
        twoDr[1].setBounds(rect);
        two.setCompoundDrawables(null,twoDr[1],null,null);

        Rect rect2 = new Rect(0,0,tabPicL*96/84,tabPicL);

        Drawable[] threeDr = three.getCompoundDrawables();
        threeDr[1].setBounds(rect2);
        three.setCompoundDrawables(null,threeDr[1],null,null);

        Drawable[] mDr = me.getCompoundDrawables();
        mDr[1].setBounds(rect2);
        me.setCompoundDrawables(null,mDr[1],null,null);
    }

    public void bottomTabSelected(int position){}


    public void showProfileBadge(){
        shapeBadgeItem.show();
        profileBNI.setBadgeItem(shapeBadgeItem);
        bottomNavigationBar.initialise();
    }

    public void clearProfileBadge(){
        Field badgeField = null;
        try {
            badgeField = profileBNI.getClass().getDeclaredField("mBadgeItem");
            badgeField.setAccessible(true);
            badgeField.set(profileBNI,null);
            bottomNavigationBar.initialise();
        } catch (NoSuchFieldException e) {e.printStackTrace();
        }catch (IllegalAccessException e){e.printStackTrace();}
    }





    public void requestPermission(final PermissionResultListener listener, final String... permissions) {

        rxPermissions.request(permissions)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        listener.onHandlePermissionResult(granted);

                    }
                });
    }


    public void onCheckedChange(View v) {

        LogUtils.i("" + v.getId());

    }





}
