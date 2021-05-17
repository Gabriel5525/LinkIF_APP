package com.example.linkif_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.linkif_app.Activity.Cadastro;
import com.example.linkif_app.Activity.Dificuldades;
import com.example.linkif_app.Activity.Facilidades;
import com.example.linkif_app.Activity.Login;
import com.example.linkif_app.Activity.Register;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private Button button_cadastro;
    private Button button_facilidades;
    private Button button_dificuldades;
    private Button button_sair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_cadastro = findViewById(R.id.button_cadastro);
        button_facilidades = findViewById(R.id.button_facilidades);
        button_dificuldades = findViewById(R.id.button_dificuldades);
        button_sair = findViewById(R.id.button_sair);

        button_facilidades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Facilidades.class);
                startActivity(intent);
                finish();
            }
        });

        button_cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Cadastro.class);
                startActivity(intent);
                finish();
            }
        });

        button_dificuldades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Dificuldades.class);
                startActivity(intent);
                finish();
            }
        });

        button_sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null){
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish();
        }
    }
}