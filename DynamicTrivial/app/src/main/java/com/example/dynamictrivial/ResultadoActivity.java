package com.example.dynamictrivial;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ResultadoActivity extends AppCompatActivity {

    private Button btnContinuar;
    MediaPlayer mp;
    List<String> selectedPlayers;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);
        LinearLayout layout = findViewById(R.id.res_layout);
        btnContinuar = findViewById(R.id.btn_continue);
        mp = MediaPlayer.create(this, R.raw.click_sound);

        // Obtener el valor del extra "respuesta"
        Intent intent = getIntent();
        selectedPlayers = intent.getStringArrayListExtra("selectedPlayers");
        String cat = intent.getStringExtra("cat");
        String categoriaMayus = cat.substring(0, 1).toUpperCase() + cat.substring(1);
        System.out.println(categoriaMayus);

        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
                //añadir sentencia para seguir el juego, probablemente volver a lanzar el dado
                //quizá se pueda guardar la primera elección entre dado sensor y dado normal
                // y en este punto, mandar al usuario a esa elección. O simplemente al menú ppal
                Intent intent = new Intent(ResultadoActivity.this, ResumenActivity.class);
                intent.putExtra("selectedPlayers", (ArrayList<String>) selectedPlayers);
                startActivity(intent);
                finish();
            }
        });


        boolean answer = intent.getBooleanExtra("answer", false);
        if (answer) {
            TextView textView = new TextView(this);
            textView.setText("¡Correcto! Acertaste la pregunta de la categoría " + categoriaMayus);
            layout.addView(textView,2);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference currentPlayer = database.getReference().child("jugadorActual");

// Add a listener to get the current player's name
            currentPlayer.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // Get the current player's name
                    String playerName = dataSnapshot.getValue(String.class);

                    // Get a reference to the current player's data
                    DatabaseReference jugadorActual = FirebaseDatabase.getInstance().getReference().child("jugadores").child(playerName);

                    // Add a listener to get the current player's points
                    jugadorActual.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            // Get the current value of puntos
                            int puntos = dataSnapshot.child("puntos" + categoriaMayus).getValue(Integer.class);

                            // Increase puntos by 1
                            puntos++;

                            // Update puntos in the database
                            Map<String, Object> childUpdates = new HashMap<>();
                            childUpdates.put("puntos" + categoriaMayus, puntos);
                            jugadorActual.updateChildren(childUpdates);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Handle possible errors
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle possible errors
                }
            });

        }
        else {
            TextView textView = new TextView(this);
            textView.setText("¡Incorrecto! Fallaste la pregunta de la categoría " + categoriaMayus);
            layout.addView(textView,2);
            // Añadir código para modificar la vista si la respuesta es erronea
        }
    }
}

