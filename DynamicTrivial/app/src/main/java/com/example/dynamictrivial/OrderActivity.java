package com.example.dynamictrivial;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.example.dynamictrivial.databinding.ActivityMainBinding;

public class OrderActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    MediaPlayer mp;
    Button btnContinuar;
    private List<String> jugadores = new ArrayList<>();
    private LinearLayout linearLayout;
    private TextView[] textViews;
    private ArrayList<String> selectedPlayers;
    ArrayList<String> testSubjects = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        btnContinuar = findViewById(R.id.btn_continue);
        mp = MediaPlayer.create(this, R.raw.click_sound);

        Intent intent = getIntent();
        selectedPlayers = intent.getStringArrayListExtra("selectedPlayers");
        /*
        testSubjects.add("pepe");
        testSubjects.add("paco");
        testSubjects.add("pedro");
        */
        LinearLayout orderLayout = findViewById(R.id.order_layout);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference orderDatabase = FirebaseDatabase.getInstance().getReference().child("orden");

        mDatabase.child("jugadores").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                jugadores = new ArrayList<>();
                for (DataSnapshot jugadorSnapshot : dataSnapshot.getChildren()) {
                    String jugadorId = jugadorSnapshot.getKey();
                    String nombre = jugadorSnapshot.child("nombre").getValue(String.class);
                    if (selectedPlayers.contains(nombre)) {
                        jugadores.add(jugadorId);
                        TextView nameView = new TextView(getApplicationContext());
                        nameView.setText(nombre);
                        orderLayout.addView(nameView);
                    }

                    /*
                    jugadores.add(jugadorId);
                    TextView nameView = new TextView(getApplicationContext());
                    nameView.setText(nombre);
                    orderLayout.addView(nameView);
                     */
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

/*
        for (int i = 0; i<testSubjects.size(); i++) {
            TextView nameView = new TextView(this);
            nameView.setText(testSubjects.get(i));
            orderLayout.addView(nameView);
        }
*/
        Button shuffleButton = (Button) findViewById(R.id.shuffle_button);
        shuffleButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mp.start();
                // Shuffle the array list containing the test subjects
                Collections.shuffle(jugadores);

                // Find the LinearLayout that contains the TextViews
                LinearLayout orderLayout = findViewById(R.id.order_layout);

                // Remove all existing TextViews from the layout
                orderLayout.removeAllViews();

                for (int i = 0; i < jugadores.size(); i++) {
                    String jugadorId = jugadores.get(i);
                    int turno = i + 1;
                    orderDatabase.child(String.valueOf(i)).setValue(jugadorId);
                    mDatabase.child("jugadores").child(jugadorId).child("turno").setValue(turno);
                }


                // Add new TextViews to the layout in the shuffled order
                for (int i = 0; i < jugadores.size(); i++) {
                    String jugadorId = jugadores.get(i);
                    TextView nameView = new TextView(OrderActivity.this);
                    DatabaseReference jugadorRef = mDatabase.child("jugadores").child(jugadorId);
                    jugadorRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String nombre = dataSnapshot.child("nombre").getValue(String.class);
                            TextView nameView = new TextView(getApplicationContext());
                            nameView.setText(nombre);
                            orderLayout.addView(nameView);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }
            }
        });
        Button nextButton = (Button) findViewById(R.id.btn_continue);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do something when the TextView is clicked
                mp.start();

                // Update the "jugadorActual" field in the database with the corresponding "jugadorId" value
                // Assuming the first player is the current player
                DatabaseReference jugadorActual = FirebaseDatabase.getInstance().getReference().child("jugadorActual");
                jugadorActual.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // Get the current player's name
                        jugadorActual.setValue(jugadores.get(0));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle possible errors
                    }
                });

                DatabaseReference dadoRef = FirebaseDatabase.getInstance().getReference().child("dado");
                dadoRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        boolean dadoValue = dataSnapshot.getValue(Boolean.class);
                        if (dadoValue == true) {
                            Intent intent = new Intent(OrderActivity.this, DiceSensorActivity.class);
                            intent.putExtra("selectedPlayers", (ArrayList<String>) selectedPlayers);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(OrderActivity.this, DiceActivity.class);
                            intent.putExtra("selectedPlayers", (ArrayList<String>) selectedPlayers);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Manejar el error en caso de que ocurra
                    }
                });
            }
        });
    }
}