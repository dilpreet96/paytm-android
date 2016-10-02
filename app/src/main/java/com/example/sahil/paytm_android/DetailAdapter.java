package com.example.sahil.paytm_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dilpreet on 2/10/16.
 */

public class DetailAdapter extends BaseAdapter{
	private Context context;
	private List<String> stringList;
	private LayoutInflater inflater;
	public DetailAdapter(Context context,List<String> stringList) {
		this.context=context;
		this.stringList=stringList;
	}

	@Override
	public int getCount() {
		return stringList.size();
	}

	@Override
	public Object getItem(int i) {
		return stringList.get(i);
	}

	@Override
	public long getItemId(int i) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(R.layout.single_list_item, parent, false);

		TextView textView = (TextView) convertView.findViewById(R.id.text);
		ImageView imageView = (ImageView) convertView.findViewById(R.id.image);

		textView.setText(stringList.get(position));
		imageView.setVisibility(View.GONE);
		return convertView;
	}
}
