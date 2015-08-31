package com.example.tanveer.sustattendancemanager;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

/**
 * Created by tanveer on 8/17/15.
 */
public class backgroundUpdate extends Service{
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    @Override
    public void onCreate() {

        //Log.i("tanvy", "sv started");
        Thread t1=new Thread()
        {
            public void run()
            {

                RequestQueue queue = Volley.newRequestQueue(backgroundUpdate.this);


                while(StaticDatas.apprunning)
                {
                    SqlDb db =new SqlDb();
                    db.open(backgroundUpdate.this);
                    Cursor resultsToprocess =db.execquery("select * from data_update");
                    if(resultsToprocess!=null)
                    {
                        if(resultsToprocess.moveToFirst())
                        {
                            do{
                                String username = StaticDatas.serviceUser;
                                final String password = StaticDatas.servicePass;
                                final String classid  = resultsToprocess.getString(0);
                                final String course_id  = resultsToprocess.getString(1);
                                final String day  = resultsToprocess.getString(2);
                                final String month  = resultsToprocess.getString(3);
                                final String year  = resultsToprocess.getString(4);
                                final String reg_no  = resultsToprocess.getString(5);
                                String late  = resultsToprocess.getString(6);
                                String done  = resultsToprocess.getString(8);
                                String success  = resultsToprocess.getString(7);
                                final String late_min  = resultsToprocess.getString(9);
                                final String year_id = resultsToprocess.getString(10);
                                final String sem_id =resultsToprocess.getString(11);
                                String uri =String.format(StaticDatas.uri + "/UpdateAttendance?user=%s&pass=%s&id=%s&course_id=%s&reg_no=%s&day=%s&month=%s&year=%s&year_id=%s&semester_id=%s&late=%s&done=%s&late_min=%s&success=%s", username, password, classid, course_id, reg_no, day, month, year, year_id, sem_id, late, done, late_min, success);
                                Log.i("tanvy",uri);



// Request a string response from the provided URL.
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, uri,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                // Display the first 500 characters of the response string.
                                                if(response.trim().equals("Success"))
                                                {
                                                    Log.i("tanvy", response + "deleted");
                                                    SqlDb data = new SqlDb();
                                                    data.open(backgroundUpdate.this);
                                                    data.execupdate(String.format("delete from data_update where class_id=%s and course_id='%s' and day =%s and month=%s and year=%s and year_id=%s and semester_id=%s and reg_no='%s'", classid, course_id, day, month, year, year_id, sem_id, reg_no));
                                                    data.close();
                                                }

                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error){
                                    }
                                });

                                queue.add(stringRequest);

                            }while(resultsToprocess.moveToNext());


                        }

                    }

                    resultsToprocess.close();
                    db.close();
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

                stopSelf();


            }


        };
        t1.setPriority(9);
        t1.start();
       // Log.i("tanvy","th started");

    }

}
