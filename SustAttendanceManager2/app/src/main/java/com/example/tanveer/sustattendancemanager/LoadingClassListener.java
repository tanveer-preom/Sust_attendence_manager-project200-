package com.example.tanveer.sustattendancemanager;

import android.app.Activity;

interface LoadingClassListener {
	public Activity getContext();
	public void onPostExecuted(String jsonresult);
}
