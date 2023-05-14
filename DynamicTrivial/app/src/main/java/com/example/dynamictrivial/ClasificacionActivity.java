package com.example.dynamictrivial;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClasificacionActivity extends AppCompatActivity {

    Button nextButton;
    MediaPlayer mp;
    private ArrayList<String> selectedPlayers;
    private int currentPlayerIndex = 0;
    private ArrayList<String> currentPlayerData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clasificacion);
        mp = MediaPlayer.create(this, R.raw.click_sound);

        Intent intent = getIntent();
        selectedPlayers = intent.getStringArrayListExtra("selectedPlayers");

        LinearLayout layoutResumen = findViewById(R.id.layout_clasificacion);
        DatabaseReference playersRef = FirebaseDatabase.getInstance().getReference().child("jugadores");

        playersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LinearLayout playersLayout = findViewById(R.id.layout_muestraclasif);

                for (DataSnapshot playerSnapshot : dataSnapshot.getChildren()) {
                    String playerName = playerSnapshot.child("nombre").getValue(String.class);
                    int puntosArte = playerSnapshot.child("puntosArte").getValue(Integer.class);
                    int puntosDeporte = playerSnapshot.child("puntosDeporte").getValue(Integer.class);
                    int puntosEntretenimiento = playerSnapshot.child("puntosEntretenimiento").getValue(Integer.class);
                    int puntosGeografia = playerSnapshot.child("puntosGeografia").getValue(Integer.class);
                    int puntosHistoria = playerSnapshot.child("puntosHistoria").getValue(Integer.class);

                    String playerInfo = "Nombre: " + playerName + "\n"
                            + "Puntos de Arte: " + puntosArte + "\n"
                            + "Puntos de Deporte: " + puntosDeporte + "\n"
                            + "Puntos de Entretenimiento: " + puntosEntretenimiento + "\n"
                            + "Puntos de Geografía: " + puntosGeografia + "\n"
                            + "Puntos de Historia: " + puntosHistoria + "\n";

                    TextView playerTextView = new TextView(ClasificacionActivity.this);
                    playerTextView.setText(playerInfo);
                    playersLayout.addView(playerTextView);
                }

                // Scroll to the bottom of the players layout
                ScrollView playersScrollView = findViewById(R.id.playersScrollView);
                playersScrollView.post(() -> playersScrollView.fullScroll(ScrollView.FOCUS_DOWN));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors
            }
        });

        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference currentPlayerRef = databaseRef.child("jugadorActual");
        currentPlayerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String currentPlayer = dataSnapshot.getValue(String.class);
                DatabaseReference orderRef = databaseRef.child("orden");
                orderRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<String> orderList = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String jugador = snapshot.getValue(String.class);
                            orderList.add(jugador);
                        }
                        int currentIndex = orderList.indexOf(currentPlayer);
                        int nextIndex = (currentIndex + 1) % orderList.size();
                        String nextPlayer = orderList.get(nextIndex);
                        databaseRef.child("jugadorActual").setValue(nextPlayer);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle possible errors
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors
            }
        });
        nextButton = findViewById(R.id.btn_continue);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
                Intent intent = new Intent(ClasificacionActivity.this, DiceActivity.class);
                intent.putStringArrayListExtra("selectedPlayers", selectedPlayers);
                startActivity(intent);
                finish();

            }
        });
    }
}
