package com.example.soichiro.simpledaycome;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by soichiro on 2018/02/18.
 */

public class Schedule extends RealmObject {
    @PrimaryKey
    private long id;
    private Date date;
    private Date nowDay;
    private String title;
    private String detail;
    private String dayCount;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }


    public Date getNowDay() {return nowDay;}
    public void setNowDay(Date nowDay) {
        this.nowDay = nowDay;
    }



    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDetail() {
        return detail;
    }
    public void setDetail(String detail) {
        this.detail = detail;
    }



//差分の日付が数字から文字列に変換されて入力されている。------------------------------------
    public String getDayCount() {
        return dayCount;
    }
    public void setDayCount(String dayCount) {this.dayCount = dayCount;
//------------------------------------------------------------------------------------------

    }



}
