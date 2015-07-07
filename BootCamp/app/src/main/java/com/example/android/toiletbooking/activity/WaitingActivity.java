package com.example.android.toiletbooking.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.toiletbooking.R;
import com.example.android.toiletbooking.fragment.ListToilets;
import com.example.android.toiletbooking.model.Toilet;

/**
 * Created by usr0200475 on 15/06/30.
 */
public class WaitingActivity extends Activity {

    public static final int WANT_TO_BOOKING = 0;
    public static final int RESQUEST_SEND_NOTI_AFTER = 1;
    public static final boolean BOOKED = true;
    public static final boolean WATING = true;

    AlertDialog.Builder alert;
    Toilet receiveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_form);

        alert = new AlertDialog.Builder(WaitingActivity.this);

        Intent intent = getIntent();
        receiveData = (Toilet)intent.getSerializableExtra("send");
        TextView textView = (TextView)findViewById(R.id.input_form_title);
        textView.setText("あなたは"+ receiveData.getWaiting() + "人目です");

    }

    public void onClickCancelButton(View view){
        finish();
    }

    public void sendToiletStatus() {
        Bundle bundle = new Bundle();
        bundle.putString("edttext", "USING");
        // set Fragmentclass Arguments
        ListToilets fragobj = new ListToilets();
        fragobj.setArguments(bundle);

    }
}
