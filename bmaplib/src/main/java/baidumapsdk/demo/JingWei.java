package baidumapsdk.demo;

import android.os.Parcel;
import android.os.Parcelable;

public class JingWei implements Parcelable{
    private double lat;
    private double lng;
    private String address;

    public JingWei(){}

    public JingWei(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public JingWei(double lat, double lng, String address) {
        this.lat = lat;
        this.lng = lng;
        this.address = address;
    }

    protected JingWei(Parcel in) {
        lat = in.readDouble();
        lng = in.readDouble();
        address = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(lat);
        dest.writeDouble(lng);
        dest.writeString(address);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<JingWei> CREATOR = new Creator<JingWei>() {
        @Override
        public JingWei createFromParcel(Parcel in) {
            return new JingWei(in);
        }

        @Override
        public JingWei[] newArray(int size) {
            return new JingWei[size];
        }
    };

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
