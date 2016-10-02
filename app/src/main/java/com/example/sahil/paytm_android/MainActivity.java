package com.example.sahil.paytm_android;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;
import com.paytm.merchant.CheckSumServiceHelper;

import java.util.ArrayList;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    MainBaseAdapter adapter;
    ArrayList<MainModel> arr;
    FloatingActionButton fab;
    String checkSum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Paytm");

		ConfirmToServer confirmToServer=new ConfirmToServer(this, new ConfirmToServer.OnRequestFinished() {
			@Override
			public void finish() {

			}
		});

		if(!PreferenceManager.getDefaultSharedPreferences(this).contains("first"))
			confirmToServer.initialSetUp("9711961486");


//        CheckSumServiceHelper checkSumServiceHelper = CheckSumServiceHelper.getCheckSumServiceHelper();
//        TreeMap<String, String> parameters = new TreeMap<String, String>();
//        String merchantKey = "kbzk1DSbJiV_O3p5";
//        parameters.put("MID", "WorldP64425807474247"); // Merchant ID (MID) provided by Paytm
//        parameters.put("ORDER_ID", "abcd"); // Merchant’s order id
//        parameters.put("CUST_ID", "defg"); // Customer ID registered with merchant
//        parameters.put("TXN_AMOUNT", "10");
//        parameters.put("CHANNEL_ID", "WEB");
//        parameters.put("INDUSTRY_TYPE_ID", "Retail"); //Provided by Paytm
//        parameters.put("WEBSITE", "worldpressplgOAuth"); //Provided by Paytm
//
//        try {
//            checkSum = checkSumServiceHelper.genrateCheckSum(merchantKey, parameters);
//            Log.e("test", checkSum);
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e("test", "problem with checksum");
//        }

        arr = new ArrayList<>();
        arr.add(new MainModel("family", R.drawable.family));
        arr.add(new MainModel("friends", R.drawable.friends));
        arr.add(new MainModel("school", R.drawable.family));
        arr.add(new MainModel("office", R.drawable.friends));
        arr.add(new MainModel("society", R.drawable.family));
        arr.add(new MainModel("college", R.drawable.friends));

        listView = (ListView)findViewById(R.id.list);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        adapter = new MainBaseAdapter(this, arr);
        listView.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "" + checkSum, Toast.LENGTH_SHORT).show();
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
