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
import com.example.final_project.model.courseInfo;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class CourseViewModel extends AndroidViewModel {
    private appDatabase database;
    private MutableLiveData<Boolean> saving = new MutableLiveData<>();
    private ObservableArrayList<courseInfo> entries = new ObservableArrayList<>();
    private ObservableArrayList<calendarTimeSlot> calendarEntries;
    private MutableLiveData<courseInfo> currentEntry = new MutableLiveData<>();
    private boolean isError;
    private Handler handler;
    public CourseViewModel(@NonNull Application application) {
        super(application);
        saving.setValue(false);
        handler = new Handler();
        database = Room.databaseBuilder(application, appDatabase.class, "courseData").build();
        new Thread(() -> {
            ArrayList<courseInfo> courses = (ArrayList<courseInfo>) database.getCourseInfoDao().getAll();
            ArrayList<calendarTimeSlot> list =  (ArrayList<calendarTimeSlot>) database.getCalenderDao().getAll();
            handler.post(() ->{
                    entries.addAll(courses);
            });

        }).start();
    }

    public boolean saveCourseInfo(boolean[] daysOfWeek, String nameAndNumber, String creditHours, String roomBuilding, int startHour, int startMin, int endHour, int endMin){
        isError = false;
        saving.setValue(true);
        double startMinPercent = (startMin / 60.0);
        double startTime = startHour + startMinPercent;
        startTime = roundDown(startTime);
        double endMinPercent = (endMin / 60.0);
        double endTime = endHour + endMinPercent;
        endTime = roundUp(endTime);
        int counter = 0;
        for(boolean element: daysOfWeek){
            if(!element){
                counter++;
            }
        }
        boolean start = startTime < 6.0;
        boolean end = endTime > 22.0;
        boolean room = roomBuilding.equals("Building and Room #");
        boolean name = nameAndNumber.equals("Course Name and Number");
        boolean checkInteger = !isInteger(creditHours);
        boolean checkDayOfWeekIsChosen = counter == daysOfWeek.length;
        boolean startNotLate = startTime > endTime;
        if(start|| end||name||room|| checkInteger||checkDayOfWeekIsChosen||startNotLate){
            isError = true;
            saving.setValue(false);
            return false;
        }
        double finalStartTime = startTime;
        double finalEndTime = endTime;
        AtomicBoolean finalIsFilled = new AtomicBoolean(false);
        int credits = Integer.parseInt(creditHours);
        double finalStartTime1 = startTime;
        double finalEndTime1 = endTime;
        new Thread(() -> {
            String[] dayOfWeek = new String[]{"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY"};
            ArrayList<Long> idsOfTimeSlots = new ArrayList<Long>();
            for (int i = 0; i < daysOfWeek.length; i++) {
                if(daysOfWeek[i]) {
                    for (double j = finalStartTime; j < finalEndTime; j += .25) {
                        // check each time slot on the days of the week in question
                            // if any slots are filled return false
                        calendarTimeSlot currentSlot = database.getCalenderDao().findByTimeandDay(j, dayOfWeek[i]);
                        if (currentSlot.filled){
                            finalIsFilled.set(true);
                        }else{
                            idsOfTimeSlots.add(currentSlot.id);
                        }
                    }
                }
            }
        // if never returned iterate through idsOfTimeSlots and update with filled and name of course
           if(!finalIsFilled.get()){
               for(Long id: idsOfTimeSlots){
                   calendarTimeSlot updatedTimeSlot = checkEntriesById(id);
//                   calendarTimeSlot updatedTimeSlot =  database.getCalenderDao().findById(id);
                   updatedTimeSlot.filled = true;
                   updatedTimeSlot.courseNameInTimeSlot = nameAndNumber;
                    database.getCalenderDao().update(updatedTimeSlot);
                    int index = calendarEntries.indexOf(updatedTimeSlot);
                    handler.post(()->{
                        calendarEntries.set(index, updatedTimeSlot);
                   });

               }
               courseInfo newCourse = new courseInfo(finalStartTime1, finalEndTime1, nameAndNumber,roomBuilding, credits, daysOfWeek[0], daysOfWeek[1], daysOfWeek[2], daysOfWeek[3], daysOfWeek[4]);
               newCourse.id = database.getCourseInfoDao().insert(newCourse);
               handler.post(()->{
                   entries.add(newCourse);
               });
           }
        }).start();
        saving.setValue(false);
        if(finalIsFilled.get()){
            isError = true;
            return false;
        }
        return true;
    }

    public void removeCourse(){

    }

    public double roundUp(double time){
        double intermediate = Math.ceil((time*100)/25)*25/100;
        return intermediate;
    }
    public double roundDown(double time){
        double intermediate = Math.floor((time*100)/25)*25/100;
        return intermediate;
    }

    public MutableLiveData<Boolean> getSaving() {
        return saving;
    }
    public boolean isInteger( String input ) {
        try {
            Integer.parseInt( input );
            return true;
        }
        catch( Exception e ) {
            return false;
        }
    }

    public void setCalendarEntries(ObservableArrayList<calendarTimeSlot> calendarEntries) {
        this.calendarEntries = calendarEntries;
    }
    public calendarTimeSlot checkEntriesById(long id){
        for (calendarTimeSlot element: calendarEntries){
            if (element.getId() == id){
                return element;
            }
        }
        return null;
    }
    public void deleteCourse(){
        new Thread(()->{
            courseInfo toBeDeleted = currentEntry.getValue();
            entries.remove(currentEntry.getValue());
            double startTime = toBeDeleted.startTime;
            double endTime = toBeDeleted.endTime;
            String[] dayOfWeek = new String[]{"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY"};
            boolean[] daysOfWeek = new boolean[]{toBeDeleted.Monday, toBeDeleted.Tuesday, toBeDeleted.Wednesday, toBeDeleted.Thursday, toBeDeleted.Friday};
            for(int i = 0; i < daysOfWeek.length; i ++) {
                if(daysOfWeek[i]) {
                    for (double j = startTime; j < endTime; j += .25) {
                        calendarTimeSlot secondToBeDeleted = database.getCalenderDao().findByTimeandDay(j, dayOfWeek[i]);
                        calendarTimeSlot thisIsTheRealOne = checkEntriesById(secondToBeDeleted.id);
                        calendarEntries.remove(thisIsTheRealOne);
                        database.getCalenderDao().delete(secondToBeDeleted);
                        currentEntry.postValue(null);
                    }
                }
            }

        }).start();
    }

    public MutableLiveData<courseInfo> getCurrentEntry() {
        return currentEntry;
    }

    public void setCurrentEntry(courseInfo currentEntry) {
        this.currentEntry.setValue(currentEntry);
    }

    public ObservableArrayList<courseInfo> getEntries() {
        return entries;
    }


    public boolean isError(){
        return isError;
    }
}
