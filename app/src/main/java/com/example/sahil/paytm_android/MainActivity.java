package com.example.sahil.paytm_android;

import android.app.Dialog;
import android.content.DialogInterface;
import android.preference.PreferenceManager;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

				Intent intent = new Intent(MainActivity.this, DetailActivity.class);

				if(position == 0) {
					intent.putExtra("type", "Family");
				} else if (position == 1) {
					intent.putExtra("type", "Friend");
				}
				startActivity(intent);
            }
        });
		try {
			checkWhetherInvite();
		}catch (NullPointerException e){

		}
	}

	private void checkWhetherInvite() throws NullPointerException{

		if(getIntent().getExtras().containsKey("table")){

			AlertDialog.Builder alertDialog=new AlertDialog.Builder(MainActivity.this);
			alertDialog.setMessage("Do you want to accept the invite by "+getIntent().getExtras().getString("sender"));
			alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i) {
					confirmInvite(getIntent().getExtras().getString("sender"),getIntent().getExtras().getString("table"));
				}
			});
			alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i) {

				}
			});
			alertDialog.show();
		}
	}

	private void confirmInvite(String sender,final String table){
		StringRequest request=new StringRequest(Request.Method.POST, "https://paytm-hack.herokuapp.com/api/confirm/", new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				try{

					JSONObject object=new JSONObject(response);
					PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit()
							.putString("id",object.getString("id")).apply();

					startService(new Intent(getApplicationContext(),RegistrationIntentService.class));

				}catch (JSONException e){
					Log.d("mytag","JSON "+e.toString());
				}


			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.d("mytag","confirm "+error.toString());
			}
		}){
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String,String> map=new HashMap<>();
				map.put("uid", PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("id",null));
				map.put("group",table);
				return map;
			}
		};
		RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
		requestQueue.add(request);
	}
}
