package com.example.dynamictrivial;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VictoryActivity extends AppCompatActivity {

    Button nextButton;
    MediaPlayer mp;
    private String winningPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_victory);
        mp = MediaPlayer.create(this, R.raw.click_sound);

        Intent intent = getIntent();
        winningPlayer = intent.getExtras().getString("winningPlayer");

        LinearLayout layoutVictoria = findViewById(R.id.layout_victory);
        TextView winner = new TextView(VictoryActivity.this);
        winner.setText(winningPlayer);
        layoutVictoria.addView(winner,1);

        nextButton = findViewById(R.id.btn_continue);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
                Intent intent = new Intent(VictoryActivity.this, MainActivity.class);

                startActivity(intent);
                finish();

            }
        });
    }
}
