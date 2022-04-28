package com.example.final_project.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class courseInfo {
    @PrimaryKey(autoGenerate = true)
    public long id;
    @ColumnInfo
    public double startTime;
    @ColumnInfo
    public double endTime;
    @ColumnInfo
    public String name;
    @ColumnInfo
    public String meetingPlace;
    @ColumnInfo
    public int creditHours;
    @ColumnInfo
    public boolean Monday = false;
    @ColumnInfo
    public boolean Tuesday = false;
    @ColumnInfo
    public boolean Wednesday = false;
    @ColumnInfo
    public boolean Thursday = false;
    @ColumnInfo
    public boolean Friday = false;

   public courseInfo(double startTime, double endTime, String name, String meetingPlace,  int creditHours, boolean Monday, boolean Tuesday, boolean Wednesday, boolean Thursday, boolean Friday){
        this.startTime = startTime;
        this.endTime = endTime;
        this.name = name;
        this.meetingPlace = meetingPlace;
        this.Monday = Monday;
        this.Tuesday = Tuesday;
        this.Wednesday = Wednesday;
        this.Thursday = Thursday;
        this.Friday = Friday;
        this.creditHours = creditHours;
    }

}
