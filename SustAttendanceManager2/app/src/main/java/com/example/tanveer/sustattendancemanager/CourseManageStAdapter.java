package com.example.tanveer.sustattendancemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by tanveer on 8/18/15.
 */
public class CourseManageStAdapter extends ArrayAdapter {
    private JSONArray students;
    private Context context;
    public CourseManageStAdapter(Context context,JSONArray arr) {
        super(context, R.layout.singe_row_frag_st);
        students =arr;
        this.context=context;
    }

    @Override
    public int getCount() {
        return students.length();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.singe_row_frag_st,parent,false);

        }
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView reg = (TextView) convertView.findViewById(R.id.reg_no);
        JSONObject temp = null;
        try {
            temp = students.getJSONObject(position);
            name.setText(temp.getString("name"));
            reg.setText(temp.getString("reg_no"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return convertView;
    }
}
