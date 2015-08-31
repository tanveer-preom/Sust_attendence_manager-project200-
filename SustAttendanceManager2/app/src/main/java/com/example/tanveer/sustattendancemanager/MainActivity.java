package com.example.tanveer.sustattendancemanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{
    private String userid,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button login =(Button) findViewById(R.id.login);
        login.setOnClickListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        userid= ((EditText) findViewById(R.id.username)).getText().toString();
        password= ((EditText) findViewById(R.id.password)).getText().toString();
        new loginclass().execute();

    }
    private class loginclass extends AsyncTask<Void,String,Void>
    {

        private ProgressDialog loadingdialog;
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            loadingdialog = new ProgressDialog(MainActivity.this);
            loadingdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            loadingdialog.setMessage("Please Wait !!");
            loadingdialog.show();

        }

        protected Void doInBackground(Void... arg0) {
            // TODO Auto-generated method stub
            try
            {
                Httprequest req=new Httprequest(StaticDatas.uri+"/Login?user="+userid+"&pass="+password);
                Log.i("tanvy",StaticDatas.uri+"/Login?user="+userid+"&pass="+password);


                //req.setKeyValues(user,pass);
                req.execute();
                String result=req.getResponse();
                JSONArray test =new JSONArray(result);
                final Intent welcome =new Intent(MainActivity.this,WelcomeWindow.class);
                welcome.putExtra("welcome", test.toString());

                        StaticDatas.serviceUser = userid;
                        StaticDatas.servicePass = password;
                        StaticDatas.apprunning = true;
                        Intent i =new Intent(MainActivity.this,backgroundUpdate.class);
                        startService(i);
                        startActivity(welcome);
                        finish();




            }
            catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Log.i("Tanvy", e.getMessage());
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block

                Log.i("Tanvy", e.getMessage());
            } catch (IOException e) {
                // TODO Auto-generated catch block

                Log.i("Tanvy", e.getMessage());
            } catch (JSONException e) {
                Log.i("Tanvy", e.getMessage());

            } finally
            {
                loadingdialog.dismiss();

            }

            return null;



        }


    }
}
