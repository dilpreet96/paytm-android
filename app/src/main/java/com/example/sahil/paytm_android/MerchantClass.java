package com.example.sahil.paytm_android;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.paytm.pgsdk.PaytmMerchant;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by dilpreet on 2/10/16.
 */

public class MerchantClass {
	Context context;
	public MerchantClass(Context context) {
		this.context=context;
		startPayment();
	}

	private String initOrderId() {
		Random r = new Random(System.currentTimeMillis());
		String orderId = "ORDER" + (1 + r.nextInt(2)) * 10000
				+ r.nextInt(10000);
		return orderId;
	}

	private void startPayment(){
		PaytmPGService Service = PaytmPGService.getStagingService();
		Map<String, String> paramMap = new HashMap<String, String>();

		// these are mandatory parameters

		paramMap.put("ORDER_ID",initOrderId() );
		paramMap.put("MID", "PayGRA72033364648247");
		paramMap.put("CUST_ID", "CUST110");
		paramMap.put("CHANNEL_ID", "WEB");
		paramMap.put("INDUSTRY_TYPE_ID", "Retail");
		paramMap.put("WEBSITE", "Gratification");
		paramMap.put("TXN_AMOUNT", "1000");
		paramMap.put("THEME", "merchant");
		paramMap.put("MOBILE_NO", "9716113010");
		PaytmOrder Order = new PaytmOrder(paramMap);

		PaytmMerchant Merchant = new PaytmMerchant(
				"https://pguat.paytm.com/paytmchecksum/paytmCheckSumGenerator.jsp",
				"https://pguat.paytm.com/paytmchecksum/paytmCheckSumVerify.jsp");

		Service.initialize(Order, Merchant, null);

		Service.startPaymentTransaction(context, true, true, new PaytmPaymentTransactionCallback() {
			@Override
			public void onTransactionSuccess(Bundle bundle) {
				Log.d("LOG", "Payment Transaction is successful " + bundle);
				Toast.makeText(context, "Payment Transaction is successful ", Toast.LENGTH_LONG).show();

			}

			@Override
			public void onTransactionFailure(String s, Bundle bundle) {
				Log.d("LOG", "Payment Transaction Failed " + s +" "+bundle);
				Toast.makeText(context, "Payment Transaction Failed ", Toast.LENGTH_LONG).show();

			}

			@Override
			public void networkNotAvailable() {

			}

			@Override
			public void clientAuthenticationFailed(String s) {

			}

			@Override
			public void someUIErrorOccurred(String s) {

			}

			@Override
			public void onErrorLoadingWebPage(int i, String s, String s1) {

			}

			@Override
			public void onBackPressedCancelTransaction() {

			}
		});
	}
}
