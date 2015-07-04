package com.example.android.toiletbooking.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import com.example.android.toiletbooking.R;
import com.example.android.toiletbooking.fragment.WomenToilets;

/**
 * Created by usr0200475 on 15/06/30.
 */
public class BookForm extends Activity {

    public static final int WANT_TO_BOOKING = 0;
    public static final int RESQUEST_SEND_NOTI_AFTER = 1;
    public static final boolean BOOKED = true;
    public static final boolean WATING = true;
    private boolean status;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_form);

        Intent intent = getIntent();
        String reiciveData = intent.getStringExtra("send");
        TextView textView = (TextView)findViewById(R.id.input_form_title);
        textView.setText(reiciveData);


        spinner = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.action, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                TextView textView = (TextView) view;
                TextView selectedItem = (TextView) findViewById(R.id.selected_item);
                StringBuilder sb = new StringBuilder();
                sb.append("parent=").append(parent.getClass().getSimpleName())
                        .append(" position=").append(pos).append(" id=").append(id)
                        .append(" textView.getText()=").append(textView.getText());

                Spinner spinner = (Spinner) parent;
                selectedItem.setText(textView.getText());

                // 選択されたアイテムのテキストを取得
                Toast.makeText(getApplicationContext(), sb.toString(),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "nothing", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onClickSendButton (View view){
        int selectedPos = spinner.getSelectedItemPosition();
        switch (selectedPos){
            case WANT_TO_BOOKING:
                status = BOOKED;
                sendToiletStatus();
                Intent intent = new Intent(BookForm.this,MyCounter.class);
                finish();
                startActivity(intent);
                break;
            case RESQUEST_SEND_NOTI_AFTER:
                finish();
                break;
        }
    }

    public void onClickCancelButton(View view){
        finish();
    }

    public void sendToiletStatus() {
        Bundle bundle = new Bundle();
        bundle.putString("edttext", "USING");
        // set Fragmentclass Arguments
        WomenToilets fragobj = new WomenToilets();
        fragobj.setArguments(bundle);

    }
}
