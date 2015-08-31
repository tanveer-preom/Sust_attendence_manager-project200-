package com.example.tanveer.sustattendancemanager;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by tanveer on 8/24/15.
 */
public class addStdDialogAdapter extends ArrayAdapter implements CompoundButton.OnCheckedChangeListener{
    private JSONArray stdArray;
    private Context context;
    public addStdDialogAdapter(Context context, int resource,JSONArray stArray) {
        super(context, resource);
        this.stdArray =stArray;
        this.context=context;
        Log.i("tanvy",stdArray.toString());
    }

    @Override
    public int getCount() {
        return stdArray.length();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.session_select,parent,false);
        }
        try {
            JSONObject temp = stdArray.getJSONObject(position);
            CheckBox chk = (CheckBox) convertView.findViewById(R.id.stdChecked);
            Log.i("tanvy", temp.toString());
            chk.setText(temp.getString("reg_no"));
            chk.setTag(position);
            chk.setOnCheckedChangeListener(this);
            if(temp.getString("isChecked")!=null) {
                if (temp.getString("isChecked").equals("1")) {
                    chk.setChecked(true);
                }
                else
                    chk.setChecked(false);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return convertView;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        try {
            int pos =(Integer) buttonView.getTag();
            JSONObject temp = stdArray.getJSONObject((Integer) buttonView.getTag());
            temp.put("isChecked","1");
            stdArray.put(pos,temp);
            Log.i("tanvy","hello");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
