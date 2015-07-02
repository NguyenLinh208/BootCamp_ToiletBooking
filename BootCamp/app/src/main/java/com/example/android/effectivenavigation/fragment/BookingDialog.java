package com.example.android.effectivenavigation.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
/**
 * Created by usr0200475 on 15/07/01.
 */

public class BookingDialog extends DialogFragment {

    private DialogListener listener = null;
    public static BookingDialog newInstance(String title, String message, int type){
        BookingDialog instance = new BookingDialog();

        // ダイアログに渡すパラメータはBundleにまとめる
        Bundle arguments = new Bundle();
        arguments.putString("title", title);
        arguments.putString("message", message);
        arguments.putInt("type", type);

        instance.setArguments(arguments);

        return instance;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        String title = getArguments().getString("title");
        String message = getArguments().getString("message");
        int type = getArguments().getInt("type");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("予約", new DialogInterface.OnClickListener() {
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