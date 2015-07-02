package com.example.android.toiletbooking.fragment; /**
 * Created by usr0200475 on 15/07/01.
 */
import java.util.EventListener;

public interface DialogListener extends EventListener{

    /**
     * OKボタンが押されたイベントを通知
     */
    public void doPositiveClick();

    /**
     * Cancelボタンが押されたイベントを通知
     */
    public void doNegativeClick();
}