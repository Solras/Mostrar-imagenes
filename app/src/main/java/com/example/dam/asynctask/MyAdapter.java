package com.example.dam.asynctask;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dam.dosactividades.R;

import java.util.ArrayList;
import java.util.Collections;

public class MyAdapter extends ArrayAdapter<String> {

    private Context ctx;
    private int res;
    private LayoutInflater lInflator;
    private ArrayList<String> values;

    static class ViewHolder {
        TextView tv;
    }

    public MyAdapter(Context context, ArrayList<String> objects) {
        super(context, R.layout.list_item, objects);
        this.ctx = context;
        this.res = R.layout.list_item;
        this.lInflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.values = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = new ViewHolder();

        if (convertView == null) {
            convertView = lInflator.inflate(res, null);
            TextView tv = (TextView) convertView.findViewById(R.id.textView);
            vh.tv = tv;

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        vh.tv.setText(values.get(position));

        return convertView;
    }
}
