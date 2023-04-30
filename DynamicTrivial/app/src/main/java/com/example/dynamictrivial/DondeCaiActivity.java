package com.example.dynamictrivial;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DondeCaiActivity extends AppCompatActivity {

    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donde_cai);

        dbRef = FirebaseDatabase.getInstance().getReference();

        // Obtener referencias a los elementos de la interfaz de usuario
        TextView tvCategorias = findViewById(R.id.tvCategorias);
        ListView lvCategorias = findViewById(R.id.listViewCategorias);

        // Obtener las categorías de la base de datos y mostrarlas en la interfaz de usuario
        dbRef.child("categorias").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> categorias = new ArrayList<>();
                for (DataSnapshot categoriaSnapshot : snapshot.getChildren()) {
                    String categoria = categoriaSnapshot.getKey();
                    categorias.add(categoria);
                }
                ArrayAdapter<String> categoriasAdapter = new ArrayAdapter<>(DondeCaiActivity.this, android.R.layout.simple_list_item_1, categorias);
                lvCategorias.setAdapter(categoriasAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DondeCaiActivity", "Error al obtener categorías de la base de datos", error.toException());
            }
        });

        // Configurar el listener de clic en un elemento de la lista de categorías
        lvCategorias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String categoriaSeleccionada = (String) parent.getItemAtPosition(position);

                // Obtener una pregunta aleatoria de la categoría seleccionada
                dbRef.child("preguntas").child(categoriaSeleccionada).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<Pregunta> preguntas = new ArrayList<>();
                        for (DataSnapshot preguntaSnapshot : snapshot.getChildren()) {
                            Pregunta pregunta = preguntaSnapshot.getValue(Pregunta.class);
                            preguntas.add(pregunta);
                        }

                        // Seleccionar una pregunta aleatoria
                        Random random = new Random();
                        Pregunta preguntaSeleccionada = preguntas.get(random.nextInt(preguntas.size()));

                        // Mostrar la pregunta en una nueva actividad
                        Intent intent = new Intent(DondeCaiActivity.this, PreguntaActivity.class);
                        intent.putExtra("pregunta", preguntaSeleccionada);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("DondeCaiActivity", "Error al obtener preguntas de la base de datos", error.toException());
                    }
                });
            }
        });
    }
}


