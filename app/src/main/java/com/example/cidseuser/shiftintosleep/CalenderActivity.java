package com.example.cidseuser.shiftintosleep;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by cidseuser on 6/9/2016.
 */
public class CalenderActivity extends Activity {

    private UserSchedule userSchedule;
    private ArrayList<UserSchedule> listOfSchedule;
    private ListView listView;
    private ScheduleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the user interface layout for the front page
        setContentView(R.layout.calender_activity);
        listView = (ListView)findViewById(R.id.listView);
        listOfSchedule = new ArrayList<UserSchedule>();


        adapter = new ScheduleAdapter(this, listOfSchedule);
        listView.setAdapter(adapter);
    }

    public void ChooseDay(View view) {


        final Calendar myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                if (userSchedule == null) {
                    userSchedule = new UserSchedule();
                }
                userSchedule.setYear(year);
                userSchedule.setMonthOfYear(monthOfYear+1);
                userSchedule.setDay(dayOfMonth);

            }

        };

        new DatePickerDialog(this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void showStartTimePickerDialog(View view) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(CalenderActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                if (userSchedule == null) {
                    userSchedule = new UserSchedule();
                }
                userSchedule.setStartHour(selectedHour);
                userSchedule.setStartMinute(selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }

    public void showEndTimePickerDialog(View view) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(CalenderActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                if (userSchedule == null) {
                    userSchedule = new UserSchedule();
                }
                userSchedule.setEndHour(selectedHour);
                userSchedule.setEndMinute(selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    public void AddtoList (View view) {
        if (userSchedule == null) {
            Toast.makeText(this,"Please enter all values.", Toast.LENGTH_SHORT).show();
        } else {

            if (userSchedule.getDay() == -1 ||
                    userSchedule.getMonthOfYear() == -1||
                    userSchedule.getYear() == -1 ||
                    userSchedule.getStartHour() == -1 ||
                    userSchedule.getStartMinute() == -1 ||
                    userSchedule.getEndHour() == -1 ||
                    userSchedule.getEndMinute() == -1) {
                Toast.makeText(this,"Please enter all values.", Toast.LENGTH_SHORT).show();
            } else {
                DatabaseOperations db = new DatabaseOperations(this);

                String date = userSchedule.getMonthOfYear() +"/" + userSchedule.getDay()+"/"+ userSchedule.getYear();
                String wakeTime =  userSchedule.getStartHour() +":" +  userSchedule.getStartMinute();
                String arriveTime =  userSchedule.getEndHour() +":" +  userSchedule.getEndMinute();
                db.putUserSchedule("",date,wakeTime,arriveTime );
                listOfSchedule.add(userSchedule);
                listView.invalidateViews();
                userSchedule = null;
            }
        }
    }
//
//    private void populateListView() {
//        // Create list of items
//        String[] myItems = {"Lullaby", "waterfall", "rainforest", "oceans", "Piano", "Violin"};
//        // Build Adapter
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                this,               // Context For The Activity
//                R.layout.da_items,  // layout to use(create)
//                myItems);           // Items to be displayed
//        // Configure the list view.
//        ListView list = (ListView) findViewById(R.id.listView);
//        list.setAdapter(adapter); // Array of options --} ArrayAdapter --} ListView
//// List view: {views: da_items.xml}
//

    public class ScheduleAdapter extends ArrayAdapter<UserSchedule> {

        List<UserSchedule> listOfSchedule;

        public ScheduleAdapter(Context context, ArrayList<UserSchedule> objects) {
            super(context, 0, objects);
            listOfSchedule = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // Get the data item for this position
            UserSchedule schedule = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_cell, parent, false);
            }
            // Lookup view for data population
            TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
            tvName.setText(schedule.getMonthOfYear() + "/" + schedule.getDay() + "/" + schedule.getYear() + " Start Time:" + schedule.getStartHour() + ":" + schedule.getStartMinute() + " End Time:" + schedule.getEndHour() + ":" + schedule.getEndMinute());
            return convertView;
        }
    }
}
