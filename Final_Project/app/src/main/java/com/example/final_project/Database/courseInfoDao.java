package com.example.final_project.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.final_project.model.calendarTimeSlot;
import com.example.final_project.model.courseInfo;

import java.util.List;

@Dao
public interface courseInfoDao {
    @Insert
    public long insert(courseInfo newCourse);
    @Query("SELECT * FROM courseInfo")
    public List<courseInfo> getAll();
    @Update
    public void update(courseInfo courseinfo);
    @Delete
    public void delete(courseInfo courseinfo);
}
