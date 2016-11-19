package com.example.cidseuser.shiftintosleep;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;

/**
 * Created by cidseuser on 6/10/2016.
 */
public class AlarmsFragment extends Fragment {
    int wake_hour = 0;
    int wake_minute = 0;
    int arrive_hour = 0;
    int arrive_minute = 0;
    Button btnSetAlarm;
    TextView tvWhentoWake;
    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    Calendar calendar = Calendar.getInstance();
    private static AlarmsFragment inst;
    ArrayList<UserSchedule> listOfSchedule;



    public static AlarmsFragment instance() {
        return inst;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DatabaseOperations db = new DatabaseOperations(this.getContext());
        listOfSchedule = db.getAllSchedule();

        Date currentDate = new Date();

        Calendar calendar = Calendar.getInstance();
        System.out.println("Current  - " +  calendar.get(Calendar.DAY_OF_MONTH) + " " +  calendar.get(Calendar.MONTH) + " " +  calendar.get(Calendar.YEAR) );

        int currentDay =  calendar.get(Calendar.DAY_OF_MONTH);
        int currentMonth =  calendar.get(Calendar.MONTH) + 1;
        int currentYear =  calendar.get(Calendar.YEAR);
        for (UserSchedule  sch: listOfSchedule)
        {
            System.out.println(arrive_hour);
            if (arrive_hour > 12)
            {
                if (sch.getDay() == currentDay - 1 &&
                        sch.getMonthOfYear() == currentMonth &&
                        sch.getYear() == currentYear  ) {
                    arrive_hour = sch.getEndHour();
                    arrive_minute = sch.getEndMinute();

                    System.out.println("Found previous date " + calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.MONTH) + " " +  calendar.get(Calendar.YEAR));
                }
                else
                {
                    System.out.println("Not previous date " + calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.MONTH) + " " +  calendar.get(Calendar.YEAR) );
                }
            }
            else {
                if (sch.getDay() == currentDay &&
                        sch.getMonthOfYear() == currentMonth &&
                        sch.getYear() == currentYear) {
                    arrive_hour = sch.getEndHour();
                    arrive_minute = sch.getEndMinute();

                    System.out.println("Found previous date " + calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.MONTH) + " " + calendar.get(Calendar.YEAR));
                } else {
                    System.out.println("Not previous date " + calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.MONTH) + " " + calendar.get(Calendar.YEAR));
                }
                if (sch.getDay() == currentDay &&
                        sch.getMonthOfYear() == currentMonth &&
                        sch.getYear() == currentYear) {
                    wake_hour = sch.getStartHour();
                    wake_minute = sch.getStartMinute();

                    System.out.println("Found current date " + calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.MONTH) + " " + calendar.get(Calendar.YEAR));
                } else {
                    System.out.println("Not current date " + calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.MONTH) + " " + calendar.get(Calendar.YEAR));
                }
            }

        }


        inst = this;

        View view = inflater.inflate(R.layout.alarms, container, false);
        alarmManager = (AlarmManager) getActivity().getSystemService(getActivity().ALARM_SERVICE);

        tvWhentoWake = (TextView) view.findViewById(R.id.tvWhenToWake);
        btnSetAlarm = (Button) view.findViewById(R.id.btnSetAlarm);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-dd-mm HH:mm:ss");
        System.out.println("WAKE:"+wake_hour + " " + wake_minute);
        System.out.println("ARRIVE:" + arrive_hour + " " + arrive_minute);

        long hour_difference = 0;
        if (arrive_hour > 12)
            hour_difference = (wake_hour * 60 + wake_minute) + (24*60 - (arrive_hour * 60 + arrive_minute));
        else
            hour_difference = (wake_hour * 60 + wake_minute) - (arrive_hour * 60 + arrive_minute);
        System.out.println(hour_difference);
        int totalSleep = (int)(hour_difference/90) ;
        if (totalSleep > 6)
            totalSleep = 6;
        if (totalSleep < 4)
            totalSleep = 4;
        System.out.println(totalSleep);

        double timetosleep_hour = ((wake_hour * 60 + wake_minute) - (totalSleep * 90) )/ 60.0;
            if (timetosleep_hour < 0)
            {
                timetosleep_hour = 24 - Math.abs(timetosleep_hour);
            }
        final int finaltimetosleep_hour = (int)(timetosleep_hour);
        int timetosleep_minute = ((wake_hour * 60 + wake_minute) - (totalSleep * 90)) % 60;
            if (timetosleep_minute < 0)
            {
                timetosleep_minute = 60 - Math.abs(timetosleep_minute);
            }
        final int finaltimetosleep_minute = timetosleep_minute;
        String smin = finaltimetosleep_minute + "";
        if (timetosleep_minute == 0)
            smin = "00";
        if (timetosleep_minute < 10)
            smin = "0" + timetosleep_minute;
        final String finalsmin = smin;

        final int timetowake_hour = wake_hour;
        final int timetowake_minute = wake_minute;
        String wmin = timetowake_minute + "";
        if (timetowake_minute == 0)
            wmin = "00";
        if (timetowake_minute < 10)
            wmin = "0" + timetowake_minute;
        final String finalwmin = wmin;
        tvWhentoWake.setText("You should sleep at "+finaltimetosleep_hour + ":" + finalsmin + " You should wake at "+timetowake_hour + ":" + finalwmin);

        System.out.println(finaltimetosleep_hour + "      " + finaltimetosleep_minute);
        setAlarm(timetowake_hour, timetowake_minute);
        setAlarm((int) finaltimetosleep_hour, (int) finaltimetosleep_minute);
        Toast.makeText(getActivity(),"The alarm will wake you at "+timetowake_hour + ":" + finalwmin,Toast.LENGTH_LONG).show();
        Toast.makeText(getActivity(),"The alarm will remind you to sleep at "+finaltimetosleep_hour + ":" + finalsmin,Toast.LENGTH_LONG).show();

//        btnSetAlarm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               }
//        });




        return view;
    }

    public void setAlarm(int timetowake, int timetowake_minute)
    {


        Calendar calendar = Calendar.getInstance();


        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY,timetowake);
        calendar.set(Calendar.MINUTE,timetowake_minute);
        Intent myIntent = new Intent(getActivity(), AlarmReceiver.class);

        pendingIntent = PendingIntent.getBroadcast(getActivity(),0,myIntent,0);
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);




    }

}