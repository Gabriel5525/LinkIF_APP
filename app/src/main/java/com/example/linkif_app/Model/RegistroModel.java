package com.example.linkif_app.Model;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;

public class RegistroModel {

    private String id;
    private String id_aluno;
    private String nome_disciplina;
    private String disciplinaAtiva;

    public RegistroModel(String id, String id_aluno, String nome_disciplina) {
        this.id = id;
        this.id_aluno = id_aluno;
        this.nome_disciplina = nome_disciplina;
        this.disciplinaAtiva = disciplinaAtiva;
    }

    public String getDisciplinaAtiva() {
        return disciplinaAtiva;
    }

    public void setDisciplinaAtiva(String disciplinaAtiva) {
        this.disciplinaAtiva = disciplinaAtiva;
    }

    public RegistroModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_aluno() {
        return id_aluno;
    }

    public void setId_aluno(String id_aluno) {
        this.id_aluno = id_aluno;
    }

    public String getNome_disciplina() {
        return nome_disciplina;
    }

    public void setNome_disciplina(String nome_disciplina) {
        this.nome_disciplina = nome_disciplina;
    }

    public  void salvarDificuldade(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("disciplinas_dificuldade").push().setValue(this);
    }
    public  void removerDisicplinasDificuldade(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        ArrayList<String> list  = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("disciplinas_dificuldade");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                list.clear();
                RegistroModel registro = new RegistroModel();
                for(DataSnapshot snapshot : datasnapshot.getChildren()) {
                    if (currentUser.getUid().equals(snapshot.child("id_aluno").getValue().toString())){
                        snapshot.getRef().removeValue();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public  void removerDisicplinasFacilidade(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        ArrayList<String> list  = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("disciplinas_facilidade");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                list.clear();
                RegistroModel registro = new RegistroModel();
                for(DataSnapshot snapshot : datasnapshot.getChildren()) {
                    if (currentUser.getUid().equals(snapshot.child("id_aluno").getValue().toString())){
                        snapshot.getRef().removeValue();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public  void salvarFacilidade(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("disciplinas_facilidade").push().setValue(this);
    }
}
