package com.example.projecto2jam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    EditText et_nombre;
    ImageView iv_personaje;
    TextView tv_bestScore;
    Button btn_jugar;

    private MediaPlayer mp;

    int numeroAleatorio = (int) (Math.random() * 10);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_jugar = findViewById(R.id.btn_jugar);
        et_nombre = findViewById(R.id.txt_Nombre);
        tv_bestScore = findViewById(R.id.tv_bestScore);
        iv_personaje = findViewById(R.id.imageView_Personaje);

        setSupportActionBar(findViewById(R.id.myToolbar));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"BD",null,1);
        SQLiteDatabase BD = admin.getWritableDatabase();

        Cursor consulta = BD.rawQuery("select * " +
                "from puntaje " +
                "where score = " +
                "(select max(score) " +
                "from puntaje)",null);
        if(consulta.moveToFirst()){
            String temp_nombre = consulta.getString(0);
            String temp_score = consulta.getString(1);
            tv_bestScore.setText("Record: "+ temp_score+ " de "+ temp_nombre);

        }
        BD.close();

        mp = MediaPlayer.create(this, R.raw.alphabet_song);
        mp.start();
        mp.setLooping(true);

        int id;

        switch (numeroAleatorio){
            case 0: case 10:
                id = getResources().getIdentifier("mango","drawable",getPackageName());
                iv_personaje.setImageResource(id);
                break;
                //Hacerlo para el resto de casos
            case 1: case 9:
                id = getResources().getIdentifier("fresa","drawable",getPackageName());
                iv_personaje.setImageResource(id);
                break;
            case 2: case 8:
                id = getResources().getIdentifier("manzana","drawable",getPackageName());
                iv_personaje.setImageResource(id);
                break;
            case 3: case 7:
                id = getResources().getIdentifier("sandia","drawable",getPackageName());
                iv_personaje.setImageResource(id);
                break;
            case 4: case 5: case 6:
                id = getResources().getIdentifier("uva","drawable",getPackageName());
                iv_personaje.setImageResource(id);
                break;
        }
    }

    public void jugar(View view){
        String nombre = et_nombre.getText().toString();

        if(!nombre.equals("")){
            mp.stop();
            mp.release();
            Intent intent = new Intent(this,MainActivity2_Nivel1.class);
            intent.putExtra("jugador",nombre);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this, "Debe escribir su nombre",Toast.LENGTH_SHORT).show();
            et_nombre.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
            imm.showSoftInput(et_nombre,InputMethodManager.SHOW_IMPLICIT);
        }

    }

    public void highscore(View view){
        Intent intent = new Intent(this,HighScoreActivity.class);
        startActivity(intent);
        finish();
    }

}