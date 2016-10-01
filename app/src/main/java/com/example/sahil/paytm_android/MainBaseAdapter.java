package com.example.sahil.paytm_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sahil on 1/10/16.
 */

public class MainBaseAdapter extends BaseAdapter {

    LayoutInflater inflater;
    Context context;
    ArrayList<MainModel> arr;

    public MainBaseAdapter(Context context, ArrayList<MainModel> arr) {
        super();
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.arr = arr;
    }

    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public Object getItem(int position) {
        return arr.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.single_list_item, parent, false);

        TextView textView = (TextView) convertView.findViewById(R.id.text);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.image);

        textView.setText(arr.get(position).name);
        imageView.setImageResource(arr.get(position).id);

        return convertView;
    }
}
