package com.example.final_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import com.example.final_project.model.courseInfo;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.RecyclerView;

public class courseInfoAdapter extends RecyclerView.Adapter<courseInfoAdapter.ViewHolder> {
    private ObservableArrayList<courseInfo> entries;
    onCourseClicked listener;

    public interface onCourseClicked{
        public void onClick(courseInfo info);
    }

    public courseInfoAdapter(ObservableArrayList<courseInfo> entries, onCourseClicked listener){
        this.entries = entries;
        this.listener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_info, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TextView name = holder.itemView.findViewById(R.id.CourseTitle);
        name.setText(entries.get(position).name);
        TextView room = holder.itemView.findViewById(R.id.roomBuild);
        room.setText(entries.get(position).meetingPlace);
        TextView cH = holder.itemView.findViewById(R.id.fullcreidtH);
        cH.setText(String.valueOf(entries.get(position).creditHours));
        CheckBox isMon = holder.itemView.findViewById(R.id.isMon);
        boolean isMonday = entries.get(position).Monday;
        if (isMonday){
            isMon.toggle();
        }
        isMon.setEnabled(false);
        CheckBox isTue = holder.itemView.findViewById(R.id.isTues);
        boolean isTuesday = entries.get(position).Tuesday;
        if (isTuesday){
            isTue.toggle();
        }
        isTue.setEnabled(false);
        CheckBox isWed = holder.itemView.findViewById(R.id.isWed);
        boolean isWednesday = entries.get(position).Wednesday;
        if (isWednesday){
            isWed.toggle();
        }
        isWed.setEnabled(false);
        CheckBox isThur = holder.itemView.findViewById(R.id.isThur);
        boolean isThursday = entries.get(position).Thursday;
        if (isThursday){
            isThur.toggle();
        }
        isThur.setEnabled(false);
        CheckBox isFri = holder.itemView.findViewById(R.id.isFri);
        boolean isFriday = entries.get(position).Friday;
        if (isFriday){
            isFri.toggle();
        }
        isFri.setEnabled(false);
        holder.itemView.setOnClickListener(view ->{
            listener.onClick(entries.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
