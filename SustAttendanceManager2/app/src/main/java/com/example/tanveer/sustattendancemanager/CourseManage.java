package com.example.tanveer.sustattendancemanager;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;

public class CourseManage extends AppCompatActivity implements LoadingClassListener{
    private String username,password,course_id,year_id,semester_id,dept;
    private int reqTracker;
    private JSONArray classes,students;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_manage);
        username = getIntent().getExtras().getString("username");
        password =getIntent().getExtras().getString("password");
        course_id = getIntent().getExtras().getString("course_id");
        year_id = getIntent().getExtras().getString("year_id");
        semester_id = getIntent().getExtras().getString("semester_id");
        dept = getIntent().getExtras().getString("dept");
        reqTracker =0;
        new LoadingClass(StaticDatas.uri+"/LoadStudent?user="+username+"&pass="+password+"&year_id="+year_id+"&semester_id="+semester_id+"&course_id="+course_id+"&dept="+dept,CourseManage.this).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_course_manage, menu);
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
    public void onPostExecuted(String jsonresult) {
        if(reqTracker==0)
        {
            reqTracker=1;
            try {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Log.i("tanvy",StaticDatas.uri + "/LoadClasses?user=" + username + "&pass=" + password + "&year_id=" + year_id + "&semester_id=" + semester_id + "&course_id=" + course_id + "&dept=" + dept);
                        new LoadingClass(StaticDatas.uri + "/LoadClasses?user=" + username + "&pass=" + password + "&year_id=" + year_id + "&sem_id=" + semester_id + "&course_id=" + course_id + "&dept=" + dept, CourseManage.this).execute();
                    }
                });

                students = new JSONArray(jsonresult);


            } catch (JSONException e) {
                e.printStackTrace();
                students =new JSONArray();
            }
            return;
        }
        else
        {
            reqTracker=0;
            try {
                classes =new JSONArray(jsonresult);

                //Log.i("tanvy",students.toString());
                //Log.i("tanvy",classes.toString());
            } catch (JSONException e) {
                e.printStackTrace();
                classes =new JSONArray();
            }
            finally {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        CourseManageSt frag1 = new CourseManageSt();
                        frag1.initComps(students.toString(), CourseManage.this,dept,course_id,year_id,semester_id);
                        ViewPager viewpager= (ViewPager) findViewById(R.id.viewpager);
                        CourseManageCl frag2 =new CourseManageCl();
                        frag2.initComp(CourseManage.this,classes,course_id,year_id,semester_id,dept);
                        Pageradapter adapter =new Pageradapter(getSupportFragmentManager(),frag1,frag2);
                        viewpager.setAdapter(adapter);

                    }
                });
            }


        }

    }
    private class Pageradapter extends FragmentPagerAdapter
    {

        private Fragment student,classes;
        public Pageradapter(FragmentManager fm,Fragment st,Fragment cl) {
            super(fm);
            student=st;
            classes=cl;
        }

        @Override
        public Fragment getItem(int position) {
            if(position==0)
                return student;
            else
                return classes;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position==0)
                return "Manage Students";
            else
                return "Manage Classes";
        }
    }
}
