package com.example.linkif_app.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.linkif_app.MainActivity;
import com.example.linkif_app.Model.UserModel;
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

public class Dificuldades extends AppCompatActivity {

    private Spinner buttonDisciplinaPickerDif;
    private ListView lstDisciplDif;
    private ArrayList<String> list ;
    private ArrayList<String> listIdAlunos ;
    private ArrayList<String> listSpinner ;
    private ArrayAdapter adapter ;
    private ArrayAdapter adapterSpinner ;
    private String item_selecionado ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dificuldades);
        buttonDisciplinaPickerDif = findViewById(R.id.buttonDisciplinaPickerDif);
        buttonDisciplinaPickerDif.setAdapter(adapter);
        lstDisciplDif = findViewById(R.id.lstDisciplDif);

        list = new ArrayList<>();
        listSpinner = new ArrayList<>();
        listIdAlunos = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.lista_alunos_dif, list);
        adapterSpinner = new ArrayAdapter<String>(this, R.layout.lista_dif, listSpinner);

        initDisciplinas();
        buttonDisciplinaPickerDif.setAdapter(adapterSpinner);
        lstDisciplDif.setAdapter(adapter);

        buttonDisciplinaPickerDif.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                item_selecionado = buttonDisciplinaPickerDif.getSelectedItem().toString();

                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("disciplinas_dificuldade");
                 reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        listIdAlunos.clear();
                        for(DataSnapshot snapshot : datasnapshot.getChildren()){
                            if (item_selecionado.equals(snapshot.child("nome_disciplina").getValue().toString())){
                                listIdAlunos.add(snapshot.child("id_aluno").getValue().toString());
                            }
                        }
                        if (listIdAlunos.isEmpty()){
                            list.clear();
                            list.add("Não existem alunos com dificuldades na disciplina");
                        }
                        else{
                            buscaUsuarios();
                        }

                        Collections.sort(list);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void buscaUsuarios() {
        DatabaseReference referenceAgendamento = FirebaseDatabase.getInstance().getReference().child("usuarios");
        referenceAgendamento.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                list.clear();
                for(DataSnapshot snapshot : datasnapshot.getChildren()){
                    for (int i=0; i<listIdAlunos.size();i++){
                        if (listIdAlunos.get(i).equals(snapshot.child("id").getValue().toString())){
                            list.add(snapshot.child("nome").getValue().toString() + " - " + snapshot.child("email").getValue().toString());
                        }
                    }

                }
                Collections.sort(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initDisciplinas() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("disciplinas");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                listSpinner.clear();
                for(DataSnapshot snapshot : datasnapshot.getChildren()){
                    listSpinner.add(snapshot.child("nome").getValue().toString());
                }
                Collections.sort(listSpinner);
                adapterSpinner.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Dificuldades.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}