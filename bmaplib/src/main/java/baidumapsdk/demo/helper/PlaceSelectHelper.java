package baidumapsdk.demo.helper;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import baidumapsdk.demo.activity.PlaceSelectActivity;
import baidumapsdk.demo.listener.TodoListener;

/**
 * Created by net on 2018/9/7.
 */

public class PlaceSelectHelper {

    private static PlaceSelectHelper instance;
    private TodoListener todoListener;

    private PlaceSelectHelper(){}

    public static PlaceSelectHelper getInstance() {
        if (instance == null) instance = new PlaceSelectHelper();
        return instance;
    }

    public PlaceSelectHelper gotoActivity(Context context){
        PlaceSelectActivity.startActivity((AppCompatActivity)context);
        return this;
    }

    public PlaceSelectHelper setTodoListener(TodoListener listener){
        todoListener = listener;
        return this;
    }

    public TodoListener getTodoListener() {
        return todoListener;
    }


}
