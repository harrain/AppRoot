package com.damon.approot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import baidumapsdk.demo.JingWei;
import baidumapsdk.demo.activity.LocationInfoActivity;
import baidumapsdk.demo.activity.MarkerInfoWindowActivity;
import baidumapsdk.demo.helper.PlaceSelectHelper;
import baidumapsdk.demo.listener.TodoListener;
import code_base.ui.activity.BaseTitleActivity;

/**
 * 移植使用前，先阅读bmaplib下的README.md
 */
public class MapAboutActivity extends BaseTitleActivity {

    @Override
    public void initView() {

        setContentView(R.layout.activity_main);
        super.initView();
        setmTTitle("baidumap的集成和使用");

        final List<Model> models = new ArrayList<>();
        models.add(new Model("选择地点、位置显示且跳转导航",null));
        models.add(new Model("地图上marker、信息弹窗且跳转位置信息",new Intent(this, MarkerInfoWindowActivity.class)));
        RecyclerView recyclerView = findViewById(R.id.nofade_nobar_wrap_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .colorResId(R.color.decor_line).sizeResId(R.dimen.decor_height).build());
        CommonAdapter<Model> adapter = new CommonAdapter<Model>(this,R.layout.textview_center,models) {
            @Override
            protected void convert(ViewHolder holder, Model model, int position) {
                holder.setText(R.id.center_tv,model.text);
            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (models.get(position).text.equals("选择地点、位置显示且跳转导航")){
                    PlaceSelectHelper.getInstance().gotoActivity(MapAboutActivity.this).setTodoListener(new TodoListener() {

                        @Override
                        public void dothis(JingWei jingWei) {
                            double[] ll = {jingWei.getLat(),jingWei.getLng()};
                            LocationInfoActivity.startActivity(MapAboutActivity.this,ll,jingWei.getAddress());
                        }
                    });
                }
                if (models.get(position).intent != null) startActivity(models.get(position).intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        recyclerView.setAdapter(adapter);

    }
}
