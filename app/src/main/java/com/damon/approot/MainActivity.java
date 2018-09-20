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

import code_base.ui.activity.BaseActivity;

public class MainActivity extends BaseActivity {

    private List<Model> datas;
    private RecyclerView recyclerView;

    @Override
    public void init() {
        super.init();

        datas = new ArrayList<>();
        datas.add(new Model("【惊！太好用了】最快速度使用百度地图",new Intent(this,MapAboutActivity.class)));
    }

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.recyclerview_nofade_noscrollbar);

        recyclerView = findViewById(R.id.nofade_nobar_wrap_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .colorResId(R.color.decor_line).sizeResId(R.dimen.decor_height).build());
    }

    @Override
    public void notifyData() {
        super.notifyData();

        CommonAdapter<com.damon.approot.Model> adapter = new CommonAdapter<com.damon.approot.Model>(this,R.layout.textview_center,datas) {

            @Override
            protected void convert(ViewHolder holder, com.damon.approot.Model model, int position) {
                holder.setText(R.id.center_tv,model.text);
            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                startActivity(datas.get(position).intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        recyclerView.setAdapter(adapter);
    }

}
