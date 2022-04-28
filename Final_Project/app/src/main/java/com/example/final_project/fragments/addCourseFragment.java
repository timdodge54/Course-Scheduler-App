package com.example.final_project.fragments;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.final_project.R;
import com.example.final_project.viewmodels.CourseViewModel;
import com.example.final_project.viewmodels.calendarViewModel;

import java.util.Locale;


public class addCourseFragment extends Fragment {
    int startHour, startMin, endHour, endMin;
    Button startTime, endTime;
    private boolean previousSavingState = false;


    public addCourseFragment(){
        super(R.layout.fragment_add_course);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CourseViewModel viewModel = new ViewModelProvider(getActivity()).get(CourseViewModel.class);
        calendarViewModel viewModel0 = new ViewModelProvider(getActivity()).get(calendarViewModel.class);
        viewModel.setCalendarEntries(viewModel0.getEntries());
        viewModel.getSaving().observe(getViewLifecycleOwner(), (saving) ->{
            if (saving && !previousSavingState) {
               Button button =  view.findViewById(R.id.save);
               button.setEnabled(false);
               button.setText("Saving..");
               previousSavingState = saving;
            }else if (previousSavingState && !saving && !viewModel.isError()){
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragment, schedueleFragment.class, null)
                        .commit();
            }else{
                Button button =  view.findViewById(R.id.save);
                button.setEnabled(true);
                button.setText("Save");
                previousSavingState = saving;
            }
        });
        view.findViewById(R.id.save).setOnClickListener(save -> {
            boolean[] daysOfWeek = new boolean[5];
            CheckBox Mon = view.findViewById(R.id.checkMonday);
            daysOfWeek[0] = Mon.isChecked();
            CheckBox Tues = view.findViewById(R.id.checkTues);
            daysOfWeek[1] = Tues.isChecked();
            CheckBox Wed = view.findViewById(R.id.checkWed);
            daysOfWeek[2] = Wed.isChecked();
            CheckBox Thur = view.findViewById(R.id.checkThurs);
            daysOfWeek[3] = Thur.isChecked();
            CheckBox Fri = view.findViewById(R.id.checkFri);
            daysOfWeek[4] = Fri.isChecked();
            EditText nameAndNumber = view.findViewById(R.id.nameAndNumber);
            String namNum = nameAndNumber.getText().toString();
            EditText creditHour = view.findViewById(R.id.creditHour);
            String cH = creditHour.getText().toString();
            EditText roomBuilding = view.findViewById(R.id.roomBuilding);
            String Rb = roomBuilding.getText().toString();
            boolean isValidTime = viewModel.saveCourseInfo(daysOfWeek, namNum, cH, Rb, startHour, startMin, endHour, endMin);
            if (!isValidTime){
                showError(view);
            }


        });

        startTime = view.findViewById(R.id.startTime);
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        startHour = selectedHour;
                        startMin = selectedMinute;
                        startTime.setText(String.format(Locale.getDefault(), "%02d:%02d", startHour, startMin));
                    }
                };
                TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(), onTimeSetListener, startHour, startMin, true);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });

        endTime = view.findViewById(R.id.endTime);
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        endHour = selectedHour;
                        endMin = selectedMinute;
                        endTime.setText(String.format(Locale.getDefault(), "%02d:%02d", endHour, endMin));
                    }
                };
                TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(), onTimeSetListener, endHour, endMin, true);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();

            }
        });
    }
    public void showError(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        View myView = getLayoutInflater().inflate(R.layout.alert_dialog, null);
        builder.setView(myView);
        TextView errorD = myView.findViewById(R.id.alertText);
        errorD.setText(R.string.error_message);
        AlertDialog dialog = builder.create();
        dialog.show();
        myView.findViewById(R.id.dismissButton).setOnClickListener(view1 -> {
            dialog.cancel();
        });


    }
    public void showSuccess(View view){
        TextView errorD = view.findViewById(R.id.alertText);
        errorD.setText(R.string.success_message);
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        View myView = getLayoutInflater().inflate(R.layout.alert_dialog, null);
        builder.setView(myView);
        AlertDialog dialog = builder.create();
        dialog.show();
        view.findViewById(R.id.dismissButton).setOnClickListener(view1 -> {
            dialog.cancel();
        });
    }

}




