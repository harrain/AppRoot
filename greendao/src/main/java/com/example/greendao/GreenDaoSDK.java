package com.example.greendao;

import android.content.Context;

import com.example.greendao.db.DaoMaster;
import com.example.greendao.db.DaoSession;

/**
 * Created by data on 2017/9/11.
 */

public class GreenDaoSDK {

    private static GreenDaoSDK instance;
    private DaoSession daoSession;

    private GreenDaoSDK() {
    }

    public static void init(Context context) {
        if (instance == null) {
            instance = new GreenDaoSDK();

            instance.initGreenDao(context);
        }
    }

    private void initGreenDao(Context context) {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, "statement.db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        daoSession = daoMaster.newSession();

    }

    public static GreenDaoSDK getInstance() {
        return instance;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
