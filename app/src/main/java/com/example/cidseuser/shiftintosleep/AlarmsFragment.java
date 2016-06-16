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
    String time1 = "2016-14-06 22:00:00";
    String time2 = "2016-15-06 07:30:00";
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
        Date d1 = null,d2 = null;

        try {
            d1 = format.parse(time1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {

            d2 = format.parse(time2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long difference = d2.getTime() - d1.getTime();
        long totalSleep =  TimeUnit.MILLISECONDS.toMinutes(difference);

        Log.e("Total Sleep : ",""+totalSleep);
        Toast.makeText(getActivity(),"Total Sleep : " + totalSleep,Toast.LENGTH_LONG).show();


        final int timetowake = (int) (totalSleep/90);
        tvWhentoWake.setText("You Should wake at "+timetowake);

        btnSetAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlarm(timetowake);
            }
        });
        return view;
    }

    public void setAlarm(int timetowake){

        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY,timetowake);
        calendar.set(Calendar.MINUTE,0);
        Intent myIntent = new Intent(getActivity(), AlarmReceiver.class);

        pendingIntent = PendingIntent.getBroadcast(getActivity(),0,myIntent,0);
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);


        Toast.makeText(getActivity(),"The alarm will wake you at :"+timetowake,Toast.LENGTH_LONG).show();



    }

}