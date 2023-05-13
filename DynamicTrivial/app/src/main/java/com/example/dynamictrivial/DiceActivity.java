package com.example.dynamictrivial;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DiceActivity extends AppCompatActivity {

    TextView currentPlayerTextView;
    ImageView diceImg;
    Button rollButton;
    Button nextButton;
    Boolean pressed;
    List<String> selectedPlayers;
    MediaPlayer mpClick;
    MediaPlayer mpDice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);
        diceImg = findViewById(R.id.imageDice);
        mpClick = MediaPlayer.create(this, R.raw.click_sound);
        mpDice = MediaPlayer.create(this, R.raw.dice_sound);
        rollDice();

        pressed = false;
        currentPlayerTextView = findViewById(R.id.current_player);
        //pilla los selectedPlayers
        Intent intent = getIntent();
        selectedPlayers = intent.getStringArrayListExtra("selectedPlayers");

        // Get a reference to the Firebase database
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        // Get a reference to the "jugadorActual" value in the Firebase database
        database.getReference("jugadorActual").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get the current player's ID from the dataSnapshot
                String currentPlayerId = dataSnapshot.getValue(String.class);

                // Use the current player's ID to retrieve their "nombre" field from the "jugadores" object
                database.getReference("jugadores/" + currentPlayerId + "/nombre").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get the current player's name from the dataSnapshot
                        String currentPlayerName = dataSnapshot.getValue(String.class);

                        // Set the current player's name in the TextView
                        currentPlayerTextView.setText(currentPlayerName);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle database errors here
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database errors here
            }
        });


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
                mpClick.start();
                Intent intent = new Intent(DiceActivity.this, DondeCaiActivity.class);
                intent.putExtra("selectedPlayers", (ArrayList<String>) selectedPlayers);
                startActivity(intent);
                finish();
            }
        });
    }

    public void rollDice() {
        int diceValue = new Random().nextInt(6) + 1;
        int res = getResources().getIdentifier("dice" + diceValue, "drawable", "com.example.dynamictrivial");

        diceImg.setImageResource(res);
    }
}