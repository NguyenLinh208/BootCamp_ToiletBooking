package com.example.android.effectivenavigation.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.TextView;
import android.content.DialogInterface;
import com.example.android.effectivenavigation.R;

/**
 * Created by usr0200475 on 15/07/01.
 */
public class MyCounter extends Activity{
    private TextView textTimer;
    private long startTime = 0L;
    private Handler myHandler = new Handler();
    long timeInMillies = 0L;
    long timeLimit = 20000 ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer);

        textTimer = (TextView) findViewById(R.id.textTimer);
        startTime = SystemClock.uptimeMillis();
        myHandler.postDelayed(updateTimerMethod, 0);
        this.setFinishOnTouchOutside(true);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private Runnable updateTimerMethod = new Runnable() {

        public void run() {
            timeInMillies = SystemClock.uptimeMillis() - startTime;

            int seconds = (int) (timeInMillies / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            textTimer.setText("" + minutes + ":"
                    + String.format("%02d", seconds));
            myHandler.postDelayed(this, 0);
            if (timeInMillies > timeLimit){
                StopCounter();
                onStop();
            }
        }
    };

    public void StopCounter(){
        myHandler.removeCallbacks(updateTimerMethod);
        final AlertDialog.Builder builder = new AlertDialog.Builder(MyCounter.this);
        builder.setNeutralButton("SKIP", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                onBackPressed();
            }
        });
        builder.setTitle("警告").setMessage("時間切れです").show();
    }


}