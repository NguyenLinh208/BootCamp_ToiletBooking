package com.example.android.toiletbooking.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.DialogFragment;
import android.widget.TextView;

import com.example.android.toiletbooking.R;

/**
 * Created by usr0200475 on 15/07/01.
 */

public class CounterDialog extends DialogFragment {
    AlertDialog.Builder alert;

    private DialogListener listener = null;
    public static CounterDialog newInstance(String title, String message){
        CounterDialog instance = new CounterDialog();
        Bundle arguments = new Bundle();
        arguments.putString("title", title);
        arguments.putString("message", message);
        return instance;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        String title = getArguments().getString("title");
        String message = getArguments().getString("message");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.doPositiveClick();
                        dismiss();
                    }
                })
                .setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.doNegativeClick();
                        dismiss();
                    }
                });
        return builder.create();
    }

    /**
     * リスナーを追加
     */
    public void setDialogListener(DialogListener listener){
        this.listener = listener;
    }

    /**
     * リスナー削除
     */
    public void removeDialogListener(){
        this.listener = null;
    }


}