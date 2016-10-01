package com.example.sahil.paytm_android;

import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dilpreet on 1/10/16.
 */

public class ConfirmToServer {
	private Context context;
	private OnRequestFinished onRequestFinished;
	public ConfirmToServer(Context context,OnRequestFinished onRequestFinished) {
		this.context=context;
		this.onRequestFinished=onRequestFinished;
	}
	public void confirm(String table,String sender){
		sendRequest(table,sender,context);
	}

	private void sendRequest(final String table, final String sender, Context context){
		StringRequest request=new StringRequest(Request.Method.POST, "", new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				onRequestFinished.finish();
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {

			}
		}){
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String,String> map=new HashMap<>();
				map.put("group",table);
				map.put("phone",sender);
				return map;
			}
		};

		RequestQueue requestQueue= Volley.newRequestQueue(context);
		requestQueue.add(request);

	}

	public void initialSetUp(final String phone){
		StringRequest request=new StringRequest(Request.Method.POST, "https://paytm-hack.herokuapp.com/api/get_user/", new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				try{

					JSONObject object=new JSONObject(response);
					PreferenceManager.getDefaultSharedPreferences(context).edit()
										.putString("id",object.getString("id")).apply();

					context.startService(new Intent(context,RegistrationIntentService.class));

				}catch (JSONException e){
					Log.d("mytag",e.toString());
				}
				onRequestFinished.finish();

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
				map.put("phone",phone);
				return map;
			}
		};

		RequestQueue requestQueue= Volley.newRequestQueue(context);
		requestQueue.add(request);
	}



	public interface OnRequestFinished{
		public void finish();
	}
}
