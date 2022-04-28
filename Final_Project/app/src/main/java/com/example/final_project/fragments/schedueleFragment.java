package com.example.final_project.fragments;

import com.example.final_project.calenderTimeSlotAdapter;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_project.R;
import com.example.final_project.model.calendarTimeSlot;
import com.example.final_project.viewmodels.calendarViewModel;

public class schedueleFragment extends Fragment {
    public schedueleFragment(){
        super(R.layout.fragment_scheduele);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        calendarViewModel viewModel = new ViewModelProvider(requireActivity()).get(calendarViewModel.class);
        ObservableArrayList<calendarTimeSlot> entries =  viewModel.getEntries();
        RadioGroup whichDay = view.findViewById(R.id.dayOfTheWeek);
        int RadioId = whichDay.getCheckedRadioButtonId();
        String dayOfWeek = "";

        if(RadioId == -1){
            RadioButton checkIfNone = view.findViewById(R.id.MONDAY);
//            checkIfNone.toggle();
            RadioId = whichDay.getCheckedRadioButtonId();
            dayOfWeek = whatDay(RadioId);

        }
        calenderTimeSlotAdapter adapter = new calenderTimeSlotAdapter(entries, dayOfWeek);
//        adapter.getFilter().filter(dayOfWeek);
        whichDay.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String day = whatDay(checkedId);
                adapter.getFilter().filter(day);
            }

        });
        entries.addOnListChangedCallback(new ObservableList.OnListChangedCallback() {
            @Override
            public void onChanged(ObservableList sender) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount) {
                adapter.notifyItemRangeChanged(positionStart, itemCount);
            }

            @Override
            public void onItemRangeInserted(ObservableList sender, int positionStart, int itemCount) {
                adapter.notifyItemRangeInserted(positionStart, itemCount);
            }

            @Override
            public void onItemRangeMoved(ObservableList sender, int fromPosition, int toPosition, int itemCount) {
                adapter.notifyItemMoved(fromPosition, toPosition);
            }

            @Override
            public void onItemRangeRemoved(ObservableList sender, int positionStart, int itemCount) {
                adapter.notifyItemRangeRemoved(positionStart, itemCount);
            }
        });
        RecyclerView recyclerView = view.findViewById(R.id.calendar);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
    public String whatDay(int id){
        String dayOfWeek = "";
        if(id == R.id.MONDAY){
            dayOfWeek = "MONDAY";
        }else if(id == R.id.TUESDAY){
            dayOfWeek = "TUESDAY";
        }else if(id == R.id.WEDNESDAY){
            dayOfWeek = "WEDNESDAY";
        }else if(id == R.id.THURSDAY){
            dayOfWeek = "THURSDAY";
        }else{
            dayOfWeek = "FRIDAY";
        }
        return dayOfWeek;
    }
}
