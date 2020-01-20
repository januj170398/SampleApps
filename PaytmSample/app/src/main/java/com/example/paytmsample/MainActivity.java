package com.example.paytmsample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements PaytmPaymentTransactionCallback {

    private ApiInterface apiServicePaytm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101);
        }
    }

    private void generateCheckSum() {
        PaytmPGService Service = PaytmPGService.getStagingService("https://securegw-stage.paytm.in/theia/processTransaction");

        HashMap<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("MID","MlKIIp05519030903434"); //MID provided by paytm
        paramMap.put("ORDER_ID", "OD157951701686832596");
        paramMap.put("CUST_ID", "6");
        paramMap.put("CHANNEL_ID", "WAP");
        paramMap.put("TXN_AMOUNT", "100");
        paramMap.put("WEBSITE", "WEBSTAGING");
        paramMap.put("CALLBACK_URL" ,"https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp");
        paramMap.put( "EMAIL" , "abc@gmail.com");   // no need
        paramMap.put( "MOBILE_NO" , "9999999999");  // no need
        paramMap.put("CHECKSUMHASH" ,"pBCc5WTTuYsggQZfCUCB7jdHVxsu8vG6VRGz2jwMRbvgk0DnRa9FliIXmJhZCskEqTKi1vuX10JYXD5QkGIeel8Gk0G25LOlF/SzQzBFt/M=");
        paramMap.put("INDUSTRY_TYPE_ID", "Retail");
        PaytmOrder Order = new PaytmOrder(paramMap);

        Service.initialize(Order,null);
        Service.startPaymentTransaction(MainActivity.this, true, true,
                MainActivity.this  );
    }

    @Override
    public void onTransactionResponse(Bundle inResponse) {
        Toast.makeText(getApplicationContext(), "Payment Transaction response " + inResponse.toString(), Toast.LENGTH_LONG).show();

    }

    @Override
    public void networkNotAvailable() {
        Toast.makeText(getApplicationContext(), "Network connection error: Check your internet connectivity", Toast.LENGTH_LONG).show();

    }

    @Override
    public void clientAuthenticationFailed(String inErrorMessage) {
        Toast.makeText(getApplicationContext(), "Authentication failed: Server error" + inErrorMessage.toString(), Toast.LENGTH_LONG).show();

    }

    @Override
    public void someUIErrorOccurred(String inErrorMessage) {
        Toast.makeText(getApplicationContext(), "UI Error " + inErrorMessage , Toast.LENGTH_LONG).show();

    }

    @Override
    public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
        Toast.makeText(getApplicationContext(), "Unable to load webpage " + inErrorMessage.toString(), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onBackPressedCancelTransaction() {
        Toast.makeText(getApplicationContext(), "Transaction cancelled" , Toast.LENGTH_LONG).show();

    }

    @Override
    public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {

    }

    public void onStartTransaction(View view) {
        generateCheckSum();

    }
}
