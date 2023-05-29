package com.example.projecto2jam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2_Nivel4 extends AppCompatActivity {

    TextView tv_nombre, tv_score;
    ImageView iv_Auno, iv_Ados, iv_vidas, iv_signo;
    EditText et_respuesta;
    MediaPlayer mp, mpGreat, mpBad;
    int score, numAleatorio_Uno, numAleatorio_Dos, resultado, vidas=3;
    String nombre_jugador, string_score, string_vidas;

    String numero[] = {"cero","uno","dos","tres","cuatro","cinco","seis","siete","ocho","nueve"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2_nivel4);

        Toast.makeText(this, "Nivel 4 - Toma de Decisiones", Toast.LENGTH_SHORT).show();
        tv_nombre = findViewById((R.id.textView_Nombre));
        tv_score = findViewById((R.id.textView_Score));
        iv_vidas = findViewById((R.id.imageView_Manzanas));
        iv_Auno = findViewById((R.id.imageView_NumeroUno));
        iv_Ados = findViewById((R.id.imageView_NumeroDos));
        iv_signo = findViewById(R.id.imageView_Signo);
        et_respuesta = findViewById((R.id.et_Resultado));

        nombre_jugador = getIntent().getStringExtra("jugador");
        tv_nombre.setText("Jugador: "+nombre_jugador);

        string_score= getIntent().getStringExtra("score");
        score = Integer.parseInt(string_score);
        tv_score.setText("Score: "+ score);

        string_vidas=getIntent().getStringExtra("vidas");
        vidas = Integer.parseInt(string_vidas);
        switch (vidas){
            case 3:
                iv_vidas.setImageResource(R.drawable.tresvidas);
                break;
            case 2:
                iv_vidas.setImageResource(R.drawable.dosvidas);
                break;
            case 1:
                iv_vidas.setImageResource(R.drawable.unavida);
                break;

        }

        mp = MediaPlayer.create(this,R.raw.goats);
        mp.start();
        mp.setLooping(true);

        setSupportActionBar(findViewById(R.id.myToolbar_Nivel1));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        mpGreat = MediaPlayer.create(this,R.raw.wonderful);
        mpBad = MediaPlayer.create(this,R.raw.bad);

        numeroAleatorio();

    }

    public void comparar(View vista) {
        String respuesta = et_respuesta.getText().toString();
        if(!respuesta.equals("")){
            int respuestaJugador = Integer.parseInt(respuesta);
            if(resultado == respuestaJugador){
                mpGreat.start();
                score++;
                tv_score.setText("Score: "+score);
            }else {
                mpBad.start();
                vidas--;
                switch (vidas){
                    case 3:
                        iv_vidas.setImageResource(R.drawable.tresvidas);
                        break;

                    case 2:
                        iv_vidas.setImageResource(R.drawable.dosvidas);
                        Toast.makeText(this, "Quedan 2 vidas", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        iv_vidas.setImageResource(R.drawable.unavida);
                        Toast.makeText(this, "Queda una vida", Toast.LENGTH_SHORT).show();
                        break;
                    case 0:
                        Toast.makeText(this, "Ha perdido todas las manzanas", Toast.LENGTH_SHORT).show();
                        mp.stop();
                        mp.release();
                        Intent intent = new Intent(this,MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }
            }
            baseDeDatos();
            et_respuesta.setText("");
            numeroAleatorio();// redibujar la pantalla
        }else{
            Toast.makeText(this,"Debe ingresar una respuesta",Toast.LENGTH_SHORT).show();
        }

    }


    private void numeroAleatorio() {
        if(score <= 39){
            numAleatorio_Uno = (int) (Math.random() * 10);
            numAleatorio_Dos = (int) (Math.random() * 10);


            // Si el numero 1 en las es mayou que cero y numero dos es menor o igual a 4 se Resta
            //Else se Suma
            if(numAleatorio_Uno > 0 && numAleatorio_Dos <= 4){
                resultado = numAleatorio_Uno + numAleatorio_Dos;
                iv_signo.setImageResource(R.drawable.adicion);
                for(int i = 0; i < numero.length; i++){
                    int id = getResources().getIdentifier(numero[i],"drawable",getPackageName());
                    if(numAleatorio_Uno == i){
                        iv_Auno.setImageResource(id);
                    }
                    if(numAleatorio_Dos == i){
                        iv_Ados.setImageResource(id);
                    }
                }
            }else {
                resultado = numAleatorio_Uno - numAleatorio_Dos;
                iv_signo.setImageResource(R.drawable.resta);
                for(int i = 0; i < numero.length; i++){
                    int id = getResources().getIdentifier(numero[i],"drawable",getPackageName());
                    if(numAleatorio_Uno == i){
                        iv_Auno.setImageResource(id);
                    }
                    if(numAleatorio_Dos == i){
                        iv_Ados.setImageResource(id);
                    }
                }
            }


        }else{
            Intent intent = new Intent(this, MainActivity2_Nivel4.class);
            string_score = String.valueOf(score);
            string_vidas = String.valueOf(vidas);
            intent.putExtra("jugador",nombre_jugador);
            intent.putExtra("score",string_score);
            intent.putExtra("vidas",string_vidas);
            mp.stop();
            mp.release();
            startActivity(intent);
            finish();
        }
    }

    public void baseDeDatos(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"BD",null,1);
        SQLiteDatabase BD = admin.getWritableDatabase();

        Cursor consulta = BD.rawQuery("select * " +
                "from puntaje " +
                "where score = " +
                "(select max(score) " +
                "from puntaje)",null);

        if(consulta.moveToFirst()){
            String tem_Nombre = consulta.getString(0);
            String temp_Score = consulta.getString(1);

            int bestScore = Integer.parseInt(temp_Score);

            if(score > bestScore){
                ContentValues modificacion = new ContentValues();
                modificacion.put("nombre",nombre_jugador);
                modificacion.put("score",score);
                BD.update("puntaje",modificacion,"score="+bestScore,null);
            }
        }else {
            ContentValues insertar = new ContentValues();
            insertar.put("nombre",nombre_jugador);
            insertar.put("score",score);
            BD.insert("puntaje",null,insertar);
        }
        BD.close();
    }

    @Override
    public  void onBackPressed(){

    }
}