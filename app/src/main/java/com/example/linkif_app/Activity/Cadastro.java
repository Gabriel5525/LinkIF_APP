package com.example.linkif_app.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.linkif_app.MainActivity;
import com.example.linkif_app.Model.RegistroModel;
import com.example.linkif_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class Cadastro extends AppCompatActivity {

    private Button button_dificuldades;
    private Button button_facilidades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        button_dificuldades = findViewById(R.id.button_dificuldades);
        button_facilidades = findViewById(R.id.button_facilidades);

        button_facilidades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistroModel registro = new RegistroModel();
                registro.removerDisicplinasFacilidade();

                Intent intent = new Intent(Cadastro.this, Cadastro_Facilidades.class);
                startActivity(intent);
                finish();
            }
        });

        button_dificuldades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RegistroModel registro = new RegistroModel();
                registro.removerDisicplinasDificuldade();

                Intent intent = new Intent(Cadastro.this, Cadastro_Dificuldades.class);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Cadastro.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}