package com.example.dynamictrivial;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;


public class ResultadoActivity extends AppCompatActivity {

    private TextView tvPregunta;
    private RadioGroup radioGroupOpciones;
    private Button btnResponder;
    private List<String> opciones;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        // Obtener el valor del extra "respuesta"
        Intent intent = getIntent();
        boolean answer = intent.getBooleanExtra("answer", false);
        if (answer) {
           // Añadir código para modificar la vista si la respuesta es correcta
        }
        else {
            // Añadir código para modificar la vista si la respuesta es erronea
        }
    }
}

