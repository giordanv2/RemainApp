package com.example.remain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Reminder> reminderList = new ArrayList<>();
    private ReminderAdapter reminderAdapter;
    private String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerViewReminders = findViewById(R.id.recyclerViewReminders);
        reminderAdapter = new ReminderAdapter(reminderList);
        recyclerViewReminders.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewReminders.setAdapter(reminderAdapter);

        ScrollView scrollView = findViewById(R.id.ScrollView1);

        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String monthString = getMonthString(month);
            selectedDate = dayOfMonth + " " + monthString; // Save selected date
        });

        Button btnNewReminder = findViewById(R.id.btnNewReminder);
        btnNewReminder.setOnClickListener(v -> showNewReminderDialog());
        List<Reminder> mockList = mockReminders();
        for (Reminder mockReminder : mockList) {
            reminderList.add(mockReminder);
        }

        reminderAdapter.notifyDataSetChanged();
        reminderAdapter.notifyItemInserted(reminderList.size() - 1);

        scrollView.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {

            int scrollViewHeight = scrollView.getChildAt(0).getHeight() - scrollView.getHeight();

            float scrollPercentage = (float) scrollY / scrollViewHeight;

            float minAlpha = 0.6f;
            btnNewReminder.setAlpha(Math.max(minAlpha, 1 - scrollPercentage));

            btnNewReminder.setVisibility(View.VISIBLE);
        });
    }

    private String getMonthString(int month) {
        String[] monthNames = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        return monthNames[month];
    }

    private void showNewReminderDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_new_reminder);

        EditText editTextTitle = dialog.findViewById(R.id.editTextTitle);
        EditText editTextDescription = dialog.findViewById(R.id.editTextDescription);
        TimePicker timePicker = dialog.findViewById(R.id.timePicker);
        timePicker.setIs24HourView(false);

        Button buttonSave = dialog.findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(v -> {
            String title = editTextTitle.getText().toString();
            String description = editTextDescription.getText().toString();
            int hour = timePicker.getCurrentHour();
            int minute = timePicker.getCurrentMinute();

            String amPm = hour >= 12 ? "PM" : "AM";
            hour = hour % 12;
            if (hour == 0) hour = 12;
            String time = String.format("%d:%02d %s", hour, minute, amPm);

            if (selectedDate == null) {
                Calendar today = Calendar.getInstance();
                int month = today.get(Calendar.MONTH);
                int day = today.get(Calendar.DAY_OF_MONTH);
                selectedDate = day + " " + getMonthString(month);
            }

            if (title.isEmpty() || description.isEmpty()) {
                Toast toast = Toast.makeText(getApplicationContext(), "Title and Description must be completed", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }

            Reminder newReminder = new Reminder(title, description, selectedDate, time);
            reminderList.add(newReminder);
            reminderAdapter.notifyItemInserted(reminderList.size() - 1);

            dialog.dismiss();
        });

        Button buttonCancel = dialog.findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private boolean isScrollViewAtBottom(ScrollView scrollView) {
        View view = (View) scrollView.getChildAt(scrollView.getChildCount() - 1);
        int diff = (view.getBottom() - (scrollView.getHeight() + scrollView.getScrollY()));
        return diff <= 0;
    }


    private List<Reminder> mockReminders() {
        List<Reminder> mockList = new ArrayList<>();
        mockList.add(new Reminder("Buy groceries", "Pick up milk and bread", "16 Nov", "5:30 PM"));
        mockList.add(new Reminder("Fix computer", "Install latest updates", "17 Nov", "6:00 PM"));
        mockList.add(new Reminder("Trip to California", "Pack luggage for the trip", "18 Nov", "7:15 PM"));
        mockList.add(new Reminder("Dentist Appointment", "Annual dental check-up", "19 Nov", "10:00 AM"));
        mockList.add(new Reminder("Gym Session", "Leg day workout", "20 Nov", "8:00 AM"));
        mockList.add(new Reminder("Read Book", "Finish reading 'The Alchemist'", "21 Nov", "9:00 PM"));
        mockList.add(new Reminder("Family Dinner", "Dinner at Grandma's house", "22 Nov", "7:00 PM"));
        mockList.add(new Reminder("Coffee with Sam", "Meet at Central Perk", "23 Nov", "3:30 PM"));

        return mockList;
    }

}
