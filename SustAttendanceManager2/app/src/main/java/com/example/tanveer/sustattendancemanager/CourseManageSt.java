package com.example.tanveer.sustattendancemanager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class CourseManageSt extends android.support.v4.app.Fragment implements View.OnClickListener,LoadingClassListener{
    private JSONArray students;
    private View view;
    private Activity courseActivity;
    private ListView studentList;
    private TextView addStudent;
    private String dept,courseId,year_id,sem_id;
    private  CourseManageStAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return view=inflater.inflate(R.layout.fragment_course_manage_st,container,false);
    }
    public CourseManageSt initComps(String str,Activity context,String dept,String courseId,String yearid,String semid)
    {
        this.courseId =courseId;
        year_id=yearid;
        sem_id=semid;
        this.dept=dept;
        try {
            students =new JSONArray(str);
            courseActivity =context;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return this;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        studentList = (ListView) view.findViewById(R.id.students);
        addStudent = (TextView) view.findViewById(R.id.stAdd);
        addStudent.setOnClickListener(this);
        initList();

    }
    private void initList()
    {
        adapter =new CourseManageStAdapter(courseActivity,students);
        studentList.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        final Dialog sessionSelect =new Dialog(courseActivity);
        ListView list =new ListView(courseActivity);
        sessionSelect.setContentView(list);
        //sessionSelect.requestWindowFeature(Window.FEATURE_NO_TITLE);
        sessionSelect.setTitle("Select a batch from below");
        final String batches[] ={"2002","2003","2004","2005","2006","2007","2008","2009","2010","2011","2012","2013","2014","2015"};
        ArrayAdapter<String> adapter =new ArrayAdapter<String>(courseActivity,android.R.layout.simple_list_item_single_choice,batches);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sessionSelect.dismiss();
                //Log.i("tanvy",StaticDatas.uri+"/Students?user="+StaticDatas.serviceUser+"&pass="+StaticDatas.servicePass+"&dept="+dept+"&session_no="+batches[position]);
                new LoadingClass(StaticDatas.uri+"/Students?user="+StaticDatas.serviceUser+"&pass="+StaticDatas.servicePass+"&dept="+dept+"&session_no="+batches[position], new LoadingClassListener() {
                    @Override
                    public Activity getContext() {
                        return courseActivity;
                    }

                    @Override
                    public void onPostExecuted(String jsonresult) {
                        try {
                            final JSONArray stdArray =new JSONArray(jsonresult);
                            courseActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    final Dialog dialog =new Dialog(courseActivity);
                                    dialog.setContentView(R.layout.dialog_session_selector);
                                    ListView stdlist = (ListView) dialog.findViewById(R.id.students);


                                    final addStdDialogAdapter adapter2 =new addStdDialogAdapter(courseActivity,R.layout.session_select,stdArray);
                                    stdlist.setAdapter(adapter2);
                                    Button cancel = (Button) dialog.findViewById(R.id.cancel);
                                    cancel.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                        }
                                    });
                                    Button done = (Button) dialog.findViewById(R.id.done);
                                    done.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                            new LoadingClass(StaticDatas.uri+"/AddStudents",CourseManageSt.this).execute(
                                                    new Key_value("pass",StaticDatas.servicePass),
                                                    new Key_value("user",StaticDatas.serviceUser),
                                                    new Key_value("course_id",courseId),
                                                    new Key_value("year_id",year_id),
                                                    new Key_value("semester_id",sem_id),
                                                    new Key_value("array",stdArray.toString()));

                                        }
                                    });
                                    CheckBox selectAll = (CheckBox) dialog.findViewById(R.id.selectAll);
                                    selectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                        @Override
                                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                            if(isChecked)
                                            {
                                                for(int i=0;i<stdArray.length();i++)
                                                {
                                                    try {
                                                        JSONObject temp = stdArray.getJSONObject(i);
                                                        temp.put("isChecked","1");
                                                        stdArray.put(i,temp);
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                                adapter2.notifyDataSetChanged();

                                            }
                                            else
                                            {
                                                for(int i=0;i<stdArray.length();i++)
                                                {
                                                    try {
                                                        JSONObject temp = students.getJSONObject(i);
                                                        temp.put("isChecked","0");
                                                        stdArray.put(i,temp);
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                                adapter2.notifyDataSetChanged();
                                            }

                                        }
                                    });

                                    dialog.show();

                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }).execute();

            }
        });
        sessionSelect.show();
    }

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
                    new LoadingClass(StaticDatas.uri + "/LoadStudent?user=" + StaticDatas.serviceUser + "&pass=" + StaticDatas.servicePass + "&year_id=" + year_id + "&semester_id=" + sem_id + "&course_id=" + courseId + "&dept=" + dept, new LoadingClassListener() {
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
                    }).execute();
                }
            });

        }

    }
}

