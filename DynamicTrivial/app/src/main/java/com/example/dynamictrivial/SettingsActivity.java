package com.example.dynamictrivial;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
/*
    private Switch switch1;
    private Switch switch2;
    private Switch switch3;
    private Switch switch4;
    private Switch switch5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Obtenemos las referencias a los Switches del layout
        switch1 = findViewById(R.id.cat1);
        switch2 = findViewById(R.id.cat2);
        switch3 = findViewById(R.id.cat3);
        switch4 = findViewById(R.id.cat4);
        switch5 = findViewById(R.id.cat5);

        // Agregamos un listener a cada Switch
        switch1.setOnCheckedChangeListener(switchListener);
        switch2.setOnCheckedChangeListener(switchListener);
        switch3.setOnCheckedChangeListener(switchListener);
        switch4.setOnCheckedChangeListener(switchListener);
        switch5.setOnCheckedChangeListener(switchListener);
    }

    private CompoundButton.OnCheckedChangeListener switchListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            // Se ejecuta cuando cambia el estado de un Switch

            // Obtenemos el id del Switch que cambió
            int switchId = buttonView.getId();

            // Creamos una variable para almacenar el mensaje que mostraremos en el Toast
            String message;

            // Dependiendo del Switch que cambió, mostramos un mensaje diferente
            switch (switchId) {
                case R.id.cat1:
                    message = isChecked ? "Switch 1 activado" : "Switch 1 desactivado";
                    break;
                case R.id.cat2:
                    message = isChecked ? "Switch 2 activado" : "Switch 2 desactivado";
                    break;
                case R.id.cat3:
                    message = isChecked ? "Switch 3 activado" : "Switch 3 desactivado";
                    break;
                case R.id.cat4:
                    message = isChecked ? "Switch 4 activado" : "Switch 4 desactivado";
                    break;
                case R.id.cat5:
                    message = isChecked ? "Switch 5 activado" : "Switch 5 desactivado";
                    break;
                default:
                    message = "Switch desconocido";
            }

            // Mostramos un Toast con el mensaje correspondiente
            Toast.makeText(SettingsActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };
*/
}
