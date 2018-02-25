package com.example.soichiro.simpledaycome;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class ScheduleEditActivity extends AppCompatActivity {
    private Realm mRealm;
    EditText mDateEdit;
    EditText mTitleEdit;
    EditText mDetailEdit;
    TextView mDayCount;
    Button mDelete;
    Button mChoice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_edit);
        mRealm = Realm.getDefaultInstance();
        mDateEdit = (EditText) findViewById(R.id.dateEdit);
        mTitleEdit = (EditText) findViewById(R.id.titleEdit);
        mDetailEdit = (EditText) findViewById(R.id.detailEdit);
        mDayCount = (TextView) findViewById(R.id.countingDay);
        mDelete = (Button) findViewById(R.id.delete);


        mChoice = findViewById(R.id.calChoice);
        mChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //カレンダー表示
                DatePickerDialogFragment datePicker = new DatePickerDialogFragment();
                datePicker.show(getSupportFragmentManager(), "datePicker");

                String date = mDateEdit.getText().toString();
  // なんか意味がありそうでわからない。




                int year = 0;
                int month = 0;
                int dayOfMonth = 0;
               // if (TextUtils.isEmpty(date)) {
                    Calendar calendar = Calendar.getInstance();
                    year = calendar.get(Calendar.YEAR);
                    month = calendar.get(Calendar.MONTH);
                    dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                    /*


                } else {
                    year = Integer.valueOf(date.substring(0, 4));
                    month = Integer.valueOf(date.substring(5, 7));
                    month = month - 1;
                    dayOfMonth = Integer.valueOf(date.substring(8, 10));
                }
                */


            }
        });


        long scheduleId = getIntent().getLongExtra("schedule_id", -1);
        if (scheduleId != -1) {
            RealmResults<Schedule> results = mRealm.where(Schedule.class)
                    .equalTo("id", scheduleId).findAll();
            Schedule schedule = results.first();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String date = sdf.format(schedule.getDate());
            mDateEdit.setText(date);
            mTitleEdit.setText(schedule.getTitle());
            mDetailEdit.setText(schedule.getDetail());
            mDayCount.setText(schedule.getDayCount());
            mDelete.setVisibility(View.VISIBLE);
        } else {
            mDelete.setVisibility(View.INVISIBLE);
        }

    }

    public void onSaveTapped(View view) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date dateParse = new Date();
        try {
            dateParse = sdf.parse(mDateEdit.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        final Date date = dateParse;

        long scheduleId = getIntent().getLongExtra("schedule_id", -1);
        if (scheduleId != -1) {
            final RealmResults<Schedule> results = mRealm.where(Schedule.class)
                    .equalTo("id", scheduleId).findAll();
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Schedule schedule = results.first();
                    schedule.setDate(date);
                    schedule.setDayCount(mDayCount.getText().toString());
                    schedule.setTitle(mTitleEdit.getText().toString());
                    schedule.setDetail(mDetailEdit.getText().toString());

                }
            });
            Snackbar.make(findViewById(android.R.id.content),
                    "アップデートしました", Snackbar.LENGTH_LONG)
                    .setAction("戻る", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    })
                    .setActionTextColor(Color.YELLOW)
                    .show();

        } else {


            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Number maxId = realm.where(Schedule.class).max("id");
                    long nextId = 0;
                    if (maxId != null) nextId = maxId.longValue() + 1;
                    Schedule schedule
                            = realm.createObject(Schedule.class, new Long(nextId));
                    schedule.setDate(date);
                    schedule.setTitle(mTitleEdit.getText().toString());
                    schedule.setDetail(mDetailEdit.getText().toString());
                    schedule.setDayCount(mDayCount.getText().toString());
                }
            });
            Toast.makeText(this, "追加しました", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    public void onDeleteTapped(View view) {
        final long scheduleId = getIntent().getLongExtra("schedule_id", -1);
        if (scheduleId != -1) {

            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Schedule schedule = realm.where(Schedule.class).equalTo("id",scheduleId).findFirst();
                    schedule.deleteFromRealm();
                    finish();
                }
            });

        }
    }
    /*
    public void onChoice(View view){

        DatePickerDialogFragment datePicker = new DatePickerDialogFragment();
        datePicker.show(getSupportFragmentManager(), "datePicker");
    }
    */




    public void setTextView(String value){
        TextView textView = (TextView) findViewById(R.id.dateEdit);
        textView.setText(value);
    }

    public void setCountView(int value){
        TextView textView = (TextView) findViewById(R.id.countingDay);
        String valueToString = String.valueOf(value);
        textView.setText(valueToString);
    }

}