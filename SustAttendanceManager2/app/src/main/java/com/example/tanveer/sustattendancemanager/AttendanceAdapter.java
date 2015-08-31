package com.example.tanveer.sustattendancemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Tanveer on 7/3/2015.
 */
public class AttendanceAdapter extends ArrayAdapter{
    private Context mActivity;
    private List<String> courses;
    public AttendanceAdapter(Context context, int resource,List<String> course) {
        super(context, resource);
        mActivity =context;
        courses =course;
    }

    @Override
    public int getCount() {
        return courses.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.single_course_item,parent,false);
        ImageView icon = (ImageView) convertView.findViewById(R.id.icon);
        TextView courseName = (TextView) convertView.findViewById(R.id.courseName);
        if(position%3==0)
        {
            icon.setImageResource(R.drawable.circle_pink);
        }
        else if(position%3==1)
            icon.setImageResource(R.drawable.circle_blue);
        else
        icon.setImageResource(R.drawable.circle_green);
        courseName.setText(courses.get(position));

        return convertView;
    }
}
