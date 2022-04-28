package com.example.final_project.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.final_project.model.calendarTimeSlot;
import com.example.final_project.model.courseInfo;

@Database(entities = {courseInfo.class, calendarTimeSlot.class}, version = 1)
public abstract class appDatabase extends RoomDatabase {
    public abstract calenderDao getCalenderDao();
    public abstract courseInfoDao getCourseInfoDao();
}
