package com.example.coder_rb.myapplication.Model;

import java.io.Serializable;

public class wall  implements Serializable {
    private int width, height;
    public String id;
    private static final long serialVersionUID = 1L;
    public String title,desc,url;

    public wall(){

    }

    public wall(int width, int height, String title, String desc, String url) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.desc = desc;
        this.url = url;
    }

    public wall(String id) {
        this.id = id;
    }
}
