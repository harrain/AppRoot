package com.damon.approot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import code_base.ui.activity.AbstractLauncherActivity;

public class LauncherActivity extends AbstractLauncherActivity {

    @Override
    public void initView() {
        super.initView();

        setContentView(R.layout.activity_launcher);
    }

    @Override
    public void grantTodo() {
        super.grantTodo();

        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}
