package com.example.multi_timer2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;

public class MainActivity extends AppCompatActivity {

    private long totalTime = 0;
    


    private TextView countdowntxt; //output
    private TextView tvtimer1; //output
    private Button addbtn;
    private Button startbtn;
    private Button resetbtn;
    private Button settimebtn;

    private CountDownTimer countDownTimer;
    private boolean timerRunning;
    private long timeLeft;



    long t2Hour,t2Minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countdowntxt = findViewById(R.id.txtoutput); //display
        addbtn = findViewById(R.id.btnadd); //add button
        startbtn = findViewById(R.id.btnstart); //start button
        resetbtn = findViewById(R.id.btnreset); //reset button
        settimebtn = findViewById(R.id.btnsettime); //set time button
        tvtimer1 = findViewById(R.id.tvTimer1); //display


        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCountDownText();
            }
        });
        startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timerRunning){
                    pauseTimer();
                }else{
                    startTimer();

                }

            }
        });
        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();

            }
        });

        settimebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initialize time picker dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        MainActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                //Initialize hour and minute
                                t2Hour = hourOfDay;
                                t2Minute = minute;
                                //Store hours and minutes in string
                                String time = t2Hour + ":" + t2Minute;
                                //Initialize 24 hours time format
                                SimpleDateFormat f24Hours = new SimpleDateFormat(
                                       "HH:mm"
                                );
                                try {
                                    Date date = f24Hours.parse(time);
                                    //initialize 12 hour time format
                                    SimpleDateFormat f12Hours = new SimpleDateFormat(
                                            "HH:mm" //"hh:mm"
                                    );
                                    //Set selected time on textview
                                    tvtimer1.setText(f12Hours.format(date));


                                }catch(ParseException e){
                                    e.printStackTrace();
                                }


                            }
                        },12,0,true

                );
                timePickerDialog.setTitle("Enter hours and minutes");


                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
//                timePickerDialog.updateTime((int)t2Hour,(int)t2Minute);
                timePickerDialog.updateTime(0,0);

                timePickerDialog.show();



            }
        });

        updateCountDownText();


    }

    private void startTimer(){
        timeLeft= totalTime;
        countDownTimer = new CountDownTimer(timeLeft,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                updateCountDownText();

            }

            @Override
            public void onFinish() {
                timerRunning = false;
                startbtn.setText("Start");
                startbtn.setVisibility(View.INVISIBLE);
                resetbtn.setVisibility((View.VISIBLE));
                totalTime = 0;

            }
        }.start();

        timerRunning = true;
        startbtn.setText("pause");
        resetbtn.setVisibility(View.INVISIBLE);



    }


    private void addCountDownText(){

        //

        totalTime = t2Hour*3600*1000 + t2Minute*60*1000 + totalTime; // in miliseconds

        long seconds = totalTime / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        String timeDisplay = String.format(Locale.getDefault(),"%02d:%02d:%02d",hours % 24,minutes % 60,seconds % 60);

        countdowntxt.setText(timeDisplay);


    }


    private void updateCountDownText(){
        // timeLeft is in miliseconds
        long seconds = timeLeft / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;



        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d:%02d",hours % 24,minutes % 60,seconds % 60);



        countdowntxt.setText(timeLeftFormatted); //display


    }


    private void pauseTimer(){
        countDownTimer.cancel();
        timerRunning = false;
        startbtn.setText("Start");
        resetbtn.setVisibility(View.VISIBLE);


    }

    private void resetTimer(){
        timeLeft = totalTime;
        updateCountDownText();
        resetbtn.setVisibility(View.INVISIBLE);
        startbtn.setVisibility(View.VISIBLE);
    }
}
