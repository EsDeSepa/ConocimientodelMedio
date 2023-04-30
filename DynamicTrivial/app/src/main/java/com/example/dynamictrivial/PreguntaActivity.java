package com.example.dynamictrivial;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import java.util.List;
import java.util.Random;


public class PreguntaActivity extends AppCompatActivity {

    private TextView preguntaTextView;
    private RadioGroup opcionesRadioGroup;
    private Button responderButton;

    private Pregunta preguntaActual;
    private List<Pregunta> listaPreguntas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta);

        preguntaTextView = findViewById(R.id.tv_pregunta);
        opcionesRadioGroup = findViewById(R.id.rg_opciones);
        responderButton = findViewById(R.id.btn_responder);

        // Obtener la lista de preguntas de la categoría seleccionada
        String categoria = getIntent().getStringExtra("CATEGORIA");
        listaPreguntas = obtenerPreguntasDeCategoria(categoria);

        // Obtener una pregunta aleatoria de la lista
        preguntaActual = obtenerPreguntaAleatoria(listaPreguntas);

        // Mostrar la pregunta en el TextView
        preguntaTextView.setText(preguntaActual.getPregunta());

        // Agregar las opciones de respuesta como RadioButtons
        List<String> opciones = preguntaActual.getOpciones();
        for (int i = 0; i < opciones.size(); i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(opciones.get(i));
            opcionesRadioGroup.addView(radioButton);
        }

        // Manejar el click del botón Responder
        responderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int respuestaSeleccionada = opcionesRadioGroup.getCheckedRadioButtonId();
                int respuestaCorrecta = preguntaActual.getRespuesta();
                if (respuestaSeleccionada == -1) {
                    Toast.makeText(getApplicationContext(), "Selecciona una opción", Toast.LENGTH_SHORT).show();
                } else if (respuestaSeleccionada == opcionesRadioGroup.getChildAt(respuestaCorrecta - 1).getId()) {
                    Toast.makeText(getApplicationContext(), "¡Respuesta correcta!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Respuesta incorrecta", Toast.LENGTH_SHORT).show();
                }
                // Obtener una nueva pregunta aleatoria y mostrarla
                preguntaActual = obtenerPreguntaAleatoria(listaPreguntas);
                preguntaTextView.setText(preguntaActual.getPregunta());
                opcionesRadioGroup.removeAllViews();
                List<String> opciones = preguntaActual.getOpciones();
                for (int i = 0; i < opciones.size(); i++) {
                    RadioButton radioButton = new RadioButton(getApplicationContext());
                    radioButton.setText(opciones.get(i));
                    opcionesRadioGroup.addView(radioButton);
                }
            }
        });
    }

    // Obtener la lista de preguntas de una categoría
    private List<Pregunta> obtenerPreguntasDeCategoria(String categoria) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("preguntas");
        Query query = ref.orderByChild("categoria").equalTo(categoria);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Pregunta> preguntas = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Pregunta pregunta = dataSnapshot.getValue(Pregunta.class);
                    preguntas.add(pregunta);
                }
                listaPreguntas = preguntas;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        query.addListenerForSingleValueEvent(eventListener);
        return listaPreguntas;
    }

    // Obtener una pregunta aleatoria de una lista
    private Pregunta obtenerPreguntaAleatoria(List<Pregunta> preguntas) {
        Random random = new Random();
        int indiceAleatorio = random.nextInt(preguntas.size());
        return preguntas.get(indiceAleatorio);
    }
}
