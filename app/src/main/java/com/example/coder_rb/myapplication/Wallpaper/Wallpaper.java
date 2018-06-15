package com.example.coder_rb.myapplication.Wallpaper;

public class Wallpaper {

    private String name;
    private int categoryid;
    private int thumbnail;

    public Wallpaper(){

    }

    public Wallpaper(String name, int categoryid, int thumbnail) {
        this.name = name;
        this.categoryid = categoryid;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
