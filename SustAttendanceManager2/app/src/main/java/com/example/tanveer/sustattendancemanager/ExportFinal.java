package com.example.tanveer.sustattendancemanager;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class ExportFinal extends Activity {
	private String username,password,courseId,yearId,semId,dept;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.export_result);
		username =getIntent().getExtras().getString("user");
		password =getIntent().getExtras().getString("pass");
		courseId =getIntent().getExtras().getString("course_id");
		yearId=getIntent().getExtras().getString("year_id");
		semId=getIntent().getExtras().getString("semester_id");
		dept=getIntent().getExtras().getString("dept");
		TextView textView = (TextView) findViewById(R.id.course_id);
		textView.setText("Course Id="+courseId+"\nYear Id="+yearId+"\nSemester Id ="+semId+"\nDepartment="+dept);

	}
	public void onclick(View v)
	{


		EditText lateConsider = (EditText) findViewById(R.id.consideration_time);
		String time =lateConsider.getText().toString();
		DownloadManager.Request r = new DownloadManager.Request(Uri.parse(StaticDatas.uri+"/PublishAttendance?course_id="+courseId+"&year_id="+yearId+"&semester_id="+semId+"&dept="+dept+"&user="+username+"&pass="+password+"&considered_time="+time));

// This put the download in the same Download dir the browser uses
				r.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Result.pdf");

// When downloading music and videos they will be listed in the player
// (Seems to be available since Honeycomb only)
				r.allowScanningByMediaScanner();

// Notify user when download is completed
// (Seems to be available since Honeycomb only)
				r.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

// Start download
				DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
				dm.enqueue(r);



	}

}
