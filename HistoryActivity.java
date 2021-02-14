package com.example.phi2;

import android.content.Intent;
import android.os.Bundle;
//import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HistoryActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);


        TextView Textviewx = (TextView)findViewById(R.id.textViewx);
       final String scanData = getIntent().getStringExtra("eid");
        Textviewx.setText(scanData);
        Button newButton = (Button) findViewById(R.id.newButton);
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(HistoryActivity.this, FormActivity.class);
                myIntent.putExtra("eid",scanData);
                startActivity(myIntent);
            }
        });

    }
}