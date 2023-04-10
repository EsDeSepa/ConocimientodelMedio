package com.example.dynamictrivial;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

//import com.example.dynamictrivial.databinding.ActivityMainBinding;

public class DiceSensorActivity extends AppCompatActivity implements SensorEventListener {

    ImageView diceImg;
    Button rollButton;
    Boolean pressed;


    //private MediaPlayer mediaPlayer;

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 1000;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice_sensor);
        diceImg = findViewById(R.id.imageDice);

        rollDice();
        pressed = false;
        /*mediaPlayer = MediaPlayer.create(this, R.raw.dice_sound);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });*/

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        rollButton = (Button) findViewById(R.id.roll_button);
        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!pressed) {
                    rollDice();
                    /*mediaPlayer.start();
                    mediaPlayer.release();
                    mediaPlayer = null;*/
                    pressed = true;
                }
            }
        });


    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        /*MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.dice_sound);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });*/
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long curTime = System.currentTimeMillis();
            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];

                float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;

                if (speed > SHAKE_THRESHOLD) {
                    if (!pressed) {
                        rollDice();
                        /*
                        mediaPlayer.start();
                        mediaPlayer.release();
                        mediaPlayer = null;
                        */
                        pressed = true;

                    }
                }
                last_x = x;
                last_y = y;
                last_z = z;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);

        // Release the MediaPlayer object
        /*if (mediaPlayer != null) {
            mediaPlayer.release();
        }*/
    }

    @Override
    protected void onStop() {
        super.onStop();
        /*mediaPlayer.release();
        mediaPlayer = null;*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private int previousDiceValue = -1; // initialize to an impossible value

    public void rollDice() {
        int diceValue;
        do {
            diceValue = new Random().nextInt(6) + 1;
        } while (diceValue == previousDiceValue);
        previousDiceValue = diceValue;

        int res = getResources().getIdentifier("dice" + diceValue, "drawable", "com.example.dynamictrivial");
        diceImg.setImageResource(res);
    }
}