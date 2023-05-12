package com.example.dynamictrivial;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    private LinearLayout checkBoxPlayers;
    private Switch switch1;
    DatabaseReference dadoRef;
    Button nextButton;
    MediaPlayer mp;
    List<String> selectedPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
        mp = MediaPlayer.create(this, R.raw.click_sound);

        // Obtener las vistas del layout
        checkBoxPlayers = findViewById(R.id.check_layout);

        // Obtenemos las referencias a los Switches del layout y su estado
        switch1 = findViewById(R.id.btnDado);
        Switch btnDado = findViewById(R.id.btnDado);
        boolean superDadoSensorActivado = btnDado.isChecked();

        // Obtener la referencia al campo "dado" en la base de datos de Firebase
        dadoRef = FirebaseDatabase.getInstance().getReference().child("dado");
        dadoRef.setValue(false);
        // Agregar un listener para actualizar el valor del campo "dado" cuando cambie el estado del Switch
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mp.start();

                // Actualizar el valor del campo "dado" en la base de datos de Firebase según el estado del Switch
                dadoRef.setValue(isChecked);
            }
        });

        // Inicializar la lista de jugadores seleccionados
        selectedPlayers = new ArrayList<>();

        // Obtener la referencia a la base de datos de Firebase
        DatabaseReference jugadoresRef = FirebaseDatabase.getInstance().getReference().child("jugadores");

        // Agregar un listener para obtener los datos de los jugadores
        jugadoresRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Limpiar la lista de jugadores seleccionados
                selectedPlayers.clear();

                // Recorrer los datos de los jugadores
                for (DataSnapshot jugadorSnapshot : dataSnapshot.getChildren()) {
                    // Obtener el nombre de cada jugador
                    String nombre = jugadorSnapshot.child("nombre").getValue(String.class);

                    // Mostrar el nombre del jugador y agregar por cada uno un CheckBox al LinearLayout
                    CheckBox checkBox = new CheckBox(SettingsActivity.this);
                    checkBox.setText(nombre);
                    checkBox.setOnCheckedChangeListener(checkBoxListener);
                    checkBoxPlayers.addView(checkBox);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar el error en caso de que ocurra
            }
        });

        nextButton = findViewById(R.id.btn_continue);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mp.start();
                // Verificar si se ha seleccionado al menos un jugador
                if (!selectedPlayers.isEmpty()) {
                    // Mostrar mensaje de jugadores seleccionados y seguir
                    Toast.makeText(SettingsActivity.this, "¡Jugadores añadidos!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SettingsActivity.this, OrderActivity.class);
                    intent.putExtra("selectedPlayers", (ArrayList<String>) selectedPlayers);
                    startActivity(intent);
                    finish();
                } else {
                    // Mostrar mensaje de ningún jugador seleccionado y volver a la actividad actual
                    Toast.makeText(SettingsActivity.this, "¡No se ha seleccionado ningún jugador! Selecciona al menos uno.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private CompoundButton.OnCheckedChangeListener checkBoxListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            String playerName = buttonView.getText().toString();

            if (isChecked) {
                // Agregar el jugador a la lista de jugadores seleccionados
                selectedPlayers.add(playerName);
            } else {
                // Quitar el jugador de la lista de jugadores seleccionados
                selectedPlayers.remove(playerName);
            }
        }
    };
}
