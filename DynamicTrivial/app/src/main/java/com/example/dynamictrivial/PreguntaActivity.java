package com.example.dynamictrivial;

import android.content.Intent;
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

    private TextView tvPregunta;
    private RadioGroup radioGroupOpciones;
    private Button btnResponder;
    private List<String> opciones;
    private int respuesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta);

        // Obtener los datos de la pregunta seleccionada
        Intent intent = getIntent();
        Pregunta pregunta = intent.getParcelableExtra("pregunta");

        // Obtener las vistas del layout
        tvPregunta = findViewById(R.id.tv_pregunta);
        radioGroupOpciones = findViewById(R.id.radio_group_opciones);
        btnResponder = findViewById(R.id.btn_responder);

        // Mostrar la pregunta y opciones en las vistas correspondientes
        tvPregunta.setText(pregunta.getPregunta());
        opciones = pregunta.getOpciones();
        respuesta = pregunta.getRespuesta();
        for (int i = 0; i < opciones.size(); i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(opciones.get(i));
            radioGroupOpciones.addView(radioButton);
        }

        // Definir el comportamiento del botón de responder
        btnResponder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener la opción seleccionada
                int opcionSeleccionada = radioGroupOpciones.getCheckedRadioButtonId();
                if (opcionSeleccionada == -1) {
                    // Si no se ha seleccionado ninguna opción, mostrar un mensaje
                    Toast.makeText(PreguntaActivity.this, "Debes seleccionar una opción", Toast.LENGTH_SHORT).show();
                } else {
                    // Si se ha seleccionado una opción, verificar si es la respuesta correcta
                    int opcionSeleccionadaIndex = radioGroupOpciones.indexOfChild(findViewById(opcionSeleccionada));
                    if (opcionSeleccionadaIndex == respuesta) {
                        // Si es la respuesta correcta, mostrar un mensaje y cerrar la actividad
                        Toast.makeText(PreguntaActivity.this, "¡Respuesta correcta!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        // Si no es la respuesta correcta, mostrar un mensaje
                        Toast.makeText(PreguntaActivity.this, "Respuesta incorrecta, intenta de nuevo", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}

