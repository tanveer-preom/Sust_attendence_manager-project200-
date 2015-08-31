package com.example.tanveer.sustattendancemanager;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class AttendanceWindow extends ActionBarActivity implements LoadingClassListener,UpdateAttendanceListener{
	private List<NameRegAttend> students;
	private ListView studentList;
	private String username,password,classdetailjson,studentsjson,year_id,sem_id,dept;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.attendance_window);
		classdetailjson = getIntent().getExtras().getString("class_detail");
		studentsjson =  getIntent().getExtras().getString("students");
		username = getIntent().getExtras().getString("username");
		password = getIntent().getExtras().getString("password");
		year_id = getIntent().getExtras().getString("year_id");
		sem_id=getIntent().getExtras().getString("semester_id");
		dept =getIntent().getExtras().getString("dept");
		Log.i("tanvy",classdetailjson);
		Log.i("tanvy",studentsjson);
		initUi();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	private void initUi()
	{
		students =new LinkedList<NameRegAttend>();
		studentList = (ListView) findViewById(R.id.attendanceList);
		initList();
		
	}
	private void initList()
	{
		Runnable r =new Runnable()
		{

			@Override
			public void run() {

				CustomAdapter_attendance adapter = null;
				try {
					adapter = new CustomAdapter_attendance(AttendanceWindow.this,studentsjson,classdetailjson,username,password,dept,year_id,sem_id,dept);
					studentList.setAdapter(adapter);
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		};
		runOnUiThread(r);
		
	}

	

	@Override
	public Activity getContext() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public void onPostExecuted(String jsonresult) {
		// TODO Auto-generated method stub


	}
	@Override
	public synchronized void getErrorMessage(Exception e) {
		// TODO Auto-generated method stub
		Log.i("Tanvy", "error in update thread "+e.getMessage());
		
	}
	@Override
	public String attendanceNumber(boolean isPresent) {
		// TODO Auto-generated method stub
		if(isPresent)
		return "1";
		else
		return "0";
	}
	@Override
	public Context classContext() {
		// TODO Auto-generated method stub
		return this;
	}
	@Override
	public Key_value[] getKeyVal(String attendance,String reg_no) {
		// TODO Auto-generated method stub



		return null;
	}
	@Override
	public NameRegAttend objectToModify(int pos) {
		// TODO Auto-generated method stub
		return students.get(pos);
	}
	@Override
	public String getUri() {
		// TODO Auto-generated method stub
		return StaticDatas.uri;
	}
	
	

}
