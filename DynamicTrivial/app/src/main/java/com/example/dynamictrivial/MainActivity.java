package com.example.dynamictrivial;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

//import com.example.dynamictrivial.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private Button mButtonShowPopup;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mp = MediaPlayer.create(this, R.raw.click_sound);

        Button settingsButton = (Button) findViewById(R.id.settings_button);
        settingsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // do something when the TextView is clicked
                mp.start();
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        mButtonShowPopup = findViewById(R.id.button_show_popup);


        mButtonShowPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();
                showPopup();
            }
        });
    }

    private void showPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Instrucciones");

        // Set up the scrollable text and image in the popup
        ScrollView scrollView = new ScrollView(this);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.dice1);
        layout.addView(imageView);

        TextView textView = new TextView(this);
        textView.setText("Para jugar a Dynamic Trivial necesitarás: 1 tablero y tantas fichas de " +
                "jugadores como jugadores seais. \nLo primero que deberéis hacer es dibujar en el " +
                "tablero un circuito cíclico, dibujar al menos varias casillas para cada una de las " +
                "categorías, y señalar la casilla inicial.\nCada jugador tira el dado y elige la categoría " +
                "en la que ha caído, despues contesta a la pregunta y si acierta suma un punto en esa " +
                "categoría, el primer jugador en conseguir al menos un punto en todas las categorías gana.");
        layout.addView(textView);



        scrollView.addView(layout);
        builder.setView(scrollView);

        builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


}