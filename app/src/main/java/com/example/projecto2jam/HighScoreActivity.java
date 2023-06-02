package com.example.projecto2jam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HighScoreActivity extends AppCompatActivity {

    Button btn_highscore;
    LinearLayout linearLayout;
    TextView txtPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        btn_highscore = findViewById(R.id.btn_VolverMenu);
        linearLayout = findViewById(R.id.layoutHighScore);


        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"BD",null,1);
        SQLiteDatabase BD = admin.getWritableDatabase();

        Cursor consulta = BD.rawQuery("select * from puntaje ORDER BY score DESC ",null);

        if(consulta.moveToFirst()){
            linearLayout.removeAllViews();

            do {
                String temp_nombre = consulta.getString(0);
                String temp_score = consulta.getString(1);
                TextView jugador = new TextView(this);
                jugador.setId(View.generateViewId());
                jugador.setText("Record: "+ temp_score+ " de "+ temp_nombre);

                linearLayout.addView(jugador);

            }while(consulta.moveToNext());

        }
        BD.close();


    }

    public void backToMenu(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}