package com.example.sahil.paytm_android;

import android.app.IntentService;
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
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dilpreet on 1/10/16.
 */

public class RegistrationIntentService extends IntentService {
	public RegistrationIntentService() {
		super("RegistrationIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		InstanceID instanceID=InstanceID.getInstance(this);
		try {
			String token = instanceID.getToken(getString(R.string.gcm_id), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
			Log.d("mytag",token);
			sendRequest(token,this);
		}catch (IOException e){
			Log.d("mytag",e.toString());
		}

		}

	private void sendRequest(final String token, final Context context){
		StringRequest request=new StringRequest(Request.Method.POST, "https://paytm-hack.herokuapp.com/api/gcm/", new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.d("mytag",response);
				PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean("first",true).apply();
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {

			}
		}){
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String,String> map=new HashMap<>();

				map.put("u_id", PreferenceManager.getDefaultSharedPreferences(context).getString("id",null));
				map.put("regid",token);
				return map;
			}
		};

		RequestQueue requestQueue=Volley.newRequestQueue(context);
		requestQueue.add(request);

	}


}
