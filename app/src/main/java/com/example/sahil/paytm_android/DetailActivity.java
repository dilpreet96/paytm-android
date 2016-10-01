package com.example.sahil.paytm_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {

    String url = "paytm-hack/api/list_user";
    Bundle bundle;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        bundle = getIntent().getBundleExtra("bundle");
        type = bundle.getString("type");

        StringRequest request;

        if(type.compareToIgnoreCase("Family") == 0) {
            request = new StringRequest(url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("test", "" + response);
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
        }
    }
}
