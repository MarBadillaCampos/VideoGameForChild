package com.example.projecto2jam;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;


public class credits extends AppCompatActivity {

    private TextView creditsTextView;
    private Button continueButton;

    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        creditsTextView = findViewById(R.id.creditsTextView);
        continueButton = findViewById(R.id.continueButton);
        continueButton.setVisibility(View.INVISIBLE); // Hacer que el botón sea invisible inicialmente

        setSupportActionBar(findViewById(R.id.myToolbar));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        mp = MediaPlayer.create(this, R.raw.alphabet_song);
        mp.start();
        mp.setLooping(true);

        creditsTextView.setText("Universidad Nacional de Costa Rica\n \nDiseño y Programación de Plataformas Móviles\n \nProyecto II\n \nProfesor:\n  \nGregorio Villalobos Camacho\n \nEstudiantes:\n \nJam Carlos Ramirez Chaves\n \nMarlen Badilla Campos\n \nFabiana Barrantes Li\n \nI Ciclo 2023\n ");

        Animation animation = new TranslateAnimation(0, 0, getWindowManager().getDefaultDisplay().getHeight(), -500);
        animation.setDuration(10000);
        animation.setFillAfter(true);
        animation.setStartOffset(2000);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                creditsTextView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                creditsTextView.setVisibility(View.GONE);
                continueButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        creditsTextView.startAnimation(animation);


        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continueButton.setVisibility(View.INVISIBLE); // Make the button invisible again when clicked

                // Create an intent to return to the MainActivity
                Intent intent = new Intent(credits.this, MainActivity.class);
                startActivity(intent);
                finish(); // Finish the credits activity to remove it from the back stack
            }
        });
    }
}
