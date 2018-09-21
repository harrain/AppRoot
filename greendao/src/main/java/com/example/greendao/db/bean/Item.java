package com.example.greendao.db.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by net on 2018/5/10.
 *
 */
@Entity
public class Item implements Parcelable{

    /**
     * int  nItemId ;     // 4 bytes 评价对象在服务器的ID
     char sItemPic ;    // 32 bytes 评价对象主图的文件名
     char sItemTitle ;  // 32 bytes 评价对象的简短描述

     int  nItemClass ;  // 4 bytes 评价对象的类别（ 比如 1 代表京东，10 表示地点）
     char sClassId ;    // 16 bytes 评价对象在类别内的ID （比如在京东的ID）

     int  nItemStatus ; // 4 bytes 评价对象的状态 （已评价，未评价，已删除，等等各种状态，可以把七种评价结果包含在内）
     int  nStatusTime ; // 4 bytes 评价对象最后一次改变状态的时间 （从一个时间点开始的秒数，比如从2018年1月1日0点开始）

     *
     *
     * i_rate_id : 3
     * t_rate_time : 2018-04-02 16:41:51
     * i_user_id : 3
     * i_item_id : 29
     * i_rate_value : 0
     * t_submit_time : 0000-00-00 00:00:00
     * c_rate_ip : 114.242.130.89
     * i_jing : 40
     * i_wei : 0
     * i_status : 2
     * c_item_title : 德芙德芙巧克力混合3...
     * c_item_pic : data/5315233.jpg
     * c_class_id : 5315233
     * c_image : data/5315233.jpg
     * c_item_name : 德芙德芙巧克力混合3...
     * i_item_code : 5315233
     */
//    private Long local_id;
    @Id
    private String i_rate_id;
    private int  nItemId;
    private String sItemPic ;    // 32 bytes 评价对象主图的文件名
    private String sItemTitle ;  // 32 bytes 评价对象的简短描述

    private int  nItemClass ;  // 4 bytes 评价对象的类别（ 比如 1 代表京东，10 表示地点）
    private String sClassId ;    // 16 bytes 评价对象在类别内的ID （比如在京东的ID）

    private int  nItemStatus ; // 4 bytes 评价对象的状态 （已评价，未评价，已删除，等等各种状态，可以把七种评价结果包含在内）暂时定为 0 未评价，1 已评价，2 已删除
    private int  nStatusTime ; // 4 bytes 评价对象最后一次改变状态的时间 （从一个时间点开始的秒数，比如从2018年1月1日0点开始）
    private int uploadStatus;//  0 notuploaded ; 1 uploaded ; 2 uploading
    private int nLat;
    private int nlng;

    private String t_rate_time;
    private String i_user_id;
    private String i_item_id;
    private String i_rate_value; // 评价模板的索引
    private String i_offitial_rates;
    private String t_submit_time;
    private String c_rate_ip;
    private String i_jing = "0";
    private String i_wei = "0";
    private String i_status; //item 0 未评价; 1 已评价； 2 已删除
    private String c_item_title;
    private String c_item_pic;
    private String c_class_id;
    private String c_image;
    private String c_item_name;
    private String i_item_code;
    private String i_mall_id;

    private String insert_time;
    private String update_time;


    @Generated(hash = 266713215)
    public Item(String i_rate_id, int nItemId, String sItemPic, String sItemTitle, int nItemClass,
            String sClassId, int nItemStatus, int nStatusTime, int uploadStatus, int nLat, int nlng,
            String t_rate_time, String i_user_id, String i_item_id, String i_rate_value, String i_offitial_rates,
            String t_submit_time, String c_rate_ip, String i_jing, String i_wei, String i_status,
            String c_item_title, String c_item_pic, String c_class_id, String c_image, String c_item_name,
            String i_item_code, String i_mall_id, String insert_time, String update_time) {
        this.i_rate_id = i_rate_id;
        this.nItemId = nItemId;
        this.sItemPic = sItemPic;
        this.sItemTitle = sItemTitle;
        this.nItemClass = nItemClass;
        this.sClassId = sClassId;
        this.nItemStatus = nItemStatus;
        this.nStatusTime = nStatusTime;
        this.uploadStatus = uploadStatus;
        this.nLat = nLat;
        this.nlng = nlng;
        this.t_rate_time = t_rate_time;
        this.i_user_id = i_user_id;
        this.i_item_id = i_item_id;
        this.i_rate_value = i_rate_value;
        this.i_offitial_rates = i_offitial_rates;
        this.t_submit_time = t_submit_time;
        this.c_rate_ip = c_rate_ip;
        this.i_jing = i_jing;
        this.i_wei = i_wei;
        this.i_status = i_status;
        this.c_item_title = c_item_title;
        this.c_item_pic = c_item_pic;
        this.c_class_id = c_class_id;
        this.c_image = c_image;
        this.c_item_name = c_item_name;
        this.i_item_code = i_item_code;
        this.i_mall_id = i_mall_id;
        this.insert_time = insert_time;
        this.update_time = update_time;
    }

