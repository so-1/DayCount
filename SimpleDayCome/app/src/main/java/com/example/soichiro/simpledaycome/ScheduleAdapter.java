package com.example.soichiro.simpledaycome;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;

/**
 * Created by soichiro on 2018/02/18.
 */

public class ScheduleAdapter extends RealmBaseAdapter<Schedule> {

    private static class ViewHolder{
        TextView date;
        TextView title;
        TextView dayCount;
    }

    public ScheduleAdapter(@Nullable OrderedRealmCollection<Schedule> data) {
        super(data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.list_item_original, parent, false);
        viewHolder = new ViewHolder();
        viewHolder.date = (TextView) convertView.findViewById(R.id.text1);
        viewHolder.title = (TextView) convertView.findViewById(R.id.text2);
        viewHolder.dayCount = (TextView) convertView.findViewById(R.id.text3);
        convertView.setTag(viewHolder);
    }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        Schedule schedule = adapterData.get(position);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String formatDate = sdf.format(schedule.getDate());
        viewHolder.date.setText(formatDate);
        viewHolder.title.setText(schedule.getTitle());
        viewHolder.dayCount.setText(schedule.getDayCount());
        return convertView;
    }
}
