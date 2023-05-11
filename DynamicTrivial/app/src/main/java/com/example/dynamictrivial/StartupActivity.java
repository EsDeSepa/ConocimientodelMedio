package com.example.dynamictrivial;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

//import com.example.dynamictrivial.databinding.ActivityMainBinding;

public class StartupActivity extends AppCompatActivity {

    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        mp = MediaPlayer.create(this, R.raw.click_sound);
        Button startButton = (Button) findViewById(R.id.start_button);

        startButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // do something when the TextView is clicked
                mp.start();
                Intent intent = new Intent(StartupActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });
        Button diceSensorButton = (Button) findViewById(R.id.dice_sensor_button);
        diceSensorButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // do something when the TextView is clicked
                mp.start();
                Intent intent = new Intent(StartupActivity.this, DiceSensorActivity.class);
                startActivity(intent);

            }
        });
        Button diceButton = (Button) findViewById(R.id.dice_button);
        diceButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // do something when the TextView is clicked
                mp.start();
                Intent intent = new Intent(StartupActivity.this, DiceActivity.class);
                startActivity(intent);

            }
        });

        Button orderButton = (Button) findViewById(R.id.order_button);
        orderButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // do something when the TextView is clicked
                mp.start();
                Intent intent = new Intent(StartupActivity.this, OrderActivity.class);
                startActivity(intent);

            }
        });
        Button dondeCaiButton = (Button) findViewById(R.id.dondecai_button);
        dondeCaiButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // do something when the TextView is clicked
                mp.start();
                Intent intent = new Intent(StartupActivity.this, DondeCaiActivity.class);
                startActivity(intent);
            }
        });
    }
}