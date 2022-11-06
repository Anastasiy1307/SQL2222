package com.example.sql;

public class DataModal {
    private String Image;
    private String Name;
    private String Speed;
    private String Power;
    public DataModal(String Image, String Name, String Speed, String Power){

        this.Image=Image;
        this.Name=Name;
        this.Speed=Speed;
        this.Power=Power;
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
