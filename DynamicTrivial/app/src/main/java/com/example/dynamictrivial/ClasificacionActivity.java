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
                LinearLayout playersLayout = findViewById(R.id.layout_clasificacion);

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


        /*DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("jugadores");

        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (String playerName : selectedPlayers) {
                    Query jugadorQuery = databaseRef.orderByChild("nombre").equalTo(playerName);
                    jugadorQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot jugadorSnapshot : dataSnapshot.getChildren()) {
                                Map<String, Object> currentPlayerObj = (Map<String, Object>) jugadorSnapshot.getValue();

                                TextView playerName = findViewById(R.id.jugador);
                                playerName.setText(currentPlayerObj.get("nombre").toString());

                                TextView puntosArte = new TextView(ClasificacionActivity.this);
                                puntosArte.setText("Puntos de la categoría Arte: " + currentPlayerObj.get("puntosArte"));
                                layoutResumen.addView(puntosArte);

                                TextView puntosDeporte = new TextView(ClasificacionActivity.this);
                                puntosDeporte.setText("Puntos de la categoría Deporte: " + currentPlayerObj.get("puntosDeporte"));
                                layoutResumen.addView(puntosDeporte);

                                TextView puntosEntretenimiento = new TextView(ClasificacionActivity.this);
                                puntosEntretenimiento.setText("Puntos de la categoría Entretenimiento: " + currentPlayerObj.get("puntosEntretenimiento"));
                                layoutResumen.addView(puntosEntretenimiento);

                                TextView puntosGeografia = new TextView(ClasificacionActivity.this);
                                puntosGeografia.setText("Puntos de la categoría Geografía: " + currentPlayerObj.get("puntosGeografia"));
                                layoutResumen.addView(puntosGeografia);

                                TextView puntosHistoria = new TextView(ClasificacionActivity.this);
                                puntosHistoria.setText("Puntos de la categoría Historia: " + currentPlayerObj.get("puntosHistoria"));
                                layoutResumen.addView(puntosHistoria);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Manejar el error en caso de que ocurra
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors
            }
        });
        */
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
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clasificacion);
        mp = MediaPlayer.create(this, R.raw.click_sound);
        // Obtener el estado del Switch del Intent
        Intent intent = getIntent();
        selectedPlayers = intent.getStringArrayListExtra("selectedPlayers");

        LinearLayout layoutResumen = findViewById(R.id.layout_clasificacion); // find the existing linear layout

        nextButton = findViewById(R.id.btn_continue);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
                showNextPlayerData(layoutResumen);
            }
        });

        if (!selectedPlayers.isEmpty()) {
            currentPlayerData = new ArrayList<>();
            loadPlayerData(selectedPlayers.get(currentPlayerIndex));
        } else {
            Toast.makeText(this, "No hay jugadores seleccionados", Toast.LENGTH_SHORT).show();
        }

    }

    private void loadPlayerData(String nombre) {
        DatabaseReference jugadoresRef = FirebaseDatabase.getInstance().getReference().child("jugador").child(nombre);
        jugadoresRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentPlayerData.clear();

                if (dataSnapshot.exists()) {
                    String jugadorNombre = dataSnapshot.child("nombre").getValue(String.class);
                    int puntosArte = dataSnapshot.child("puntosArte").getValue(Integer.class);
                    int puntosDeporte = dataSnapshot.child("puntosDeporte").getValue(Integer.class);
                    int puntosEntretenimiento = dataSnapshot.child("puntosEntretenimiento").getValue(Integer.class);
                    int puntosGeografia = dataSnapshot.child("puntosGeografia").getValue(Integer.class);
                    int puntosHistoria = dataSnapshot.child("puntosHistoria").getValue(Integer.class);

                    currentPlayerData.add("Nombre: " + jugadorNombre);
                    currentPlayerData.add("Puntos de la categoría Arte: " + puntosArte);
                    currentPlayerData.add("Puntos de la categoría Deporte: " + puntosDeporte);
                    currentPlayerData.add("Puntos de la categoría Entretenimiento: " + puntosEntretenimiento);
                    currentPlayerData.add("Puntos de la categoría Geografía: " + puntosGeografia);
                    currentPlayerData.add("Puntos de la categoría Historia: " + puntosHistoria);

                    showPlayerData();
                } else {
                    Toast.makeText(ClasificacionActivity.this, "No se encontraron datos para el jugador", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar el error en caso de que ocurra
            }
        });
*/

    /*
    private void showPlayerData() {
        LinearLayout layoutResumen = findViewById(R.id.layout_clasificacion);
        layoutResumen.removeAllViews();

        for (String data : currentPlayerData) {
            TextView textView = new TextView(this);
            textView.setText(data);
            layoutResumen.addView(textView);
        }
    }

    private void showNextPlayerData(LinearLayout layoutResumen) {
        currentPlayerIndex++;
        if (currentPlayerIndex < selectedPlayers.size()) {
            loadPlayerData(selectedPlayers.get(currentPlayerIndex));
        } else {
            Toast.makeText(this, "No hay más jugadores", Toast.LENGTH_SHORT).show();
        }
    }
*/
        /*
        DatabaseReference jugadoresRef = FirebaseDatabase.getInstance().getReference().child("jugadores");
        jugadoresRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Jugador> players = new ArrayList<>();

                for (DataSnapshot jugadorSnapshot : dataSnapshot.getChildren()) {
                    String nombre = jugadorSnapshot.child("nombre").getValue(String.class);
                    int puntosArte = jugadorSnapshot.child("puntosArte").getValue(Integer.class);
                    int puntosDeporte = jugadorSnapshot.child("puntosDeporte").getValue(Integer.class);
                    int puntosEntretenimiento = jugadorSnapshot.child("puntosEntretenimiento").getValue(Integer.class);
                    int puntosGeografia = jugadorSnapshot.child("puntosGeografia").getValue(Integer.class);
                    int puntosHistoria = jugadorSnapshot.child("puntosHistoria").getValue(Integer.class);
                    Jugador jugador = new Jugador(nombre, puntosArte, puntosDeporte, puntosEntretenimiento, puntosGeografia, puntosHistoria);

                    players.add(jugador);
                    obtenerDatosJugador(jugador);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
*/

/*
    private void obtenerDatosJugador(final Jugador jugadorActual) {
        playerNext = jugadorActual;
        DatabaseReference preguntasRef = FirebaseDatabase.getInstance().getReference()
                .child("jugadores").child(jugadorActual.getNombre());
        preguntasRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Jugador> jugadores = new ArrayList<>();
                for (DataSnapshot jugadorSnapshot : dataSnapshot.getChildren()) {
                    Jugador jugador = jugadorSnapshot.getValue(Jugador.class);
                    jugadores.add(jugador);
                }
                if (jugadores.isEmpty()) {
                    Toast.makeText(ResumenActivity.this, "No hay datos en en jugador seleccionado", Toast.LENGTH_SHORT).show();
                    return;
                }

                mostrarJugador(jugadorActual);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Do nothing
            }
        });
    }

    private void mostrarJugador(Jugador jugador) {
        Intent intent = new Intent(this, Jugador.class);
        intent.putExtra("player", playerNext.getNombre());
        //pasar jugador actual
        intent.putExtra("jugador", jugador.getNombre());
        startActivity(intent);
    }
    */
