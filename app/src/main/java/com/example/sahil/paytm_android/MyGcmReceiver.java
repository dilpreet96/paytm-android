package com.example.sahil.paytm_android;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmReceiver;

/**
 * Created by dilpreet on 1/10/16.
 */

public class MyGcmReceiver extends GcmReceiver {

	private String msg,table,sender;
	private NotificationManager mNotificationManager;
	private Context ctx;
	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
		ctx=context;
		msg=intent.getExtras().getString("msg");
		if(intent.getExtras().containsKey("sender")) {
			table = intent.getExtras().getString("table");
			sender=intent.getExtras().getString("sender");
			sendInviteNotification(msg,table,sender);
		}

		Log.d("mytag",msg);

	}
	private void sendInviteNotification(String msg,String table,String sender) {
		mNotificationManager = (NotificationManager) ctx
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Intent intent=new Intent(ctx, MainActivity.class);
		intent.putExtra("table",table);
		intent.putExtra("sender",sender);
		PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0,intent
				, 0);

		Bitmap bitmap= BitmapFactory.decodeResource(ctx.getResources(), R.mipmap.ic_launcher);
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				ctx).setSmallIcon(R.mipmap.ic_launcher)
				.setLargeIcon(bitmap)
				.setContentTitle("Paytm")
				.setContentIntent(contentIntent)
				.setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
				.setContentText(msg)

				.setDefaults(Notification.DEFAULT_ALL);

		mBuilder.setContentIntent(contentIntent);
		mNotificationManager.notify(10, mBuilder.build());
	}

}
