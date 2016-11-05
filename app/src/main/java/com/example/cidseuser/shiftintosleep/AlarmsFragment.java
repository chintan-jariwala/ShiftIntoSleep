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
    String date1 = "2016-14-06 22:00:00";
    String date2 = "2016-15-06 07:30:00";
    int wake_hour = 6;
    int wake_minute = 30;
    int arrive_hour = 22;
    int arrive_minute = 22;
    Button btnSetAlarm;
    TextView tvWhentoWake;
    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    Calendar calendar = Calendar.getInstance();
    private static AlarmsFragment inst;



    public static AlarmsFragment instance() {
        return inst;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inst = this;

        View view = inflater.inflate(R.layout.alarms, container, false);
        alarmManager = (AlarmManager) getActivity().getSystemService(getActivity().ALARM_SERVICE);

        tvWhentoWake = (TextView) view.findViewById(R.id.tvWhenToWake);
        btnSetAlarm = (Button) view.findViewById(R.id.btnSetAlarm);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-dd-mm HH:mm:ss");
        Date d1 = null, d2 = null;

        long hour_difference = (wake_hour * 60 + wake_minute) - (arrive_hour * 60 - arrive_minute);
        long totalSleep = (int)(hour_difference/90) ;


        Log.e("Total Sleep : ",""+totalSleep);
        Toast.makeText(getActivity(),"Total Sleep : " + totalSleep,Toast.LENGTH_LONG).show();

        final int timetosleep_hour = (int) ((wake_hour * 60 + wake_minute) - (totalSleep * 90) / 60) ;
        final int timetosleep_minute = (int) ((wake_hour * 60 + wake_minute) - (totalSleep * 90) % 60);
        tvWhentoWake.setText("You should sleep at "+timetosleep_hour + ":" + timetosleep_minute);

        final int timetowake_hour = wake_hour;
        final int timetowake_minute = wake_minute;
        tvWhentoWake.setText("You should wake at "+timetowake_hour + ":" + timetowake_minute);

        //fix this!!!!!!!!!!!!

        btnSetAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlarm(timetowake_hour, timetowake_minute);
            }
        });
        btnSetAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlarm(timetosleep_hour, timetosleep_minute);
            }
        });
        return view;
    }

    public void setAlarm(int timetowake, int timetowake_minute){



        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY,timetowake);
        calendar.set(Calendar.MINUTE,timetowake_minute);
        Intent myIntent = new Intent(getActivity(), AlarmReceiver.class);

        pendingIntent = PendingIntent.getBroadcast(getActivity(),0,myIntent,0);
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);


        Toast.makeText(getActivity(),"The alarm will wake you at :"+timetowake + ":" + timetowake_minute,Toast.LENGTH_LONG).show();



    }

}