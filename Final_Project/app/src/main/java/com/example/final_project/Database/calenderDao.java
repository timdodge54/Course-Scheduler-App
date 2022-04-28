package com.example.final_project.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.final_project.model.calendarTimeSlot;

import java.util.List;

@Dao
public interface calenderDao {
    @Insert
    public long insert(calendarTimeSlot timeSlot);
    @Query("SELECT * FROM calendartimeslot")
    public List<calendarTimeSlot> getAll();
    @Query("Select * FROM calendartimeslot WHERE id = :id LIMIT 1")
    public calendarTimeSlot findById(long id);
    @Query("SELECT * FROM calendartimeslot WHERE startTime = :startTime AND dayOfWeek = :dayOfWeek LIMIT 1")
    public calendarTimeSlot findByTimeandDay(double startTime, String dayOfWeek);
    @Update
    public void update(calendarTimeSlot timeSlot);
    @Delete
    public void delete(calendarTimeSlot timeSlot);

}
