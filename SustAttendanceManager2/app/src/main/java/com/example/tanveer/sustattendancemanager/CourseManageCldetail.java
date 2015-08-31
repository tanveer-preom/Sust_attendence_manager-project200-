package com.example.tanveer.sustattendancemanager;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CourseManageCldetail extends AppCompatActivity implements LoadingClassListener{
    private JSONObject class_detail;
    private JSONArray students;
    ListView stdlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_manage_cldetail);
        stdlist= (ListView) findViewById(R.id.students);
        String jsonClass = getIntent().getExtras().getString("class_detail");
        try {
            class_detail =new JSONObject(jsonClass);
            new LoadingClass(StaticDatas.uri+"/LoadStudents?user="+StaticDatas.serviceUser+
                    "&pass="+StaticDatas.servicePass+
                    "&year_id="+class_detail.getString("year_id")+
                    "&sem_id="+class_detail.getString("semester_id")+
                    "&dept="+class_detail.getString("accepting_dept")+
                    "&course_id="+class_detail.getString("course_id")+
                    "&class_id="+class_detail.getString("id")+
                    "&day="+class_detail.getString("day")+
                    "&month="+class_detail.getString("month")+
                    "&year="+class_detail.getString("year")+
                    "&give_all=yes"

                    ,this).execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_course_manage_cldetail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Activity getContext() {
        return this;
    }

    @Override
    public void onPostExecuted(final String jsonresult) {
        Log.i("tanvy",jsonresult);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CourseManageClDetailAdapter adapter =new CourseManageClDetailAdapter(CourseManageCldetail.this,R.layout.single_row_manage_cl,jsonresult);
                stdlist.setAdapter(adapter);
            }
        });

    }
}
