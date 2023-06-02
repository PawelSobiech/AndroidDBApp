package com.example.app2_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "telefony")
public class Element {
    public Element(@NonNull String producent, String model, String wersja_android, String adres_www)
    {
        mProducent = producent;
        mModel = model;
        mWersja_Android = wersja_android;
        mAdres_WWW = adres_www;
    }
    public Element()
    {
        mProducent = "";
        mModel = "";
        mWersja_Android = "";
        mAdres_WWW = "";
    }
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private long mID;
    @NonNull
    @ColumnInfo(name = "producent")
    private String mProducent;
    @NonNull
    @ColumnInfo(name = "model")
    private String mModel;
    @NonNull
    @ColumnInfo(name = "wersja_android")
    private String mWersja_Android;
    @NonNull
    @ColumnInfo(name = "adres_www")
    private String mAdres_WWW;

    public long getMID() {
        return mID;
    }

    @NonNull
    public String getMAdres_WWW() {
        return mAdres_WWW;
    }

    @NonNull
    public String getMModel() {
        return mModel;
    }

    @NonNull
    public String getMProducent() {
        return mProducent;
    }

    @NonNull
    public String getMWersja_Android() {
        return mWersja_Android;
    }

    public void setMAdres_WWW(@NonNull String mAdres_WWW) {
        this.mAdres_WWW = mAdres_WWW;
    }

    public void setMID(long mID) {
        this.mID = mID;
    }

    public void setMModel(@NonNull String mModel) {
        this.mModel = mModel;
    }

    public void setMProducent(@NonNull String mProducent) {
        this.mProducent = mProducent;
    }

    public void setMWersja_Android(@NonNull String mWersja_Android) {
        this.mWersja_Android = mWersja_Android;
    }

    public Element getElement()
    {
        return this;
    }
}