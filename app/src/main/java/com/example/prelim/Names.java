package com.example.prelim;

import android.net.Uri;

public class Names {
    Uri imageUri;
    int id;
    String name;

    //constructor
    public Names(Uri imageUri, int id, String name) {
        this.imageUri = imageUri;
        this.id = id;
        this.name = name;
    }

    //getters and setters
    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
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
}
