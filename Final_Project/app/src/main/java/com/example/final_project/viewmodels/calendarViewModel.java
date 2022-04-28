package com.example.final_project.viewmodels;

import android.app.Application;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.final_project.Database.appDatabase;
import com.example.final_project.model.calendarTimeSlot;

import java.util.ArrayList;
import java.util.List;

public class calendarViewModel extends AndroidViewModel {
    private appDatabase database;
    private MutableLiveData<Boolean> saving = new MutableLiveData<>();
    private ObservableArrayList<calendarTimeSlot> entries = new ObservableArrayList<>();
    private Handler handler;
    public calendarViewModel(@NonNull Application application){
        super(application);
        saving.setValue(false);
        handler = new Handler();
        database = Room.databaseBuilder(application, appDatabase.class, "courseData").build();
        new Thread(() -> {
            ArrayList<calendarTimeSlot> timeSlots = (ArrayList<calendarTimeSlot>) database.getCalenderDao().getAll();
            handler.post(()->{
                entries.addAll(timeSlots);
            });
        }).start();

    }
    public void createWeeklySchedule(){
        // check if data exists
        new Thread(() -> {
        List<calendarTimeSlot> checkIfDataExists = database.getCalenderDao().getAll();
        if(checkIfDataExists.isEmpty()) {
            String[] daysOfWeek = new String[]{"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY"};

            for (String element : daysOfWeek) {
                for (double j = 6.00; j < 22.00; j += .25) {
                    calendarTimeSlot newTime = new calendarTimeSlot(j, j + .25, element);
                    newTime.id = database.getCalenderDao().insert(newTime);
                    handler.post(()->{
                        entries.add(newTime);
                    });


                }
            }
        }
        }).start();
    }

    public ObservableArrayList<calendarTimeSlot> getEntries() {
        return entries;
    }

    public MutableLiveData<Boolean> getSaving() {
        return saving;
    }
}
