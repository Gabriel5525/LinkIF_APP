package com.example.linkif_app.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.linkif_app.MainActivity;
import com.example.linkif_app.Model.RegistroModel;
import com.example.linkif_app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class Cadastro_Dificuldades extends AppCompatActivity {

    private LinearLayout layout_cadastro;
    private CheckBox checkBox;
    private Button btn_Cadastro_dif;
    private ArrayList<String> list;
    private ArrayList<String> listCheckBox;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_dificuldades);
        layout_cadastro = findViewById(R.id.layout_cadastro_dif);
        btn_Cadastro_dif = findViewById(R.id.btn_Cadastro_dif);

        list = new ArrayList<>();
        listCheckBox = new ArrayList<>();

        getDisciplinasToList();

        btn_Cadastro_dif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                RegistroModel registro = new RegistroModel();

                for(int i=0;i<listCheckBox.size();i++){
                    registro.setNome_disciplina(listCheckBox.get(i));
                    registro.setId_aluno(currentUser.getUid());
                    registro.setDisciplinaAtiva("X");
                    registro.salvarDificuldade();
                }

                Toast.makeText(Cadastro_Dificuldades.this, "Cadastro concluído com sucesso", Toast.LENGTH_SHORT).show();
                abrirTelaPrincipal();
            }
        });
    }

    private void abrirTelaPrincipal() {
        Intent intent = new Intent(Cadastro_Dificuldades.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void geraCheckBox() {
        for(int i=0;i<list.size();i++){
            checkBox = new CheckBox(getApplicationContext());

            LinearLayout.LayoutParams checkParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            checkParams.setMargins(20, 10, 10, 20);
            checkParams.gravity = Gravity.START;

            checkBox.setId(i);
            checkBox.setText(list.get(i));
            layout_cadastro.addView(checkBox, checkParams);

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        listCheckBox.add(buttonView.getText().toString());
                    }
                    else{
                        listCheckBox.remove(listCheckBox.indexOf(buttonView.getText().toString()));
                    }
                }
            });
        }
    }

    private void getDisciplinasToList() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("disciplinas");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                list.clear();
                for(DataSnapshot snapshot : datasnapshot.getChildren()){
                    list.add(snapshot.child("nome").getValue().toString());
                }
                Collections.sort(list);
                geraCheckBox();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Cadastro_Dificuldades.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}