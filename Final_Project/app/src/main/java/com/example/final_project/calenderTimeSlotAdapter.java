package com.example.final_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.RecyclerView;
import com.example.final_project.model.calendarTimeSlot;

import java.util.ArrayList;
import java.util.List;

public class calenderTimeSlotAdapter extends RecyclerView.Adapter<calenderTimeSlotAdapter.ViewHolder> implements Filterable {
    private ObservableArrayList<calendarTimeSlot> entries;
    private ObservableArrayList<calendarTimeSlot> completeEntries;
    private String dayofWeek;


    public calenderTimeSlotAdapter(ObservableArrayList<calendarTimeSlot> entries, String dayofWeek){
        this.completeEntries = entries;
        this.entries = new ObservableArrayList<>();
        entries.addAll(completeEntries);
        this.dayofWeek = dayofWeek;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calender_time_slot, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TextView textView = holder.itemView.findViewById(R.id.startTimeSlot);
        double Number = entries.get(position).startTime;
        int bigNum = (int)Math.floor(Number);
        double deci = Number - bigNum;
        double Min = deci * 60;
        int MinInt = (int)Min;
        String Minute =String.valueOf(MinInt);
        if (MinInt == 0){
            Minute = Minute + "0";
        }
        String time = String.valueOf(bigNum) + ":" + Minute;
        textView.setText(time);
        TextView textView1 = holder.itemView.findViewById(R.id.nameAndNumberSlot);
        textView1.setText(entries.get(position).courseNameInTimeSlot);

    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    public void setNeededEntries(String dayOfWeek) {
        this.dayofWeek = dayOfWeek;
    }

    @Override
    public Filter getFilter() {
        return theFilter;
    }
    private Filter theFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<calendarTimeSlot> fileredList = new ArrayList<>();
            if(constraint == null|| constraint.length() == 0){
                fileredList.addAll(completeEntries);
            }else {
                String filterPattern = constraint.toString();
                for (calendarTimeSlot item : completeEntries){
                    if(item.dayOfWeek.equals(filterPattern)){
                        fileredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = fileredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            entries.clear();
            entries.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public ObservableArrayList<calendarTimeSlot> getEntries() {
        return entries;
    }
}
