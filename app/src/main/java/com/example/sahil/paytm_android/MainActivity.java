package com.example.sahil.paytm_android;

import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

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

    }
}
