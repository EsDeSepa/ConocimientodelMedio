package com.example.dynamictrivial;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class DiceActivity extends AppCompatActivity {

    ImageView diceImg;
    Button rollButton;
    Button nextButton;
    Boolean pressed;
    MediaPlayer mpClic;
    MediaPlayer mpDice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);
        diceImg = findViewById(R.id.imageDice);
        mpClic = MediaPlayer.create(this, R.raw.click_sound);
        mpDice = MediaPlayer.create(this, R.raw.dice_sound);
        rollDice();

        pressed = false;

        rollButton = (Button) findViewById(R.id.roll_button);
        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                mpDice.start();
                if(!pressed) {
                    rollDice();
                    pressed = true;
                    nextButton.setVisibility(View.VISIBLE); // make the button visible
                }
            }
        });

        nextButton = (Button) findViewById(R.id.next_button);
        nextButton.setVisibility(View.INVISIBLE); // set the button to be invisible by default
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                mpClic.start();
                Intent intent = new Intent(DiceActivity.this, DondeCaiActivity.class);
                startActivity(intent);
            }
        });
    }

    public void rollDice() {
        int diceValue = new Random().nextInt(6) + 1;
        int res = getResources().getIdentifier("dice" + diceValue, "drawable", "com.example.dynamictrivial");

        diceImg.setImageResource(res);
    }
}