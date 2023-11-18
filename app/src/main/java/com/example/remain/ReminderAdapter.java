package com.example.remain;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ViewHolder> {

    private List<Reminder> reminderList;

    public ReminderAdapter(List<Reminder> reminderList) {
        this.reminderList = reminderList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reminder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Reminder reminder = reminderList.get(position);
        holder.tvReminderTitle.setText(reminder.getTitle());
        holder.tvReminderDescription.setText(reminder.getDescription());
        // Format and set date and time
        holder.tvReminderDate.setText(reminder.getDate());
        holder.tvReminderTime.setText(reminder.getTime());
    }

    @Override
    public int getItemCount() {
        return reminderList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvReminderTitle, tvReminderDescription, tvReminderDate, tvReminderTime;

        ViewHolder(View itemView) {
            super(itemView);
            tvReminderTitle = itemView.findViewById(R.id.tvReminderTitle);
            tvReminderDescription = itemView.findViewById(R.id.tvReminderDescription);
            tvReminderDate = itemView.findViewById(R.id.tvReminderDate);
            tvReminderTime = itemView.findViewById(R.id.tvReminderTime);
        }
    }
}
