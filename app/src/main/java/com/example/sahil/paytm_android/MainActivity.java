package com.example.sahil.paytm_android;

import android.preference.PreferenceManager;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    MainBaseAdapter adapter;
    ArrayList<MainModel> arr;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		ConfirmToServer confirmToServer=new ConfirmToServer(this, new ConfirmToServer.OnRequestFinished() {
			@Override
			public void finish() {

			}
		});

		if(!PreferenceManager.getDefaultSharedPreferences(this).contains("first"))
			confirmToServer.initialSetUp("9716113010");


        arr = new ArrayList<>();
        arr.add(new MainModel("family", R.mipmap.ic_launcher));
        arr.add(new MainModel("friends", R.mipmap.ic_launcher));

        listView = (ListView)findViewById(R.id.list);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        adapter = new MainBaseAdapter(this, arr);
        listView.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                if(position == 0) {
                    bundle.putString("type", "Family");
                } else if (position == 1) {
                    bundle.putString("type", "Friend");
                }
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("bundle", bundle);
                startActivity(intent);
            }
        });
    }
}
