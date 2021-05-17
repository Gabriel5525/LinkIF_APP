package com.example.linkif_app.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.linkif_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class Dificuldades extends AppCompatActivity {

    private Button buttonDisciplinaPickerDif;
    private ListView lstDisciplDif;
    private ArrayList<String> list ;
    private ArrayAdapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dificuldades);
        
        initDisciplinas();

        buttonDisciplinaPickerDif = findViewById(R.id.buttonDisciplinaPickerDif);
        lstDisciplDif = findViewById(R.id.lstDisciplDif);

    }

    private void initDisciplinas() {

        list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.lista_dif, list);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("disciplinas");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                list.clear();
                for(DataSnapshot snapshot : datasnapshot.getChildren()){
                    list.add(snapshot.getValue().toString());
                }
                Collections.sort(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void openDisciplinaPicker(View view) {

    }
}