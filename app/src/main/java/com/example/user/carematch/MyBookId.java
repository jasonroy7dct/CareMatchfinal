package com.example.user.carematch;


import android.support.annotation.NonNull;

public class MyBookId {

    public String myBookId;

    public <T extends MyBookId>T withId(@NonNull final  String id){
        this.myBookId = id;
        return (T) this;
    }

}