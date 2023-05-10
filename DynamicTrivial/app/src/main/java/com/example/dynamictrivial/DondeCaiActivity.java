package com.example.dynamictrivial;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DondeCaiActivity extends AppCompatActivity {

    private DatabaseReference categoriasRef;
    private List<String> categoriasList = new ArrayList<>();
    private String categoriaNext;

    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donde_cai);
        mp = MediaPlayer.create(this, R.raw.click_sound);

        categoriasRef = FirebaseDatabase.getInstance().getReference().child("categorias");
        categoriasRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                categoriasList.clear();
                for (DataSnapshot categoriaSnapshot : dataSnapshot.getChildren()) {
                    String categoriaNombre = categoriaSnapshot.getKey();
                    categoriasList.add(categoriaNombre);
                }
                createButtons();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Do nothing
            }

        });


    }

    private void createButtons() {
        /*LinearLayout buttonsLayout = findViewById(R.id.buttons_layout);
        buttons_layout.removeAllViews();
        */


        // Botón 1
        Button buttonCat1 = findViewById(R.id.button_cat1);
        buttonCat1.setText("ARTE");
        buttonCat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
                Toast.makeText(DondeCaiActivity.this, "Categoría: ARTE", Toast.LENGTH_SHORT).show();
                // Acción personalizada para el botón ARTE
            }
        });

        // Botón 2
        Button buttonCat2 = findViewById(R.id.button_cat2);
        buttonCat2.setText("DEPORTES");
        buttonCat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
                Toast.makeText(DondeCaiActivity.this, "Categoría: DEPORTES", Toast.LENGTH_SHORT).show();
                // Acción personalizada para el botón DEPORTES
            }
        });


        // Botón 3
        Button buttonCat3 = findViewById(R.id.button_cat3);
        buttonCat3.setText("HISTORIA");
        buttonCat3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
                Toast.makeText(DondeCaiActivity.this, "Categoría: HISTORIA", Toast.LENGTH_SHORT).show();
                // Acción personalizada para el botón HISTORIA
            }
        });

        // Botón 4
        Button buttonCat4 = findViewById(R.id.button_cat4);
        buttonCat4.setText("GEOGRAFIA");
        buttonCat4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
                Toast.makeText(DondeCaiActivity.this, "Categoría: GEOGRAFIA", Toast.LENGTH_SHORT).show();
                // Acción personalizada para el botón GEOGRAFIA
            }
        });

        // Botón 5
        Button buttonCat5 = findViewById(R.id.button_cat5);
        buttonCat5.setText("ENTRETENIMIENTO");
        buttonCat5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
                Toast.makeText(DondeCaiActivity.this, "Categoría: ENTRETENIMIENTO", Toast.LENGTH_SHORT).show();
                // Acción personalizada para el botón ENTRETENIMIENTO
            }
        });
    }


    private void obtenerPreguntaAleatoria(final String categoriaSeleccionada) {
        categoriaNext = categoriaSeleccionada;
        DatabaseReference preguntasRef = FirebaseDatabase.getInstance().getReference()
                .child("categorias").child(categoriaSeleccionada);
        preguntasRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Pregunta> preguntas = new ArrayList<>();
                for (DataSnapshot preguntaSnapshot : dataSnapshot.getChildren()) {
                    Pregunta pregunta = preguntaSnapshot.getValue(Pregunta.class);
                    preguntas.add(pregunta);
                }
                if (preguntas.isEmpty()) {
                    Toast.makeText(DondeCaiActivity.this, "No hay preguntas en la categoría seleccionada", Toast.LENGTH_SHORT).show();
                    return;
                }
                int randomIndex = new Random().nextInt(preguntas.size());
                Pregunta preguntaAleatoria = preguntas.get(randomIndex);
                mostrarPregunta(preguntaAleatoria);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Do nothing
            }
        });
    }

    private void mostrarPregunta(Pregunta pregunta) {
        Intent intent = new Intent(this, PreguntaActivity.class);
        intent.putExtra("cat", categoriaNext);
        //pasar jugador actual
        intent.putExtra("pregunta", pregunta);
        startActivity(intent);
    }

}



