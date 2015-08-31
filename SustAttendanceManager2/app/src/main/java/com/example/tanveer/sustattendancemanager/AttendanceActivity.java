package com.example.tanveer.sustattendancemanager;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AttendanceActivity extends ActionBarActivity implements LoadingClassListener,OnItemClickListener{
		private String username,password,jsonCourse,year,semester,dept;
		private ListView courseList;
		private List<String> courses;
		private String selectedCourse;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.attendance_activity);
			username=getIntent().getExtras().getString("user");
			password=getIntent().getExtras().getString("pass");
			jsonCourse = getIntent().getExtras().getString("courses");
			year = getIntent().getExtras().getString("year_id");
			semester =getIntent().getExtras().getString("sem_id");
			dept =getIntent().getExtras().getString("dept");
			initUi();

		}
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
		private void initUi()
		{
			courseList =(ListView) findViewById(R.id.course_list);
			courses =new LinkedList<String>();
			courseList.setOnItemClickListener(this);
			initList();
		}
		private void initList()
		{
			Runnable r=new Runnable()
			{

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						JSONArray array = new JSONArray(jsonCourse);
						for(int i=0;i<array.length();i++)
						{
							JSONObject currentCourse = array.getJSONObject(i);
							courses.add(currentCourse.getString("course_id"));
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
					AttendanceAdapter adapter =new AttendanceAdapter(AttendanceActivity.this,R.layout.single_course_item,courses);
					courseList.setAdapter(adapter);
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
			try {
				new JSONArray(jsonresult);
				Intent i =new Intent(AttendanceActivity.this,AttendanceClass.class);
				i.putExtra("user",username);
				i.putExtra("pass",password);
				i.putExtra("year_id",year);
				i.putExtra("sem_id",semester);
				i.putExtra("course_id",selectedCourse);
				i.putExtra("dept",dept);
				i.putExtra("classes",jsonresult);
				startActivity(i);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Log.i("Tanvy", "JSON "+e.getMessage());
				e.printStackTrace();
				Intent i =new Intent(AttendanceActivity.this,AttendanceClass.class);
				i.putExtra("user",username);
				i.putExtra("pass",password);
				i.putExtra("year_id",year);
				i.putExtra("sem_id",semester);
				i.putExtra("course_id",selectedCourse);
				i.putExtra("dept",dept);
				i.putExtra("classes", "");
				startActivity(i);

			}
			
		}
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int pos,long arg3) {
			// TODO Auto-generated method stub
			selectedCourse = courses.get(pos);
			new LoadingClass(StaticDatas.uri+"/LoadClasses?user="+username+"&pass="+password+"&year_id="+year+"&sem_id="+semester+"&dept="+dept+"&course_id="+courses.get(pos),this).execute();
			
		}
		
}
