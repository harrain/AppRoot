package com.damon.approot;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.greendao.dao.ItemDBDao;
import com.example.greendao.db.bean.Item;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import code_base.util.model.FileUtils;
import code_base.util.model.IOUtils;
import code_base.util.model.RandomUtils;

public class DBGreendaoActivity extends AppCompatActivity {
    String tag = "DBGreendaoActivity";

    private  String dbPath ;
    private RecyclerView recyclerView;
    List<Item> datas;
    private CommonAdapter<Item> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbgreendao);
        dbPath= getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)+"/"+"statement.db";
        datas = new ArrayList<>();

        recyclerView = findViewById(R.id.sqlite_gd_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .sizeResId(R.dimen.decor_height).colorResId(R.color.decor_line)
                .marginResId(R.dimen.activity_horizontal_margin).build());
        adapter = new CommonAdapter<Item>(this, R.layout.textview,datas) {
            @Override
            protected void convert(ViewHolder holder, Item item, int position) {
                holder.setText(R.id.textview,item.getSItemTitle());
            }
        };
        recyclerView.setAdapter(adapter);

        writeToSqlite(readdb());
    }

    public void assetsclick(View v){

        datas.addAll(ItemDBDao.getInstance().queryAll());
        adapter.notifyDataSetChanged();

    }

    public void insertclick(View view){

        String id = RandomUtils.generate16RandomString()+"@"+"0";
        String title = id+"随机数test";
        Item item = new Item();
        item.setI_rate_id(id);
        item.setSItemTitle(title);
        ItemDBDao.getInstance().insertItem(item);

        Item item1 = new Item();
        item1.setI_rate_id("zxcv@0");
        item1.setSItemTitle("zxcvtest");
        ItemDBDao.getInstance().insertItem(item1);

        ItemDBDao.getInstance().setItemChangeListener(new ItemDBDao.ItemChangeListener() {
            @Override
            public void itemChanged(Item item, int type) {
                if (datas!=null) {
                    if (datas.contains(item)) {
                        datas.set(datas.indexOf(item), item);//集合中有，则替换更新
                    } else {
                        datas.add(item);//没有则加入集合
                    }
                    if (recyclerView!=null)recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            if (adapter != null)adapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
    }

    private File getDbFile() {
        File file = null;
        try {

            file = FileUtils.writeFile(getAssets().open("statement.db"),dbPath);
        }catch (Exception e){
            e.printStackTrace();
        }
        return file;
    }

    private SQLiteDatabase opendb(){
        return SQLiteDatabase.openDatabase(getDbFile().getAbsolutePath(),null,SQLiteDatabase.OPEN_READONLY);
    }
    /**
     * 读现有的asset db文件数据
     */
    private List<Map<String,String>> readdb(){
        List<Map<String,String>> mapList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = opendb();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from ITEM", null);
        cursor.moveToFirst();
        while (cursor.moveToNext()){
            int idindex = cursor.getColumnIndex("I_RATE_ID");
            int titleindex = cursor.getColumnIndex("S_ITEM_TITLE");
            String id = cursor.getString(idindex);
            String title = cursor.getString(titleindex);

            Log.i(tag, "opendb: id "+id+" title "+title);

            Map<String,String> map = new ArrayMap<>();
            map.put("id",id);
            map.put("title",title);
            mapList.add(map);
        }
        cursor.close();
        return mapList;
    }
    /**
     * 写入本应用内的数据库
     */
    private void writeToSqlite(List<Map<String,String>> maps){
        List<Item> items = new ArrayList<>();
        for (Map<String, String> map : maps) {
            Item item = new Item();
            item.setI_rate_id(map.get("id"));
            item.setSItemTitle(map.get("title"));
            items.add(item);
        }
        ItemDBDao.getInstance().insertItems(items);
    }
}
