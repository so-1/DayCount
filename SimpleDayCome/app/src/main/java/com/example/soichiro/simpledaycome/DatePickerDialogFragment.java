package com.example.soichiro.simpledaycome;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

import static java.lang.reflect.Array.set;

public class DatePickerDialogFragment extends DialogFragment  {

    @Override
    // ダイアログが生成された時に呼ばれるメソッド ※必須
    public Dialog onCreateDialog(Bundle savedInstanceState){
        // 今日の日付のカレンダーインスタンスを取得
        final Calendar calendar = Calendar.getInstance();

        // ダイアログ生成  DatePickerDialogのBuilderクラスを指定してインスタンス化します
        DatePickerDialog dateBuilder = new DatePickerDialog(
                getActivity(),
                new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // 選択された年・月・日を整形 ※月は0-11なので+1している
                        String dateStr = year + "/" + (month + 1) + "/" + dayOfMonth;

                        // MainActivityのインスタンスを取得
                        ScheduleEditActivity act = (ScheduleEditActivity) getActivity();
                        act.setTextView(dateStr);

                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(year,month,dayOfMonth);
                        // 1970/1/1 から設定した calendar1 のミリ秒
                        long timeMillis1 = calendar1.getTimeInMillis();

                        // 現在時刻のミリ秒
                        long currentTimeMillis = System.currentTimeMillis();

                        // 差分のミリ秒
                        long diff = timeMillis1 - currentTimeMillis;

                        // ミリ秒から秒へ変換
                        diff = diff / 1000;
                        // minutes
                        diff = diff / 60;
                        // hour
                        diff = diff / 60;
                        // day
                        diff = diff / 24;

                        act.setCountView((int)diff);

                    }


                },
                calendar.get(Calendar.YEAR), // 初期選択年
                calendar.get(Calendar.MONTH), // 初期選択月
                calendar.get(Calendar.DAY_OF_MONTH) // 初期選択日
        );

        // dateBulderを返す
        return dateBuilder;
    }


}