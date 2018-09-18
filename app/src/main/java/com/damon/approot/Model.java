package com.damon.approot;

import android.content.Intent;

public class Model {
    public String text;
    public Intent intent;

    Model(String text, Intent intent) {
        this.text = text;
        this.intent = intent;
    }
}
