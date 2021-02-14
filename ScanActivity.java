package com.example.phi2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView scannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);

        //asynch task to get recent data for the eid




    }

    @Override
    public void handleResult(Result result) {
        Snackbar snackbar;

        //Initializing snackbar using Snacbar.make() method
        snackbar = Snackbar.make(scannerView,"The text is "+ result.getText()+"Press Back to quit",Snackbar.LENGTH_LONG).setAction("View History", new View.OnClickListener() {
            public void onClick(View view) {

               onBackPressed();
            }});
        //Displaying the snackbar using the show method()
        snackbar.show();
        //Snackbar snackbar2 = Snackbar.make(scannerView, "Press Back, to quit scanning" , Snackbar.LENGTH_LONG);
        //snackbar2.show();
        //onBackPressed();
        Intent myIntent = new Intent(ScanActivity.this, HistoryActivity.class);
        myIntent.putExtra("eid", result.getText()); //Optional parameters
       this.startActivity(myIntent);
    }

    @Override
    protected void onPause() {

        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    protected void onResume() {

        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
        Snackbar snackbar = Snackbar.make(scannerView, "Press Back, to quit scanning" , Snackbar.LENGTH_LONG);
    }
}