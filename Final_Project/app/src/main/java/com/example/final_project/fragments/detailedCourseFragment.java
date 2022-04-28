package com.example.final_project.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.final_project.R;
import com.example.final_project.viewmodels.CourseViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class detailedCourseFragment extends Fragment {

    public detailedCourseFragment(){
        super(R.layout.fragment_full_course_view);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CourseViewModel viewModel = new ViewModelProvider(getActivity()).get(CourseViewModel.class);
        viewModel.getCurrentEntry().observe(getViewLifecycleOwner(), (entry) -> {
            if (entry == null) {
                getActivity().getSupportFragmentManager().popBackStack();
            }else{
            TextView courseTitle = view.findViewById(R.id.fullCourseTitle);
            courseTitle.setText(entry.name);
            TextView creditHour = view.findViewById(R.id.fullcreidtH);
            creditHour.setText(String.valueOf(entry.creditHours));
            TextView startTime = view.findViewById(R.id.fullStartTime);
            String sTime = convertTime(entry.startTime);
            startTime.setText(sTime);
            TextView endTime = view.findViewById(R.id.fullEndTime);
            String eTime = convertTime(entry.endTime);
            endTime.setText(eTime);
            TextView meetingPlace = view.findViewById(R.id.fullroomBuild);
            meetingPlace.setText(entry.meetingPlace);
            CheckBox isMon = view.findViewById(R.id.fullisMon);
            boolean isMonday = entry.Monday;
            if (isMonday) {
                isMon.toggle();
            }
            isMon.setEnabled(false);
            CheckBox isTue = view.findViewById(R.id.fullisTues);
            boolean isTuesday = entry.Tuesday;
            if (isTuesday) {
                isTue.toggle();
            }
            isTue.setEnabled(false);
            CheckBox isWed = view.findViewById(R.id.fullisWed);
            boolean isWednesday = entry.Wednesday;
            if (isWednesday) {
                isWed.toggle();
            }
            isWed.setEnabled(false);
            CheckBox isThur = view.findViewById(R.id.fullisThur);
            boolean isThursday = entry.Thursday;
            if (isThursday) {
                isThur.toggle();
            }
            isThur.setEnabled(false);
            CheckBox isFri = view.findViewById(R.id.fullisFri);
            boolean isFriday = entry.Friday;
            if (isFriday) {
                isFri.toggle();
            }
            isFri.setEnabled(false);
        }
        });
        FloatingActionButton fab = view.findViewById(R.id.trashButton);
        fab.setOnClickListener(view1 -> {
            viewModel.deleteCourse();
        });
    }
    public String convertTime(double Number){
        int bigNum = (int)Math.floor(Number);
        double deci = Number - bigNum;
        double Min = deci * 60;
        int MinInt = (int)Min;
        String Minute =String.valueOf(MinInt);
        if (MinInt == 0){
            Minute = Minute + "0";
        }
        return String.valueOf(bigNum) + ":" + Minute;
    }
}
