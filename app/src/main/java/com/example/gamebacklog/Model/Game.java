package com.example.gamebacklog.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.example.gamebacklog.R;

import java.io.Serializable;

@Entity(tableName = "gameBacklog_table")

public class Game implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "title")
    private String mTitle;

    @ColumnInfo(name = "status")
    private String mStatus;

    @ColumnInfo(name = "platform")
    private String mPlatform;

    @ColumnInfo(name = "date")
    private String mDate;

    public Game(String mTitle, String mStatus, String mPlatform, String mDate) {
        this.mTitle = mTitle;
        this.mStatus = mStatus;
        this.mPlatform = mPlatform;
        this.mDate = mDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getMStatus() {
        return mStatus;
    }

    public void setmStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public String getMPlatform() {
        return mPlatform;
    }

    public void setmPlatform(String mPlatform) {
        this.mPlatform = mPlatform;
    }

    public String getMDate() {
        return mDate;
    }

    public void setmDate(String mDate) { this.mDate = mDate; }

    public int getmStatusInt(String currentStatus) {
        int status = 0;
        if(currentStatus.equals(R.string.spinnerOption1))
            status = 0;
        else if(currentStatus.equals(R.string.spinnerOption2))
            status = 1;
        else if(currentStatus.equals(R.string.spinnerOption3))
            status = 2;
        else if(currentStatus.equals(R.string.spinnerOption4))
            status = 3;
        return status;
    }
}
