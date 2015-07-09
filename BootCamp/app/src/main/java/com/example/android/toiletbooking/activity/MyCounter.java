package com.example.android.toiletbooking.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.content.DialogInterface;
import com.example.android.toiletbooking.R;
import com.example.android.toiletbooking.fragment.ListToilets;
import com.example.android.toiletbooking.model.GridViewAdapter;

/**
 * Created by usr0200475 on 15/07/01.
 */
public class MyCounter extends Activity {
    private TextView textTimer;
    private long startTime = 0L;
    private Handler myHandler = new Handler();
    long timeInMillies = 0L;
    long timeLimit = 180000 ;
    long countTime = 0L;
    AlertDialog.Builder alert;


    public static interface OnCompleteListener {
        public abstract void onComplete(String time);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer);

        textTimer = (TextView) findViewById(R.id.textTimer);
        startTime = SystemClock.uptimeMillis();
        myHandler.postDelayed(updateTimerMethod, 0);
        this.setFinishOnTouchOutside(false);
        alert = new AlertDialog.Builder(MyCounter.this);
    }

    public void onClickCancelButton(View view){
        Bundle bundle = new Bundle();
        bundle.putString("cancelFromCounter", "FromMyCounter");
        ListToilets fragmentObject = new ListToilets();
        fragmentObject.setArguments(bundle);
        finish();
    }

    private Runnable updateTimerMethod = new Runnable() {

        public void run() {
            timeInMillies = SystemClock.uptimeMillis() - startTime;
            countTime = timeLimit - timeInMillies;
            int seconds = (int) (countTime / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            textTimer.setText("" + minutes + ":"
                    + String.format("%02d", seconds));
            myHandler.postDelayed(this, 0);
            if (timeInMillies > timeLimit){
                onStopCounter();
            }
        }
    };

    public void onStopCounter(){
        myHandler.removeCallbacks(updateTimerMethod);
        alert.setNeutralButton(R.string.in_problem_rebook, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                onBackPressed();
            }
        });
        alert.setNegativeButton("終了", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                onBackPressed();
            }
        });
        if (! (MyCounter.this).isFinishing()) {
            alert.setTitle(R.string.finish_booking).setMessage(R.string.problem).show();
            Bundle bundle = new Bundle();
            bundle.putString("finishCounter", "FromMyCounter");
            ListToilets fragmentObject = new ListToilets();
            fragmentObject.setArguments(bundle);
        }
    }
}
