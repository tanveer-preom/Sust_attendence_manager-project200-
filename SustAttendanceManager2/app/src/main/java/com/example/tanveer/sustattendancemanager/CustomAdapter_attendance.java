package com.example.tanveer.sustattendancemanager;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class CustomAdapter_attendance extends ArrayAdapter
{

	private JSONObject classDetail,temp;
	private JSONArray students;
	private Context context;
	private SqlDb db;
	private String year_id;
	private String semester_id;
	public CustomAdapter_attendance(AttendanceWindow attendanceWindow, String studentsjson, String classdetailjson, String username, String password, String dept, String year_id, String sem_id, String dept1) throws JSONException {
		super(attendanceWindow, R.layout.single_row);
		context =attendanceWindow;
		classDetail = new JSONObject(classdetailjson);
		students = new JSONArray(studentsjson);
		this.year_id=year_id;
		semester_id=sem_id;
		db =new SqlDb();
		db.open(attendanceWindow);
	}

	@Override
	public int getCount() {
		return students.length();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view =convertView;
		if(view ==null)
		{
			view =  LayoutInflater.from(context).inflate(R.layout.single_row,parent,false);
		}
		TextView reg= (TextView) view.findViewById(R.id.reg_no);
		TextView name = (TextView) view.findViewById(R.id.name);
		try {
			temp = students.getJSONObject(position);
			reg.setText(temp.getString("reg_no"));
			name.setText(temp.getString("name"));
			final TextView attendance = (TextView) view.findViewById(R.id.attendance_tick);
			final TextView absence = (TextView) view.findViewById(R.id.absence_tick);
			attendance.setTag(position);
			absence.setTag(position);

			final CheckBox late = (CheckBox) view.findViewById(R.id.late);
			late.setTag(position);
			if(temp.getString("late").equals("0"))
			{
				late.setChecked(false);
			}
			else
			{
				late.setChecked(true);
			}
			if(temp.getString("done").equals("0"))
			{
				attendance.setBackgroundResource(R.drawable.download1);
				absence.setBackgroundResource(R.drawable.untitled);

			}
			else
			{
				attendance.setBackgroundResource(R.drawable.download);
				absence.setBackgroundResource(R.drawable.untitled11);
			}
			attendance.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					try {
						temp = students.getJSONObject((Integer) attendance.getTag());
						attendance.setBackgroundResource(R.drawable.download);
						absence.setBackgroundResource(R.drawable.untitled11);
						Log.i("tanvy", "afha");
						db.execupdate(String.format("insert into data_update values(%d,'%s',%d,%d,%d,'%s',%d,%d,%d,%d,'%s','%s')", classDetail.getInt("id"), classDetail.getString("course_id"), classDetail.getInt("day"),
								classDetail.getInt("month"),
								classDetail.getInt("year"),
								temp.getString("reg_no"),
								temp.getInt("late"),
								1,
								1,
								temp.getInt("late_min"),
								year_id,
								semester_id
						));
						temp.put("success", 1);
						temp.put("done", 1);
						students.put(position, temp);
						//Log.i("tanvy",students.toString());
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});
			absence.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					attendance.setBackgroundResource(R.drawable.download1);
					absence.setBackgroundResource(R.drawable.untitled);
					late.setChecked(false);
					try {
						temp = students.getJSONObject((Integer) attendance.getTag());
						Log.i("tanvy","befor");
						attendance.setBackgroundResource(R.drawable.download1);
						absence.setBackgroundResource(R.drawable.untitled);
						Log.i("tanvy", "befor");
						db.execupdate(String.format("insert into data_update values(%d,'%s',%d,%d,%d,'%s',%d,%d,%d,%d,'%s','%s')", classDetail.getInt("id"), classDetail.getString("course_id"), classDetail.getInt("day"),
								classDetail.getInt("month"),
								classDetail.getInt("year"),
								temp.getString("reg_no"),
								0,
								0,
								0,
								0,
								year_id,
								semester_id
						));
						temp.put("success", 0);
						temp.put("done", 0);
						temp.put("late",0);
						temp.put("late_min",0);
						students.put(position,temp);
						//Log.i("tanvy",students.toString());
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});
			late.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if(isChecked)
					{
						attendance.setBackgroundResource(R.drawable.download);
						absence.setBackgroundResource(R.drawable.untitled11);
						try {
							temp = students.getJSONObject((Integer) attendance.getTag());
							attendance.setBackgroundResource(R.drawable.download);
							absence.setBackgroundResource(R.drawable.untitled11);
							db.execupdate(String.format("insert into data_update values(%d,'%s',%d,%d,%d,'%s',%d,%d,%d,%d,'%s','%s')", classDetail.getInt("id"), classDetail.getString("course_id"), classDetail.getInt("day"),
									classDetail.getInt("month"),
									classDetail.getInt("year"),
									temp.getString("reg_no"),
									1,
									1,
									1,
									5,
									year_id,
									semester_id
							));
							temp.put("success", 1);
							temp.put("done", 1);
							temp.put("late",1);
							temp.put("late_min",5);
							students.put(position, temp);
							//Log.i("tanvy",students.toString());
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					else
					{
						try {
							temp = students.getJSONObject((Integer) attendance.getTag());
							attendance.setBackgroundResource(R.drawable.download);
							absence.setBackgroundResource(R.drawable.untitled11);
							db.execupdate(String.format("insert into data_update values(%d,'%s',%d,%d,%d,'%s',%d,%d,%d,%d,'%s','%s')", classDetail.getInt("id"), classDetail.getString("course_id"), classDetail.getInt("day"),
									classDetail.getInt("month"),
									classDetail.getInt("year"),
									temp.getString("reg_no"),
									0,
									1,
									1,
									0,
									year_id,
									semester_id
							));
							temp.put("late",0);
							temp.put("late_min",0);
							temp.put("success", 1);
							temp.put("done", 1);
							students.put(position, temp);
							//Log.i("tanvy",students.toString());
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

				}
			});

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return view;
	}




}
