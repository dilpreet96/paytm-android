package com.example.sahil.paytm_android;

import android.content.DialogInterface;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {

    String url = "http://paytm-hack.herokuapp.com/api/list_user/";
    Bundle bundle;
    String type;
	private List<String> stringList;
	private DetailAdapter adapter;
	FloatingActionButton fab;
	GridView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
		listView=(GridView)findViewById(R.id.detail_list);
        type = getIntent().getStringExtra("type");
		Log.d("mytag",type);
		stringList=new ArrayList<>();
		adapter=new DetailAdapter(this,stringList);
		listView.setAdapter(adapter);
        final StringRequest request;


            request = new StringRequest(Request.Method.POST,url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
								JSONArray jsonArray=new JSONArray(response);
								JSONObject object=null;
								for(int i=0;i<jsonArray.length();i++){
									object=jsonArray.getJSONObject(i);
									stringList.add(object.getString("phone"));
									Log.d("mytag",object.getString("phone"));

								}

								adapter.notifyDataSetChanged();


							}catch ( JSONException e){
								Log.d("mytag",e.toString());
							}
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("test", "error in getting users");
                        }
                    }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("group", type);
                    return map;
                }
            };
			RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
			queue.add(request);


		fab = (FloatingActionButton) findViewById(R.id.fab);

		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				createDialog();
			}
		});


	}

	private void createDialog(){
		AlertDialog.Builder builder=new AlertDialog.Builder(DetailActivity.this);
		final EditText editText=new EditText(getApplicationContext());
		LinearLayout.LayoutParams layoutParams=new AppBarLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		editText.setLayoutParams(layoutParams);
		builder.setView(editText);
		builder.setTitle("Enter phone number to invite");
		builder.setPositiveButton("INVITE", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				sendINvite(editText.getText().toString());
			}
		});
		builder.show();
	}


	private void sendINvite(final String receiverNumber){
		StringRequest request=new StringRequest(Request.Method.POST, "http://paytm-hack.herokuapp.com/api/api/invite/", new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {


			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.d("mytag",error.toString());
			}
		}){
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String,String> map=new HashMap<>();
				map.put("u_id", PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("id",null));
				map.put("group",type);
				map.put("receiver_phone",receiverNumber);
				return map;

			}
		};
		RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
		requestQueue.add(request);
	}
}
