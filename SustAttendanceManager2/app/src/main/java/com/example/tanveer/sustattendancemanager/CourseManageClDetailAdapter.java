package com.example.tanveer.sustattendancemanager;

import android.app.Dialog;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by tanveer on 8/22/15.
 */
public class CourseManageClDetailAdapter extends ArrayAdapter implements View.OnClickListener{
    private Context context;
    private JSONArray array;

    public CourseManageClDetailAdapter(Context context, int resource,String student) {
        super(context, resource);
        this.context =context;
        try {
            array =new JSONArray(student);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        return array.length();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView=LayoutInflater.from(context).inflate(R.layout.single_row_manage_cl,parent,false);

        }
        Button change = (Button) convertView.findViewById(R.id.change);
        change.setTag(position);
        change.setOnClickListener(this);

        try {
            JSONObject temp = array.getJSONObject(position);
            String reg_no = temp.getString("reg_no");
            String late =temp.getString("late_min");
            String success =temp.getString("success");
            String done =temp.getString("done");
            String processed="";
            TextView reg = (TextView) convertView.findViewById(R.id.reg_no);
            if(success.equals("1"))
            {
                processed="<font color=#004d40>Present=Yes</font><br>";
            }
            else
            {
                processed="<font color=#004d40>Present=No</font><br>";
            }
            processed+=String.format("<font color=#d32f2f>Late=%smin</font><br>",late);
            if(done.equals("1"))
            {
                processed+="<font color=#7e57c2>PTE=Yes</font>";
            }
            else
            {
                processed+="<font color=#7e57c2>PTE=No</font>";
            }
            reg.setText(reg_no);
            TextView details = (TextView) convertView.findViewById(R.id.present);
            details.setText(Html.fromHtml(processed));


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return convertView;
    }

    @Override
    public void onClick(View v) {
        final int pos= (int) v.getTag();
        try {
            final JSONObject temp = array.getJSONObject(pos);
            final Dialog dialog =new Dialog(context);
            dialog.setTitle(Html.fromHtml("<font color=#00b0ff>Edit</font>"));
            dialog.setContentView(R.layout.dialog_change_course_manage_cl);
            TextView name = (TextView) dialog.findViewById(R.id.name);
            TextView reg_no = (TextView) dialog.findViewById(R.id.reg_no);
            final EditText late = (EditText) dialog.findViewById(R.id.late);
            final CheckBox present = (CheckBox) dialog.findViewById(R.id.present);
            final CheckBox pte = (CheckBox) dialog.findViewById(R.id.pte);
            Button confirm = (Button) dialog.findViewById(R.id.confirm);
            Button cancel = (Button) dialog.findViewById(R.id.cancel);
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SqlDb db =new SqlDb();
                    String p,l,done;
                    db.open(context);
                    if(present.isChecked())
                       p = "1";
                    else
                        p="0";
                    l=late.getText().toString();
                    if(pte.isChecked())
                    done ="1";
                    else
                    done="0";
                    try {
                        temp.put("late_min",l);
                        temp.put("success",p);
                        temp.put("done",done);
                        array.put(pos,temp);
                        notifyDataSetChanged();
                        db.execupdate(String.format("insert into data_update values(%s,'%s',%s,%s,%s,'%s',%s,%s,%s,%s,'%s','%s')", temp.getInt("id"), temp.getString("course_id"), temp.getInt("day"),
                                temp.getString("month"),
                                temp.getString("year"),
                                temp.getString("reg_no"),
                                temp.getString("late"),
                                temp.getString("success"),
                                temp.getString("done"),
                                temp.getString("late_min"),
                                temp.getString("year_id"),
                                temp.getString("semester_id")
                        ));
                        dialog.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            name.setText("Name : "+temp.getString("name"));
            reg_no.setText("Reg-no : "+temp.getString("reg_no"));
            late.setText(temp.getString("late_min"));
            if(temp.getString("success").equals("1"))
                present.setChecked(true);
            else
                present.setChecked(false);
            if(temp.getString("done").equals("1"))
                pte.setChecked(true);
            else
                pte.setChecked(false);
            dialog.show();


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
