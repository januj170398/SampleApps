package com.sample.googlemapssample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int  ERROR_DIALOG_REQUEST = 9001;

    private Button btnMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isServicesOk()){
            init();
        }
    }

    private void init(){

        btnMap = findViewById(R.id.btnMap);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean isServicesOk(){
        Log.d(TAG, "isServicesOk: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);

        if (available == ConnectionResult.SUCCESS){
            // everything is fine and user can make maps

            Log.d(TAG, "isServicesOk: play services workings");
            return true;
        } else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            // an error occured but we can resolve it
            Log.d(TAG, "isServicesOk: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this,
                    available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {

            Toast.makeText(this, "you can't make map requests", Toast.LENGTH_SHORT).show();

        }
        return false;



    }
}
