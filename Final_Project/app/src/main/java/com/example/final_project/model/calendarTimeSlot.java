package com.example.final_project.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity
public class calendarTimeSlot {
    @PrimaryKey(autoGenerate = true)
    public long id;
    @ColumnInfo
    public double startTime;
    @ColumnInfo
    public double endTime;
    @ColumnInfo
    public boolean filled = false;
    @ColumnInfo
    public String courseNameInTimeSlot = "Empty";
    @ColumnInfo
    public String dayOfWeek;

    public calendarTimeSlot(double startTime, double endTime, String dayOfWeek){
        this.startTime = startTime;
        this.endTime = endTime;
        this.dayOfWeek = dayOfWeek;
    }
    public void setTimeSlot(String courseName){
        filled = true;
        courseNameInTimeSlot = courseName;
    }
    public long getId() {
        return id;
    }

}
