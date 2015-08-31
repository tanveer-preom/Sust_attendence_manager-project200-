package com.example.tanveer.sustattendancemanager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class WelcomeWindow extends ActionBarActivity implements AdapterView.OnItemClickListener{

	private String jsonstring,name,userid,profession,department,password;
	private JArrayIndex parsedresult;
	private Dialog courseSelector;
	private String years[]={"2010","2011","2012","2013","2014","2015","2016","2017","2018","2019","2020","2021","2022","2023","2024"};
	private String semester[]={"1","2"};
	private JSONArray departments;
	private ArrayList<String> dialogDeptName,dialogDeptCode;
	private Spinner dialogdeptSpin,dialogYearSpin,dialogSemSpin;
	private String currentYear,currentSem,currentDept,currentCourses;
	//private TextView username;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_window);
		currentCourses="";
		jsonstring=getIntent().getExtras().getString("welcome");
		initUi();
		welcomeAlertDialog();

		//Log.i("Tanvy", "aftha");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		StaticDatas.apprunning=false;
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
		if (id == R.id.action_session) {
			parsedresult.setJObjectPosition(1);
			dialogDeptName = new ArrayList<>();
			dialogDeptCode = new ArrayList<>();
			//dialogDeptName.add("Any");
			//dialogDeptCode.add("Any");
			try {
				departments = new JSONArray(parsedresult.getValue("Courses"));
				for (int i = 0; i < departments.length(); i++) {
					JSONObject currentObj = departments.getJSONObject(i);
					dialogDeptName.add(currentObj.getString("dept_name"));
					dialogDeptCode.add(currentObj.getString("accepting_dept"));
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
			courseSelector = new Dialog(WelcomeWindow.this);
			courseSelector.setTitle("Course details");
			courseSelector.setContentView(R.layout.course_selector);
			courseSelector.show();
			dialogdeptSpin = (Spinner) courseSelector.findViewById(R.id.depts);
			ArrayAdapter<String> deptAdapter = new ArrayAdapter<String>(WelcomeWindow.this, android.R.layout.simple_spinner_item, dialogDeptName);
			deptAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			dialogdeptSpin.setAdapter(deptAdapter);
			dialogYearSpin = (Spinner) courseSelector.findViewById(R.id.year);
			dialogYearSpin.setAdapter(new ArrayAdapter<>(WelcomeWindow.this, android.R.layout.simple_spinner_item, years));
			dialogSemSpin = (Spinner) courseSelector.findViewById(R.id.semester);
			dialogSemSpin.setAdapter(new ArrayAdapter<>(WelcomeWindow.this, android.R.layout.simple_spinner_item, semester));
			Button ok = (Button) courseSelector.findViewById(R.id.ok);
			final Button cancel = (Button) courseSelector.findViewById(R.id.cancel);
			ok.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					currentYear = years[dialogYearSpin.getSelectedItemPosition()];
					currentSem = semester[dialogSemSpin.getSelectedItemPosition()];
					currentDept = dialogDeptCode.get(dialogdeptSpin.getSelectedItemPosition());
					final String deptName=dialogdeptSpin.getSelectedItem().toString();
					courseSelector.dismiss();
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							setTitle("" + deptName + " " + currentYear + "/" + currentSem);
							new LoadingClass(StaticDatas.uri + "/Courses?user=" + userid + "&pass=" + password + "&year=" + currentYear + "&semester=" + currentSem + "&dept=" + currentDept, new LoadingClassListener() {
								@Override
								public Activity getContext() {
									return WelcomeWindow.this;
								}

								@Override
								public void onPostExecuted(String jsonresult) {
									try {

										currentCourses=jsonresult;


									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							}).execute();

						}
					});

			};

		});
			cancel.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					courseSelector.dismiss();
				}
			});

		return true;
	}
		return super.onOptionsItemSelected(item);
	}


	void initUi()
	{
		ListView list = (ListView) findViewById(R.id.buttonlist);
		ImageView propic = (ImageView) findViewById(R.id.profilepic);
		list.setAdapter(new WelcomeListAdapter(WelcomeWindow.this, R.layout.single_row_welcomelist));
		list.setOnItemClickListener(this);
		
	}
	public void logout(View v)
	{
		Intent goback =new Intent(this,MainActivity.class);
		startActivity(goback);
		finish();
	}

	
	private void welcomeAlertDialog()
	{
		try {
			parsedresult= new JArrayIndex(jsonstring).setJObjectPosition(0);
			profession="Professor";
			userid=parsedresult.getValue("inst_id");
			password=parsedresult.getValue("pass");
			name=parsedresult.getValue("name");
			department=parsedresult.getValue("dept_name");
			viewAlertDialog(profession,department,name);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	@SuppressWarnings("deprecation")
	private void viewAlertDialog(String profession,String dept,String name)
	{
	
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Welcome "+profession+"\n"+name+"\n\n"+"Department of "+dept+"\n")
		       .setTitle("Department of "+dept);
		AlertDialog dialog = builder.create();
		DialogInterface.OnClickListener listener =new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	               // User cancelled the dialog
	        	   dialog.dismiss();
	        	   
	           }
		};
		dialog.setButton("Ok", listener);
		
		

		dialog.show();
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if(position==0) {
			if (currentDept==null || currentSem==null || currentYear==null)
			{
				parsedresult.setJObjectPosition(1);
				dialogDeptName = new ArrayList<>();
				dialogDeptCode = new ArrayList<>();
			//dialogDeptName.add("Any");
			//dialogDeptCode.add("Any");
			try {
				departments = new JSONArray(parsedresult.getValue("Courses"));
				for (int i = 0; i < departments.length(); i++) {
					JSONObject currentObj = departments.getJSONObject(i);
					dialogDeptName.add(currentObj.getString("dept_name"));
					dialogDeptCode.add(currentObj.getString("accepting_dept"));
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
			courseSelector = new Dialog(WelcomeWindow.this);
			courseSelector.setTitle("Course details");
			courseSelector.setContentView(R.layout.course_selector);
			courseSelector.show();
			dialogdeptSpin = (Spinner) courseSelector.findViewById(R.id.depts);
			ArrayAdapter<String> deptAdapter = new ArrayAdapter<String>(WelcomeWindow.this, android.R.layout.simple_spinner_item, dialogDeptName);
			deptAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			dialogdeptSpin.setAdapter(deptAdapter);
			dialogYearSpin = (Spinner) courseSelector.findViewById(R.id.year);
			dialogYearSpin.setAdapter(new ArrayAdapter<>(WelcomeWindow.this, android.R.layout.simple_spinner_item, years));
			dialogSemSpin = (Spinner) courseSelector.findViewById(R.id.semester);
			dialogSemSpin.setAdapter(new ArrayAdapter<>(WelcomeWindow.this, android.R.layout.simple_spinner_item, semester));
			Button ok = (Button) courseSelector.findViewById(R.id.ok);
			Button cancel = (Button) courseSelector.findViewById(R.id.cancel);
			ok.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					currentYear = years[dialogYearSpin.getSelectedItemPosition()];
					currentSem = semester[dialogSemSpin.getSelectedItemPosition()];
					currentDept = dialogDeptCode.get(dialogdeptSpin.getSelectedItemPosition());
					courseSelector.dismiss();
					Log.i("tanvy", StaticDatas.uri + "/Courses?user=" + userid + "&pass=" + password + "&year=" + currentYear + "&semester=" + currentSem + "&dept=" + currentDept);
					new LoadingClass(StaticDatas.uri + "/Courses?user=" + userid + "&pass=" + password + "&year=" + currentYear + "&semester=" + currentSem + "&dept=" + currentDept, new LoadingClassListener() {
						@Override
						public Activity getContext() {
							return WelcomeWindow.this;
						}

						@Override
						public void onPostExecuted(String jsonresult) {
							try {
								new JArrayIndex(jsonresult);

								final Intent i = new Intent(WelcomeWindow.this, AttendanceActivity.class);
								i.putExtra("user", userid);
								i.putExtra("pass", password);
								i.putExtra("courses", jsonresult);
								i.putExtra("year_id", currentYear);
								i.putExtra("sem_id", currentSem);
								i.putExtra("dept",currentDept);
								currentCourses= jsonresult;
								//courseSelector.dismiss();
								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										setTitle("" + currentDept + " " + currentYear + "/" + currentSem);
										startActivity(i);

									}
								});


							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					}).execute();

				}
			});
			cancel.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					courseSelector.dismiss();
				}
			});
		}
			else
			{
				Intent i = new Intent(WelcomeWindow.this, AttendanceActivity.class);
				i.putExtra("user", userid);
				i.putExtra("pass", password);
				i.putExtra("courses", currentCourses);
				i.putExtra("year_id", currentYear);
				i.putExtra("sem_id", currentSem);
				i.putExtra("dept",currentDept);
				startActivity(i);

			}
		}
		else if(position==1) {
			if (currentDept == null || currentSem == null || currentYear == null) {
				parsedresult.setJObjectPosition(1);
				dialogDeptName = new ArrayList<>();
				dialogDeptCode = new ArrayList<>();
				//dialogDeptName.add("Any");
				//dialogDeptCode.add("Any");
				try {
					departments = new JSONArray(parsedresult.getValue("Courses"));
					for (int i = 0; i < departments.length(); i++) {
						JSONObject currentObj = departments.getJSONObject(i);
						dialogDeptName.add(currentObj.getString("dept_name"));
						dialogDeptCode.add(currentObj.getString("accepting_dept"));
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
				courseSelector = new Dialog(WelcomeWindow.this);
				courseSelector.setTitle("Course details");
				courseSelector.setContentView(R.layout.course_selector);
				courseSelector.show();
				dialogdeptSpin = (Spinner) courseSelector.findViewById(R.id.depts);
				ArrayAdapter<String> deptAdapter = new ArrayAdapter<String>(WelcomeWindow.this, android.R.layout.simple_spinner_item, dialogDeptName);
				deptAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				dialogdeptSpin.setAdapter(deptAdapter);
				dialogYearSpin = (Spinner) courseSelector.findViewById(R.id.year);
				dialogYearSpin.setAdapter(new ArrayAdapter<>(WelcomeWindow.this, android.R.layout.simple_spinner_item, years));
				dialogSemSpin = (Spinner) courseSelector.findViewById(R.id.semester);
				dialogSemSpin.setAdapter(new ArrayAdapter<>(WelcomeWindow.this, android.R.layout.simple_spinner_item, semester));
				Button ok = (Button) courseSelector.findViewById(R.id.ok);
				Button cancel = (Button) courseSelector.findViewById(R.id.cancel);
				ok.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						currentYear = years[dialogYearSpin.getSelectedItemPosition()];
						currentSem = semester[dialogSemSpin.getSelectedItemPosition()];
						currentDept = dialogDeptCode.get(dialogdeptSpin.getSelectedItemPosition());
						courseSelector.dismiss();
						Log.i("tanvy", StaticDatas.uri + "/Courses?user=" + userid + "&pass=" + password + "&year=" + currentYear + "&semester=" + currentSem + "&dept=" + currentDept);
						new LoadingClass(StaticDatas.uri + "/Courses?user=" + userid + "&pass=" + password + "&year=" + currentYear + "&semester=" + currentSem + "&dept=" + currentDept, new LoadingClassListener() {
							@Override
							public Activity getContext() {
								return WelcomeWindow.this;
							}

							@Override
							public void onPostExecuted(String jsonresult) {
								try {
									new JArrayIndex(jsonresult);

									final Intent i = new Intent(WelcomeWindow.this, CourseActivity.class);
									i.putExtra("user", userid);
									i.putExtra("pass", password);
									i.putExtra("courses", jsonresult);
									i.putExtra("year_id", currentYear);
									i.putExtra("sem_id", currentSem);
									i.putExtra("dept",currentDept);
									currentCourses=jsonresult;
									//courseSelector.dismiss();
									Log.i("tanvy", jsonresult);
									runOnUiThread(new Runnable() {
										@Override
										public void run() {
											setTitle("" + currentDept + " " + currentYear + "/" + currentSem);
											startActivity(i);

										}
									});


								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
						}).execute();

					}
				});
				cancel.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						courseSelector.dismiss();
					}
				});

			/*
			*/
			}
			else
			{
				Intent i = new Intent(WelcomeWindow.this, CourseActivity.class);
				i.putExtra("user", userid);
				i.putExtra("pass", password);
				i.putExtra("courses", currentCourses);
				i.putExtra("year_id", currentYear);
				i.putExtra("sem_id", currentSem);
				i.putExtra("dept",currentDept);
				startActivity(i);
			}

		}
		else
		{
			if (currentDept == null || currentSem == null || currentYear == null) {
				parsedresult.setJObjectPosition(1);
				dialogDeptName = new ArrayList<>();
				dialogDeptCode = new ArrayList<>();
				//dialogDeptName.add("Any");
				//dialogDeptCode.add("Any");
				try {
					departments = new JSONArray(parsedresult.getValue("Courses"));
					for (int i = 0; i < departments.length(); i++) {
						JSONObject currentObj = departments.getJSONObject(i);
						dialogDeptName.add(currentObj.getString("dept_name"));
						dialogDeptCode.add(currentObj.getString("accepting_dept"));
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
				courseSelector = new Dialog(WelcomeWindow.this);
				courseSelector.setTitle("Course details");
				courseSelector.setContentView(R.layout.course_selector);
				courseSelector.show();
				dialogdeptSpin = (Spinner) courseSelector.findViewById(R.id.depts);
				ArrayAdapter<String> deptAdapter = new ArrayAdapter<String>(WelcomeWindow.this, android.R.layout.simple_spinner_item, dialogDeptName);
				deptAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				dialogdeptSpin.setAdapter(deptAdapter);
				dialogYearSpin = (Spinner) courseSelector.findViewById(R.id.year);
				dialogYearSpin.setAdapter(new ArrayAdapter<>(WelcomeWindow.this, android.R.layout.simple_spinner_item, years));
				dialogSemSpin = (Spinner) courseSelector.findViewById(R.id.semester);
				dialogSemSpin.setAdapter(new ArrayAdapter<>(WelcomeWindow.this, android.R.layout.simple_spinner_item, semester));
				Button ok = (Button) courseSelector.findViewById(R.id.ok);
				Button cancel = (Button) courseSelector.findViewById(R.id.cancel);
				ok.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						currentYear = years[dialogYearSpin.getSelectedItemPosition()];
						currentSem = semester[dialogSemSpin.getSelectedItemPosition()];
						currentDept = dialogDeptCode.get(dialogdeptSpin.getSelectedItemPosition());
						courseSelector.dismiss();
						Log.i("tanvy", StaticDatas.uri + "/Courses?user=" + userid + "&pass=" + password + "&year=" + currentYear + "&semester=" + currentSem + "&dept=" + currentDept);
						new LoadingClass(StaticDatas.uri + "/Courses?user=" + userid + "&pass=" + password + "&year=" + currentYear + "&semester=" + currentSem + "&dept=" + currentDept, new LoadingClassListener() {
							@Override
							public Activity getContext() {
								return WelcomeWindow.this;
							}

							@Override
							public void onPostExecuted(String jsonresult) {
								try {
									new JArrayIndex(jsonresult);

									final Intent i = new Intent(WelcomeWindow.this, ExportMain.class);
									i.putExtra("user", userid);
									i.putExtra("pass", password);
									i.putExtra("courses", jsonresult);
									i.putExtra("year_id", currentYear);
									i.putExtra("sem_id", currentSem);
									i.putExtra("dept",currentDept);
									currentCourses=jsonresult;
									//courseSelector.dismiss();
									Log.i("tanvy", jsonresult);
									runOnUiThread(new Runnable() {
										@Override
										public void run() {
											setTitle("" + currentDept + " " + currentYear + "/" + currentSem);
											startActivity(i);

										}
									});


								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
						}).execute();

					}
				});
				cancel.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						courseSelector.dismiss();
					}
				});

			/*
			*/
			}
			else
			{
				Intent i = new Intent(WelcomeWindow.this, ExportMain.class);
				i.putExtra("user", userid);
				i.putExtra("pass", password);
				i.putExtra("courses", currentCourses);
				i.putExtra("year_id", currentYear);
				i.putExtra("sem_id", currentSem);
				i.putExtra("dept",currentDept);
				startActivity(i);
			}

		}
	}
}
