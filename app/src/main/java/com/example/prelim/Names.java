package com.example.prelim;

import android.net.Uri;

public class Names {
    private int id;
    private String name;
    private Uri imageUri;

//    public Names(int id, String name, Uri imageUri) {
//        this.id = id;
//        this.name = name;
//        this.imageUri = imageUri;
//    }


    public Names() {
    }

    public Names(Uri imageUri, String name) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }
}
