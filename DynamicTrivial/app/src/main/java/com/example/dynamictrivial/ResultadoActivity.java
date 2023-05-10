package com.example.dynamictrivial;

import android.content.Intent;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ResultadoActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);
        LinearLayout layout = findViewById(R.id.login_layout);

        // Obtener el valor del extra "respuesta"
        Intent intent = getIntent();
        String cat = intent.getStringExtra("cat");
        String categoriaMayus = cat.substring(0, 1).toUpperCase() + cat.substring(1);
        System.out.println(categoriaMayus);
        boolean answer = intent.getBooleanExtra("answer", false);
        if (answer) {
            TextView textView = new TextView(this);
            textView.setText(answer + "");
            layout.addView(textView);
            /*FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference jugadoresRef = database.getReference().child("jugadores").child("jugador1");

            jugadoresRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // Get the current value of puntos
                    int puntos = dataSnapshot.child("puntos" + categoriaMayus).getValue(Integer.class);

                    // Increase puntos by 1
                    puntos++;

                    // Update puntos in the database
                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put("puntos" + categoriaMayus, puntos);
                    jugadoresRef.updateChildren(childUpdates);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle possible errors
                }
            });*/
        }
        else {
            TextView textView = new TextView(this);
            textView.setText(answer + "");
            layout.addView(textView);
            // Añadir código para modificar la vista si la respuesta es erronea
        }
    }
}

