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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ResumenActivity extends AppCompatActivity {

    Button nextButton;
    MediaPlayer mp;
    private Jugador playerNext;
    List<String> selectedPlayers;
    String currentPlayer = "jugador1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen);
        mp = MediaPlayer.create(this, R.raw.click_sound);
        LinearLayout layoutResumen = findViewById(R.id.layout_resumen); // find the existing linear layout

        Intent intent = getIntent();
        selectedPlayers = intent.getStringArrayListExtra("selectedPlayers");

        // get the player object from the Firebase JSON
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                Map<String, Object> playersObj = (Map<String, Object>) map.get("jugadores");
                Map<String, Object> currentPlayerObj = (Map<String, Object>) playersObj.get(currentPlayer);

                TextView playerName = findViewById(R.id.jugador);
                playerName.setText(currentPlayerObj.get("nombre").toString());

                TextView puntosArte = new TextView(ResumenActivity.this);
                puntosArte.setText("Puntos de la categoría Arte: " + ((Long) currentPlayerObj.get("puntosArte")).intValue());
                layoutResumen.addView(puntosArte,1);

                TextView puntosDeporte = new TextView(ResumenActivity.this);
                puntosDeporte.setText("Puntos de la categoría Deporte: " + ((Long) currentPlayerObj.get("puntosDeporte")).intValue());
                layoutResumen.addView(puntosDeporte,2);

                TextView puntosEntretenimiento = new TextView(ResumenActivity.this);
                puntosEntretenimiento.setText("Puntos de la categoría Entretenimiento: " + ((Long) currentPlayerObj.get("puntosEntretenimiento")).intValue());
                layoutResumen.addView(puntosEntretenimiento,3);

                TextView puntosGeografia = new TextView(ResumenActivity.this);
                puntosGeografia.setText("Puntos de la categoría Geografía: " + ((Long) currentPlayerObj.get("puntosGeografia")).intValue());
                layoutResumen.addView(puntosGeografia,4);

                TextView puntosHistoria = new TextView(ResumenActivity.this);
                puntosHistoria.setText("Puntos de la categoría Historia: " + ((Long) currentPlayerObj.get("puntosHistoria")).intValue());
                layoutResumen.addView(puntosHistoria,5);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors
            }
        });

        nextButton = findViewById(R.id.btn_continue);
        //nextButton.setVisibility(View.INVISIBLE); // set the button to be invisible by default
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
                Intent intent = new Intent(ResumenActivity.this, ClasificacionActivity.class);
                intent.putExtra("selectedPlayers", (ArrayList<String>) selectedPlayers);
                startActivity(intent);
            }
        });
    }
}