    @Generated(hash = 1470900980)
    public Item() {
    }


    protected Item(Parcel in) {
        i_rate_id = in.readString();
        nItemId = in.readInt();
        sItemPic = in.readString();
        sItemTitle = in.readString();
        nItemClass = in.readInt();
        sClassId = in.readString();
        nItemStatus = in.readInt();
        nStatusTime = in.readInt();
        uploadStatus = in.readInt();
        nLat = in.readInt();
        nlng = in.readInt();
        t_rate_time = in.readString();
        i_user_id = in.readString();
        i_item_id = in.readString();
        i_rate_value = in.readString();
        i_offitial_rates = in.readString();
        t_submit_time = in.readString();
        c_rate_ip = in.readString();
        i_jing = in.readString();
        i_wei = in.readString();
        i_status = in.readString();
        c_item_title = in.readString();
        c_item_pic = in.readString();
        c_class_id = in.readString();
        c_image = in.readString();
        c_item_name = in.readString();
        i_item_code = in.readString();
        i_mall_id = in.readString();
        insert_time = in.readString();
        update_time = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(i_rate_id);
        dest.writeInt(nItemId);
        dest.writeString(sItemPic);
        dest.writeString(sItemTitle);
        dest.writeInt(nItemClass);
        dest.writeString(sClassId);
        dest.writeInt(nItemStatus);
        dest.writeInt(nStatusTime);
        dest.writeInt(uploadStatus);
        dest.writeInt(nLat);
        dest.writeInt(nlng);
        dest.writeString(t_rate_time);
        dest.writeString(i_user_id);
        dest.writeString(i_item_id);
        dest.writeString(i_rate_value);
        dest.writeString(i_offitial_rates);
        dest.writeString(t_submit_time);
        dest.writeString(c_rate_ip);
        dest.writeString(i_jing);
        dest.writeString(i_wei);
        dest.writeString(i_status);
        dest.writeString(c_item_title);
        dest.writeString(c_item_pic);
        dest.writeString(c_class_id);
        dest.writeString(c_image);
        dest.writeString(c_item_name);
        dest.writeString(i_item_code);
        dest.writeString(i_mall_id);
        dest.writeString(insert_time);
        dest.writeString(update_time);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public String getI_rate_id() {
        return i_rate_id;
    }

    public void setI_rate_id(String i_rate_id) {
        this.i_rate_id = i_rate_id;
    }

    public String getT_rate_time() {
        return t_rate_time;
    }

    public void setT_rate_time(String t_rate_time) {
        this.t_rate_time = t_rate_time;
    }

    public String getI_user_id() {
        return i_user_id;
    }

    public void setI_user_id(String i_user_id) {
        this.i_user_id = i_user_id;
    }

    public String getI_item_id() {
        return i_item_id;
    }

    public void setI_item_id(String i_item_id) {
        this.i_item_id = i_item_id;
    }

    public String getI_rate_value() {
        return i_rate_value;
    }

    public void setI_rate_value(String i_rate_value) {
        this.i_rate_value = i_rate_value;
    }

    public String getT_submit_time() {
        return t_submit_time;
    }

    public void setT_submit_time(String t_submit_time) {
        this.t_submit_time = t_submit_time;
    }

    public String getC_rate_ip() {
        return c_rate_ip;
    }

    public void setC_rate_ip(String c_rate_ip) {
        this.c_rate_ip = c_rate_ip;
    }

    public String getI_jing() {
        return i_jing;
    }

    public void setI_jing(String i_jing) {
        this.i_jing = i_jing;
    }

    public String getI_wei() {
        return i_wei;
    }

    public void setI_wei(String i_wei) {
        this.i_wei = i_wei;
    }

    public String getI_status() {
        return i_status;
    }

    public void setI_status(String i_status) {
        this.i_status = i_status;
    }

    public String getC_item_title() {
        return c_item_title;
    }

    public void setC_item_title(String c_item_title) {
        this.c_item_title = c_item_title;
    }

    public String getC_item_pic() {
        return c_item_pic;
    }

    public void setC_item_pic(String c_item_pic) {
        this.c_item_pic = c_item_pic;
    }

    public String getC_class_id() {
        return c_class_id;
    }

    public void setC_class_id(String c_class_id) {
        this.c_class_id = c_class_id;
    }

    public String getC_image() {
        return c_image;
    }

    public void setC_image(String c_image) {
        this.c_image = c_image;
    }

    public String getC_item_name() {
        return c_item_name;
    }

    public void setC_item_name(String c_item_name) {
        this.c_item_name = c_item_name;
    }

    public String getI_item_code() {
        return i_item_code;
    }

    public void setI_item_code(String i_item_code) {
        this.i_item_code = i_item_code;
    }

    @Override
    public String toString() {
        return "Item{" +
                "i_rate_id='" + i_rate_id + '\'' +
                ", t_rate_time='" + t_rate_time + '\'' +
                ", i_user_id='" + i_user_id + '\'' +
                ", i_item_id='" + i_item_id + '\'' +
                ", i_rate_value='" + i_rate_value + '\'' +
                ", t_submit_time='" + t_submit_time + '\'' +
                ", c_rate_ip='" + c_rate_ip + '\'' +
                ", i_jing='" + i_jing + '\'' +
                ", i_wei='" + i_wei + '\'' +
                ", i_status='" + i_status + '\'' +
                ", c_item_title='" + c_item_title + '\'' +
                ", c_item_pic='" + c_item_pic + '\'' +
                ", c_class_id='" + c_class_id + '\'' +
                ", c_image='" + c_image + '\'' +
                ", c_item_name='" + c_item_name + '\'' +
                ", i_item_code='" + i_item_code + '\'' +
                '}';
    }

    public String getI_mall_id() {
        return this.i_mall_id;
    }

    public void setI_mall_id(String i_mall_id) {
        this.i_mall_id = i_mall_id;
    }

    public int getNItemId() {
        return this.nItemId;
    }

    public void setNItemId(int nItemId) {
        this.nItemId = nItemId;
    }

    public String getSItemPic() {
        return this.sItemPic;
    }

    public void setSItemPic(String sItemPic) {
        this.sItemPic = sItemPic;
    }

    public String getSItemTitle() {
        return this.sItemTitle;
    }

    public void setSItemTitle(String sItemTitle) {
        this.sItemTitle = sItemTitle;
    }

    public int getNItemClass() {
        return this.nItemClass;
    }

    public void setNItemClass(int nItemClass) {
        this.nItemClass = nItemClass;
    }

    public String getSClassId() {
        return this.sClassId;
    }

    public void setSClassId(String sClassId) {
        this.sClassId = sClassId;
    }

    public int getNItemStatus() {
        return this.nItemStatus;
    }

    public void setNItemStatus(int nItemStatus) {
        this.nItemStatus = nItemStatus;
    }

    public int getNStatusTime() {
        return this.nStatusTime;
    }

    public void setNStatusTime(int nStatusTime) {
        this.nStatusTime = nStatusTime;
    }


    public String getI_offitial_rates() {
        return this.i_offitial_rates;
    }


    public void setI_offitial_rates(String i_offitial_rates) {
        this.i_offitial_rates = i_offitial_rates;
    }

    public String getInsert_time() {
        return this.insert_time;
    }

    public void setInsert_time(String insert_time) {
        this.insert_time = insert_time;
    }

    public String getUpdate_time() {
        return this.update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public int getUploadStatus() {
        return this.uploadStatus;
    }

    public void setUploadStatus(int uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    public int getNLat() {
        return this.nLat;
    }

    public void setNLat(int nLat) {
        this.nLat = nLat;
    }

    public int getNlng() {
        return this.nlng;
    }

    public void setNlng(int nlng) {
        this.nlng = nlng;
    }
}
