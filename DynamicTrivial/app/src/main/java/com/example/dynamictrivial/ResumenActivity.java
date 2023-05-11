package com.example.dynamictrivial;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class ResumenActivity extends AppCompatActivity {


    Button nextButton;
    MediaPlayer mp;
    private Jugador playerNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen);
        mp = MediaPlayer.create(this, R.raw.click_sound);
        DatabaseReference jugadoresRef = FirebaseDatabase.getInstance().getReference().child("jugadores");
        jugadoresRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Jugador> players = new ArrayList<>();

                for (DataSnapshot jugadorSnapshot : dataSnapshot.getChildren()) {
                    String nombre = jugadorSnapshot.child("nombre").getValue(String.class);
                    int puntosArte = jugadorSnapshot.child("puntosArte").getValue(Integer.class);
                    int puntosDeporte = jugadorSnapshot.child("puntosDeporte").getValue(Integer.class);
                    int puntosEntretenimiento = jugadorSnapshot.child("puntosEntretenimiento").getValue(Integer.class);
                    int puntosGeografia = jugadorSnapshot.child("puntosGeografia").getValue(Integer.class);
                    int puntosHistoria = jugadorSnapshot.child("puntosHistoria").getValue(Integer.class);
                    Jugador jugador = new Jugador(nombre, puntosArte, puntosDeporte, puntosEntretenimiento, puntosGeografia, puntosHistoria);

                    players.add(jugador);
                    obtenerDatosJugador(jugador);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        nextButton = (Button) findViewById(R.id.btn_continue);
        nextButton.setVisibility(View.INVISIBLE); // set the button to be invisible by default
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                mp.start();
                //activar cuando se cree la clase siguiente = ClasificacionActivity con la clasificacion de la partida en ese momento
                /*
                Intent intent = new Intent(ResumenActivity.this, ClasificacionActivity.class);
                startActivity(intent);
                */
            }
        });
    }

    private void obtenerDatosJugador(final Jugador jugadorActual) {
        playerNext = jugadorActual;
        DatabaseReference preguntasRef = FirebaseDatabase.getInstance().getReference()
                .child("jugadores").child(jugadorActual.getNombre());
        preguntasRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Jugador> jugadores = new ArrayList<>();
                for (DataSnapshot jugadorSnapshot : dataSnapshot.getChildren()) {
                    Jugador jugador = jugadorSnapshot.getValue(Jugador.class);
                    jugadores.add(jugador);
                }
                if (jugadores.isEmpty()) {
                    Toast.makeText(ResumenActivity.this, "No hay datos en en jugador seleccionado", Toast.LENGTH_SHORT).show();
                    return;
                }

                mostrarJugador(jugadorActual);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Do nothing
            }
        });
    }

    private void mostrarJugador(Jugador jugador) {
        Intent intent = new Intent(this, Jugador.class);
        intent.putExtra("player", playerNext.getNombre());
        //pasar jugador actual
        intent.putExtra("jugador", jugador.getNombre());
        startActivity(intent);
    }


}
