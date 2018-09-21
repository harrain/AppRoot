package com.example.greendao.dao;

import android.util.Log;

import com.example.greendao.GreenDaoSDK;
import com.example.greendao.db.ItemDao;
import com.example.greendao.db.bean.Item;

import org.greenrobot.greendao.query.Query;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by net on 2018/5/10.
 */

public class ItemDBDao {

    private ItemDao dao;
    private Query<Item> query;

    private String TAG = "ItemDBDao";
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public ItemDBDao() {
        dao = GreenDaoSDK.getInstance().getDaoSession().getItemDao();
        query = dao.queryBuilder().build();
    }

    private static ItemDBDao instance;

    public static ItemDBDao getInstance() {
        if (instance == null) {
            instance = new ItemDBDao();
        }
        return instance;
    }

    public List<Item> queryAll() {
        return query.forCurrentThread().list();
    }

    public List<Item> queryForEvaluation() {
        return dao.queryBuilder().where(ItemDao.Properties.I_status.eq(1)).build().forCurrentThread().list();
    }

    public List<Item> queryByItemClass(int itemClass) {
        return dao.queryBuilder().where(ItemDao.Properties.NItemClass.eq(itemClass)).build().forCurrentThread().list();
    }

    public List<Item> queryByItemClassForEvaluation(int itemClass) {
        return dao.queryBuilder().where(ItemDao.Properties.NItemClass.eq(itemClass), ItemDao.Properties.I_status.eq(1)).build().forCurrentThread().list();
    }

    public List<Item> queryByLimit(int offset, int item_count) {

        return dao.queryBuilder().where(ItemDao.Properties.I_status.eq(0)).limit(item_count).offset(offset * item_count).build().forCurrentThread().list();
    }

    public List<Item> queryUpload() {
        return dao.queryBuilder().where(
                ItemDao.Properties.NItemClass.in(1, 2, 3, 10, 30, 40, 50) //除了person，其他的查入
                , ItemDao.Properties.UploadStatus.eq(0)  //未upload的
                , ItemDao.Properties.I_status.eq(1)      //已评价的
        ).build().forCurrentThread().list();
    }

    public Item searchItem(String id) {
        try {

            return dao.queryBuilder().where(ItemDao.Properties.I_rate_id.eq(id)).build().forCurrentThread().unique();
        }catch (Exception e){
//            Log.e(TAG,"searchItem --没找到对应ID的item");
        }
        return null;
    }

    public void deleteItem(Item item) {
        dao.delete(item);
        if (itemChangeListener!=null) itemChangeListener.itemChanged(item,DELETE);
    }

    public void deleteItems(ArrayList<Item> deletes) {
        dao.deleteInTx(deletes);
        if (itemsChangeListener!=null) itemsChangeListener.itemsChanged(deletes,DELETE);
    }

    public void insertItem(Item item) {
        item.setInsert_time(sdf.format(new Date()));
        long iid = dao.insertOrReplace(item);
        Log.i(TAG,"insertItem: rateid:"+item.getI_rate_id()+" rowid:"+iid);
        if (itemChangeListener!=null) itemChangeListener.itemChanged(item,INSERT);
    }//insertOrReplace 有替换无插 会查数据库;save有更无插key必须是默认ID（Long）只看ID 更快捷

    public void insertOrSkip(Item item) {
        try {
//            item.setInsert_time(sdf.format(new Date()));
            dao.insert(item);
            if (itemChangeListener!=null) itemChangeListener.itemChanged(item,INSERT);
        } catch (Exception e) {
            Log.e(TAG,"insertOrSkip error:"+e.getMessage()+" id:"+item.getI_rate_id());
        }
    }

    public void updateItem(Item item) {
        item.setUpdate_time(sdf.format(new Date()));
        dao.update(item);
        if (itemChangeListener!=null) itemChangeListener.itemChanged(item,UPDATE);
    }

    public void insertItems(List<Item> items) {
        try {
            dao.insertInTx(items);
            if (itemsChangeListener!=null) itemsChangeListener.itemsChanged(items,INSERT);
        } catch (Exception e) {
            Log.e(TAG,"insertItems "+e.getMessage());//如果某一item插入出错，全部都无法插入数据库
        }
    }


    public List<Item> queryForPersonEvaluation() {
        return dao.queryBuilder().where(ItemDao.Properties.NItemClass.eq(20), ItemDao.Properties.I_status.eq(1)).build().list();
    }

    public ItemDao getDao() {
        return dao;
    }

    public void setItemChangeListener(ItemChangeListener listener){
        itemChangeListener = listener;
    }

    public void setItemsChangeListener(ItemsChangeListener listener){
        itemsChangeListener = listener;
    }

    public static int INSERT = 0;
    public static int UPDATE = 1;
    public static int DELETE = -1;
    private ItemChangeListener itemChangeListener;
    private ItemsChangeListener itemsChangeListener;

    public interface ItemChangeListener{
        void itemChanged(Item item,int type);
    }

    public interface ItemsChangeListener{
        void itemsChanged(List<Item> items,int type);
    }
}
