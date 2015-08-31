package com.example.tanveer.sustattendancemanager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class ExportMain extends ActionBarActivity implements OnItemClickListener,LoadingClassListener{

	private Button addcourse;
	private ListView courselist;
	private ArrayAdapter<String> adapter;
	private ArrayList<String> courses;
	private String username,password,loaded_course,year_id,sem_id,dept;
	private JSONArray jarraycourse;
	private AlertDialog alert=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		username=getIntent().getExtras().getString("user");
		password=getIntent().getExtras().getString("pass");
		dept = getIntent().getExtras().getString("dept");
		try {
			jarraycourse =new JSONArray(getIntent().getExtras().getString("courses"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		setContentView(R.layout.course_window);
		setTitle("My Courses ("+(year_id=getIntent().getExtras().getString("year_id"))+"/"+(sem_id=getIntent().getExtras().getString("sem_id"))+")");
		initUi();
		//new LoadingClass(StaticDatas.uri,this).execute(new Key_value("user",username),new Key_value("pass",password),new Key_value("command","course"));

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
			onCourseAddClicked();
		}

		return true;
	}

	private void initUi()
	{

		courselist=(ListView) findViewById(R.id.course_list);

		courselist.setOnItemClickListener(this);
		courses= new ArrayList<>();
		initList();
	}
	private void initList()
	{

		Runnable r =new Runnable()
		{
			public void run()
			{

				if(jarraycourse!=null)
					for(int i =0;i<jarraycourse.length();i++)
					{
						try {
							JSONObject temp = jarraycourse.getJSONObject(i);
							courses.add(temp.getString("course_id"));
						} catch (JSONException e) {
							e.printStackTrace();
						}

					}


				adapter=new ArrayAdapter<String>(ExportMain.this,R.layout.single_row_welcomelist,R.id.text,courses);
				courselist.setAdapter(adapter);
			}
		};
		runOnUiThread(r);

	}

	public void onCourseAddClicked() {
		// TODO Auto-generated method stub





		//initList();
	}
	@Override
	public void onItemClick(AdapterView<?> list, View arg1, int pos, long id) {
		// TODO Auto-generated method stub
		try {
			String course_id = jarraycourse.getJSONObject(pos).getString("course_id");
			Intent i =new Intent(ExportMain.this,ExportFinal.class);
			i.putExtra("course_id",course_id);
			i.putExtra("year_id",year_id);
			i.putExtra("semester_id",sem_id);
			i.putExtra("dept",dept);
			i.putExtra("user",username);
			i.putExtra("pass",password);
			startActivity(i);
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
	@Override
	public Activity getContext() {
		// TODO Auto-generated method stub
		return ExportMain.this;
	}
	@Override
	public void onPostExecuted(String jsonresult) {
		// TODO After asynctask is finished
		if(jsonresult.compareTo("success")==0)
		{
			Runnable r=new Runnable()
			{
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast.makeText(ExportMain.this,"Course Loaded Successfully !!" , Toast.LENGTH_SHORT).show();
					courses.add(loaded_course);
					initList();
				}
			};
			runOnUiThread(r);
			return;
		}

		Log.i("Tanvy", jsonresult);
		try {
			JArrayIndex index =new JArrayIndex(jsonresult).setJObjectPosition(0);
			int len =index.getLength();
			for(int i=0 ; i<len;i++)
			{
				index.setJObjectPosition(i);
				courses.add(index.getValue("title"));
				//Log.i("Tanvy", courses.get(i));

			}
			initList();

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.i("Tanvy", "JSON "+e.getMessage());
			e.printStackTrace();
		}

	}



}
