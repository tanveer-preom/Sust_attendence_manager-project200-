package com.example.tanveer.sustattendancemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Tanveer on 6/26/2015.
 */
public class WelcomeListAdapter extends ArrayAdapter{
    private Context context;

    public WelcomeListAdapter(Context context, int resource) {
        super(context, resource);
        this.context=context;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView =inflater.inflate(R.layout.single_row_welcomelist,parent,false);
        TextView text = (TextView) convertView.findViewById(R.id.text);
        ImageView icon = (ImageView) convertView.findViewById(R.id.icon);
        if(position==0)
        {

            text.setText("Manage attendance");
            icon.setImageResource(R.drawable.ic_assignment_ind_black_36dp);

        }
        if(position==1)
        {

            text.setText("Manage my courses");
            icon.setImageResource(R.drawable.ic_assignment_black_36dp);

        }
        if(position==2)
        {

            text.setText("View and publish");
            icon.setImageResource(R.drawable.ic_get_app_black_36dp);

        }
        return convertView;
    }
}
