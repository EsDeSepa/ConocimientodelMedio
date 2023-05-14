package com.example.dynamictrivial;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NuevoJugadorActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    MediaPlayer mp;
    Button btnContinuar;
    Button btnAdd;
    private List<String> jugadores = new ArrayList<>();
    private ArrayList<String> selectedPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevojugador);

        btnContinuar = findViewById(R.id.btn_continue);
        btnAdd = findViewById(R.id.btn_add);
        mp = MediaPlayer.create(this, R.raw.click_sound);

        Intent intent = getIntent();
        selectedPlayers = intent.getStringArrayListExtra("selectedPlayers");

        LinearLayout orderLayout = findViewById(R.id.newplayer_layout);
        mDatabase = FirebaseDatabase.getInstance().getReference();


        //añadir jugador a la bbd para que settings lo pille al volver. Para ello crear el jugador con sus puntos a 0 y añadirlo a la BBDD y su nombre a selectedPlayers
        Button addButton = (Button) findViewById(R.id.btn_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();

                Toast.makeText(NuevoJugadorActivity.this, "Jugador añadido", Toast.LENGTH_SHORT).show();

                selectedPlayers.add("Patatas");
                intent.putExtra("selectedPlayers", (ArrayList<String>) selectedPlayers);
            }
        });

        Button nextButton = (Button) findViewById(R.id.btn_continue);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();

                Intent intent = new Intent(NuevoJugadorActivity.this, SettingsActivity.class);
                intent.putExtra("selectedPlayers", (ArrayList<String>) selectedPlayers);
                startActivity(intent);
                finish();
            }
        });
    }
}