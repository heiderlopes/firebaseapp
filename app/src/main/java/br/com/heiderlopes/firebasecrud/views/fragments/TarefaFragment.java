package br.com.heiderlopes.firebasecrud.views.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.heiderlopes.firebasecrud.R;
import br.com.heiderlopes.firebasecrud.adapter.RecyclerViewAdapter;
import br.com.heiderlopes.firebasecrud.model.Tarefa;

public class TarefaFragment extends Fragment {

    private RecyclerView rvTarefas;

    private RecyclerViewAdapter recyclerViewAdapter;
    private EditText etTarefa;
    private Button btAdd;

    private DatabaseReference databaseReference;
    private List<Tarefa> tarefas;

    public TarefaFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tarefa, container, false);

        tarefas = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("tarefas");

        etTarefa = (EditText) v.findViewById(R.id.etTarefa);
        rvTarefas = (RecyclerView)v.findViewById(R.id.rvTarefas);
        btAdd = (Button) v.findViewById(R.id.btAdd);

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String descricao = etTarefa.getText().toString();

                if(TextUtils.isEmpty(descricao)){
                    Toast.makeText(getContext(), "Informe uma tarefa", Toast.LENGTH_LONG).show();
                    return;
                }

                Tarefa taskObject = new Tarefa(descricao);
                databaseReference.push().setValue(taskObject);
                etTarefa.setText("");

            }
        });

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getAll(dataSnapshot);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getAll(dataSnapshot);
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                delete(dataSnapshot);
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return v;
    }
    private void getAll(DataSnapshot dataSnapshot){
        for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
            String descricao = singleSnapshot.getValue(String.class);
            tarefas.add(new Tarefa(descricao));
        }
        recyclerViewAdapter = new RecyclerViewAdapter(getContext(), tarefas);
        rvTarefas.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTarefas.setAdapter(recyclerViewAdapter);
    }

    private void delete(DataSnapshot dataSnapshot){
        for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
            String taskTitle = singleSnapshot.getValue(String.class);
            for(int i = 0; i < tarefas.size(); i++){
                if(tarefas.get(i).getDescricao().equals(taskTitle)){
                    tarefas.remove(i);
                }
            }
            recyclerViewAdapter.notifyDataSetChanged();
            recyclerViewAdapter = new RecyclerViewAdapter(getContext(), tarefas);
            rvTarefas.setAdapter(recyclerViewAdapter);
        }
    }
}
