package com.example.sahil.paytm_android;

import android.app.VoiceInteractor;
import android.content.Context;
import android.content.DialogInterface;
import android.preference.PreferenceManager;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

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
 * Created by sahil on 2/10/16.
 */

public class ApiRequests {

    Context context;
    String balanceUrl = "https://pguat.paytm.com/oltp/HANDLER_INTERNAL/checkBalance?JsonData={\"TOKEN\":\"%s\",\"MID\":\"PaySUB57211212672224\"}";
    String sendOtpUrl = "";
    String sendmoneyUrl = "https://paytm-hack.herokuapp.com/api/send_money/";
    String token, id, owner, mobile;

    public ApiRequests(Context context) {
        this.context = context;
//        token = PreferenceManager.getDefaultSharedPreferences(context).getString("access", "");
        token = "97c2cb90-6515-41bd-a013-9ad3919e11bc";
        id = PreferenceManager.getDefaultSharedPreferences(context).getString("id", "");
        owner = PreferenceManager.getDefaultSharedPreferences(context).getString("owner", "");
        mobile = PreferenceManager.getDefaultSharedPreferences(context).getString("mobile", "");
    }

    public void checkBalance() {
        String url = String.format(balanceUrl, token);

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("test", response);
                        // shit to do
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("test", "" + error);
                    }
                });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

    public void sendMoney(final String giver, final String taker, final String amount) {

            StringRequest request = new StringRequest(Request.Method.POST, sendmoneyUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.length() > 0) {
                                final String res = response;
                                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                                final EditText editText=new EditText(context);
                                LinearLayout.LayoutParams layoutParams=new AppBarLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                editText.setLayoutParams(layoutParams);
                                builder.setView(editText);
                                builder.setTitle("Enter the otp sent to you");
                                builder.setCancelable(false);
                                builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        sendOtp(res, editText.getText().toString(), mobile);
                                    }
                                });
                                builder.show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("test", "error in django money url");
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("taker", taker);
                    map.put("giver", giver);
                    map.put("amount", amount);
                    return map;
                }
            };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
//        JSONObject inner = new JSONObject();
//        final JSONObject outer = new JSONObject();
//        try {
//            inner.put("payeePhoneNumber", payeeMobile);
//            inner.put("amount", amount);
//            inner.put("payeeEmailId", "");
//            inner.put("payeeSsoId", id);
//            inner.put("currencyCode", "INR");
//            inner.put("isToVerify", "1");
//            inner.put("isLimitApplicable", "1");
//            inner.put("comment", "test");
//
//            outer.put("request", inner);
//            outer.put("ipAddress", "127.0.0.1");
//            outer.put( "platformName", "PayTM");
//            outer.put("operationType", "P2P_TRANSFER");
//            outer.put("channel", "");
//            outer.put("version", "");
//
//            Log.e("test", "" + outer);
//        } catch (JSONException e) {
//            Log.e("test", "error in creating json");
//        }

//        StringRequest request = new StringRequest(Request.Method.POST, sendmoneyUrl,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.d("test", "onResponse: " + response);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.e("test", "error in sending money");
//                    }
//                })
//        {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> map = new HashMap<>();
//                map.put("ssotoken", token);
//                return map;
//            }
//
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                JSONObject inner = new JSONObject();
//                HashMap<String, String> outer = new HashMap<>();
//                try {
//                    inner.put("payeePhoneNumber", payeeMobile);
//                    inner.put("amount", amount);
//                    inner.put("payeeEmailId", "");
//                    inner.put("payeeSsoId", id);
//                    inner.put("currencyCode", "INR");
//                    inner.put("isToVerify", "1");
//                    inner.put("isLimitApplicable", "1");
//                    inner.put("comment", "test");
//
//                    outer.put("request", inner.toString());
//                    outer.put("ipAddress", "127.0.0.1");
//                    outer.put( "platformName", "PayTM");
//                    outer.put("operationType", "P2P_TRANSFER");
//                    outer.put("channel", "");
//                    outer.put("version", "");
//
//                    Log.e("test", "" + outer);
//                } catch (JSONException e) {
//                    Log.e("test", "error in creating json");
//                }
//                return outer;
//            }
//        };
//
//        RequestQueue queue = Volley.newRequestQueue(context);
//        queue.add(request);
    }

    public void sendOtp(final String state,final String otp,final String giver) {
        StringRequest request = new StringRequest(Request.Method.POST, sendOtpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.compareToIgnoreCase("ok") == 0) {
                            Toast.makeText(context, "money sent successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("state", state);
                map.put("otp", otp);
                map.put("giver", giver);
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }
}
