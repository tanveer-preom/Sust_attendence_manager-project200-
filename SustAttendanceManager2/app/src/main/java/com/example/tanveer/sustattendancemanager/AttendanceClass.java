package com.example.tanveer.sustattendancemanager;

import java.security.Timestamp;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

public class AttendanceClass extends ActionBarActivity implements LoadingClassListener,OnItemClickListener {
	private Button addClass;
	private ListView classList;
	private String username,password,dept,jsonClasses,year,semester,course_id;
	private List<String> classes,classesToShow;
	private JSONObject currentClass;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		CalendarView view;

		super.onCreate(savedInstanceState);
		setContentView(R.layout.attendance_course);
		username =getIntent().getExtras().getString("user");
		password=getIntent().getExtras().getString("pass");
		dept =getIntent().getExtras().getString("dept");
		jsonClasses=getIntent().getExtras().getString("classes");
		year = getIntent().getExtras().getString("year_id");
		semester = getIntent().getExtras().getString("sem_id");
		course_id =getIntent().getExtras().getString("course_id");

		initUi();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_attendance, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId()==R.id.addclass)
		{
			onClick();
		}

		return true;
	}

	private void initUi()
	{
		//addClass = (Button) findViewById(R.id.addClass);
		classList = (ListView) findViewById(R.id.class_list);
		classes = new LinkedList<String>();
		classesToShow =new LinkedList<>();
		//addClass.setOnClickListener(this);
		classList.setOnItemClickListener(this);
		if(!jsonClasses.equals(""))
		initList();
	}
	private void initList()
	{
		classesToShow.clear();
		Runnable r =new Runnable()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					JSONArray array =new JSONArray(jsonClasses);
					for(int i=0;i<array.length();i++)
					{
						JSONObject temp = array.getJSONObject(i);
						classesToShow.add(temp.getString("year")+" / "+temp.getString("month")+" / "+temp.getString("day"));
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				ArrayAdapter<String> adapter =new ArrayAdapter<String>(AttendanceClass.this,R.layout.single_row_welcomelist,R.id.text,classesToShow);
				classList.setAdapter(adapter);
			}
			
		};
		runOnUiThread(r);
	}


	public void onClick() {
		// TODO Auto-generated method stub
		java.util.Date date =new java.util.Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		String cyear = dateFormat.format(date);
		dateFormat = new SimpleDateFormat("MM");
		String month = dateFormat.format(date);
		dateFormat = new SimpleDateFormat("dd");
		String day = dateFormat.format(date);
		new LoadingClass(StaticDatas.uri+"/AddClass",AttendanceClass.this).execute(new Key_value("user",username)
				,new Key_value("pass",password),
				new Key_value("course_id",course_id),
				new Key_value("year_id",year),
				new Key_value("semester_id",semester),
				new Key_value("day",day),
				new Key_value("month",month),
				new Key_value("year",cyear),
				new Key_value("dept",dept)
		);


	}
	private String getTimeDate(boolean timestamp)
	{
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE_dd_MM_yy_hh_mm_ss_a");
		

		Log.i("Tanvy",dateFormat.format(cal.getTime()));
		return dateFormat.format(cal.getTime());
	}
	@Override
	public Activity getContext() {
		// TODO Auto-generated method stub
		return this;
	}
	@Override
	public void onPostExecuted(String jsonresult) {
		Log.i("tanvy",jsonresult);
		if(jsonresult.trim().equals("success"))
		{
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(AttendanceClass.this,"Class Added Successfully",Toast.LENGTH_LONG).show();
					new LoadingClass(StaticDatas.uri + "/LoadClasses?user=" + StaticDatas.serviceUser + "&pass=" + StaticDatas.servicePass + "&year_id=" + year+ "&sem_id=" + semester + "&course_id=" + course_id + "&dept=" + dept, AttendanceClass.this).execute();
				}
			});
			return;
		}
		try {
			new JSONArray(jsonresult);
			jsonClasses =jsonresult;
			initList();
		} catch (JSONException e) {
			e.printStackTrace();
		}


		
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
		// TODO Auto-generated method stub
		try {
			JSONArray classes =new JSONArray(jsonClasses);
			currentClass = classes.getJSONObject(pos);
			LoadingClassListener stLoader =new LoadingClassListener() {
				@Override
				public Activity getContext() {
					return AttendanceClass.this;
				}

				@Override
				public void onPostExecuted(String jsonresult) {
					Intent i =new Intent(AttendanceClass.this,AttendanceWindow.class);
					i.putExtra("class_detail",currentClass.toString());
					i.putExtra("year_id",year);
					i.putExtra("semester_id",semester);
					i.putExtra("dept",dept);
					i.putExtra("username",username);
					i.putExtra("password",password);
					i.putExtra("students",jsonresult);
					startActivity(i);
				}
			};

			new LoadingClass(StaticDatas.uri+"/LoadStudents?user="+username+
					"&pass="+password+
					"&year_id="+year+
					"&sem_id="+semester+
					"&dept="+dept+
					"&course_id="+currentClass.getString("course_id")+
					"&class_id="+currentClass.getString("id")+
					"&day="+currentClass.getString("day")+
					"&month="+currentClass.getString("month")+
					"&year="+currentClass.getString("year")

					,stLoader).execute();

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

}
