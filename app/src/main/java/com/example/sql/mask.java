package com.example.sql;

import android.os.Parcel;
import android.os.Parcelable;

public class mask implements Parcelable {

    private int ID;
    private String Image;
    private String Name;
    private String Speed;
    private String Power;
    public  mask(int ID,String Image,String Name,String Speed,String Power){
        this.ID=ID;
        this.Image=Image;
        this.Name=Name;
        this.Speed=Speed;
        this.Power=Power;
    }
    protected mask(Parcel in) {
        ID=in.readInt();
        Image=in.readString();
        Name=in.readString();
        Speed=in.readString();
        Power=in.readString();
    }

    public static final Creator<mask> CREATOR = new Creator<mask>() {
        @Override
        public mask createFromParcel(Parcel in) {
            return new mask(in);
        }

        @Override
        public mask[] newArray(int size) {
            return new mask[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(ID);
        parcel.writeString(Image);
        parcel.writeString(Name);
        parcel.writeString(Power);
        parcel.writeString(Speed);
    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSpeed() {
        return Speed;
    }

    public void setSpeed(String speed) {
        Speed = speed;
    }

    public String getPower() {
        return Power;
    }

    public void setPower(String power) {
        Power = power;
    }
}