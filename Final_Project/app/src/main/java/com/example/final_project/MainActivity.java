package com.example.final_project;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.example.final_project.fragments.addCourseFragment;
import com.example.final_project.fragments.viewCoursesFragment;
import com.example.final_project.fragments.schedueleFragment;
import com.example.final_project.viewmodels.calendarViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        DrawerLayout drawerLayout = findViewById(R.id.Drawer_layout);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment, schedueleFragment.class, null)
                    .commit();
        }

        FloatingActionButton fab = findViewById(R.id.addCourseButton);
        calendarViewModel viewModel = new ViewModelProvider(this).get(calendarViewModel.class);
        viewModel.createWeeklySchedule();
        navigationView.setNavigationItemSelectedListener(menuItem -> {
                    menuItem.setChecked(true);
                    if (menuItem.getItemId() == R.id.Sched_item) {
                        getSupportFragmentManager().beginTransaction()
                                .setReorderingAllowed(true)
                                .replace(R.id.fragment, schedueleFragment.class, null)
                                .commit();
                    } else  {
                        getSupportFragmentManager().beginTransaction()
                                .setReorderingAllowed(true)
                                .replace(R.id.fragment, viewCoursesFragment.class, null)
                                .commit();
                    }
                    return true;
                });


        toolbar.setNavigationOnClickListener(view -> {
            drawerLayout.open();
        });
    }
    public void AddButton(View view){
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment, addCourseFragment.class, null)
                .commit();
    }
    public void removeCurrent(View view){
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment, schedueleFragment.class, null)
                .commit();
    }
}