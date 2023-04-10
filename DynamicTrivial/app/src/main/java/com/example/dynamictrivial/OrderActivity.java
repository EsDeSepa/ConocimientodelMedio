package com.example.dynamictrivial;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;

//import com.example.dynamictrivial.databinding.ActivityMainBinding;

public class OrderActivity extends AppCompatActivity {

    ArrayList<String> testSubjects = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        testSubjects.add("pepe");
        testSubjects.add("paco");
        testSubjects.add("pedro");

        LinearLayout orderLayout = findViewById(R.id.order_layout);

        for (int i = 0; i<testSubjects.size(); i++) {
            TextView nameView = new TextView(this);
            nameView.setText(testSubjects.get(i));
            orderLayout.addView(nameView);
        }

        Button shuffleButton = (Button) findViewById(R.id.shuffle_button);
        shuffleButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Shuffle the array list containing the test subjects
                Collections.shuffle(testSubjects);

                // Find the LinearLayout that contains the TextViews
                LinearLayout orderLayout = findViewById(R.id.order_layout);

                // Remove all existing TextViews from the layout
                orderLayout.removeAllViews();

                // Add new TextViews to the layout in the shuffled order
                for (int i = 0; i < testSubjects.size(); i++) {
                    TextView nameView = new TextView(OrderActivity.this);
                    nameView.setText(testSubjects.get(i));
                    orderLayout.addView(nameView);
                }
            }
        });



    }
}