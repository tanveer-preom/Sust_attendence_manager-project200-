package com.example.tanveer.sustattendancemanager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class CourseManageCl extends android.support.v4.app.Fragment implements AdapterView.OnItemClickListener,View.OnClickListener,LoadingClassListener {
    private View view;
    private ListView classList;
    private Activity courseActivity;
    private JSONArray students;
    private ArrayList<String> sList;
    private String day,month,year,course_id,year_id,semester_id,dept;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return view=inflater.inflate(R.layout.fragment_course_manage_cl,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        classList = (ListView) view.findViewById(R.id.classes);
        classList.setOnItemClickListener(this);
        TextView addClass= (TextView) view.findViewById(R.id.clAdd);
        addClass.setOnClickListener(this);
        sList =new ArrayList<>();
        if(students!=null)
        initList();
    }
    public CourseManageCl initComp(Activity con,JSONArray arr,String course,String yearid,String semid,String dept)
    {
        courseActivity =con;
        students = arr;
        course_id=course;
        year_id=yearid;
        semester_id=semid;
        this.dept=dept;
        return this;
    }
    private void initList()
    {
        sList.clear();
        for(int i=0;i<students.length();i++)
        {
            try {
                JSONObject temp = students.getJSONObject(i);
                String stTopass= String.format("%s/%s/%s",temp.getString("day"),temp.getString("month"),temp.getString("year"));
                sList.add(stTopass);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        ArrayAdapter<String> adapter =new ArrayAdapter<String>(courseActivity,R.layout.single_row_welcomelist,R.id.text,sList);
        classList.setAdapter(adapter);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i =new Intent(courseActivity,CourseManageCldetail.class);
        try {
            JSONObject temp = students.getJSONObject(position);
            i.putExtra("class_detail",temp.toString());
            startActivity(i);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        final Dialog dialog =new Dialog(courseActivity);
        dialog.setTitle("Choose date");
        dialog.setContentView(R.layout.dialog_add_class);
        final CalendarView calendar = (CalendarView) dialog.findViewById(R.id.calendar);
        Button done = (Button) dialog.findViewById(R.id.done);
        Button cancel = (Button) dialog.findViewById(R.id.cancel);
        dialog.show();
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {


                try {
                    day=dayOfMonth+"";
                    CourseManageCl.this.month =month+1+"";
                    CourseManageCl.this.year =year+"";

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                new LoadingClass(StaticDatas.uri + "/AddClass", new LoadingClassListener() {
                    @Override
                    public Activity getContext() {
                        return courseActivity;
                    }

                    @Override
                    public void onPostExecuted(String jsonresult) {
                        if(jsonresult.trim().equals("success"))
                        {
                            courseActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(courseActivity,"Class Added Successfully",Toast.LENGTH_LONG).show();
                                    new LoadingClass(StaticDatas.uri + "/LoadClasses?user=" + StaticDatas.serviceUser + "&pass=" + StaticDatas.servicePass + "&year_id=" + year_id + "&sem_id=" + semester_id + "&course_id=" + course_id + "&dept=" + dept, CourseManageCl.this).execute();

                                }
                            });


                        }


                    }
                }).execute(new Key_value("user",StaticDatas.serviceUser)
                        ,new Key_value("pass",StaticDatas.servicePass),
                        new Key_value("year_id",year_id),
                        new Key_value("semester_id",semester_id),
                        new Key_value("day",day),
                        new Key_value("month",month),
                        new Key_value("year",year),
                        new Key_value("dept",dept),
                        new Key_value("course_id",course_id)
                );


            }
        });

    }

    @Override
    public Activity getContext() {
        return courseActivity;
    }

    @Override
    public void onPostExecuted(final String jsonresult) {
        courseActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    students =new JSONArray(jsonresult);
                    initList();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
