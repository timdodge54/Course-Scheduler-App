package com.example.final_project.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_project.R;
import com.example.final_project.courseInfoAdapter;
import com.example.final_project.model.courseInfo;
import com.example.final_project.viewmodels.CourseViewModel;
import com.example.final_project.viewmodels.calendarViewModel;

public class viewCoursesFragment extends Fragment {
    public viewCoursesFragment(){super(R.layout.fragment_course_view);}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CourseViewModel viewModel = new ViewModelProvider(getActivity()).get(CourseViewModel.class);
        calendarViewModel viewModel0 = new ViewModelProvider(getActivity()).get(calendarViewModel.class);
        viewModel.setCalendarEntries(viewModel0.getEntries());
        ObservableArrayList<courseInfo> entries =  viewModel.getEntries();
        courseInfoAdapter adapter = new courseInfoAdapter(entries,
                entry ->{
                        viewModel.setCurrentEntry(entry);
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment, detailedCourseFragment.class, null )
                                .setReorderingAllowed(true)
                                .addToBackStack(null)
                                .commit();
                }
                );
        entries.addOnListChangedCallback(new ObservableList.OnListChangedCallback() {
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
        RecyclerView recyclerView = view.findViewById(R.id.secondRV);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
