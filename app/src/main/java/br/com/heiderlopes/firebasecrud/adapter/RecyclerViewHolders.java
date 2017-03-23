package br.com.heiderlopes.firebasecrud.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.List;
import br.com.heiderlopes.firebasecrud.R;
import br.com.heiderlopes.firebasecrud.model.Tarefa;

public class RecyclerViewHolders extends RecyclerView.ViewHolder{
    private static final String TAG = RecyclerViewHolders.class.getSimpleName();

    public TextView tvTarefa;
    public Button btApagar;
    private List<Tarefa> tarefas;

    public RecyclerViewHolders(final View itemView, final List<Tarefa> tarefas) {
        super(itemView);
        this.tarefas = tarefas;
        tvTarefa = (TextView)itemView.findViewById(R.id.tvTarefa);
        btApagar = (Button) itemView.findViewById(R.id.btApagar);

        btApagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String taskTitle = tarefas.get(getAdapterPosition()).getDescricao();

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

                Query query = ref.orderByChild("tarefas").equalTo(taskTitle);

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                            appleSnapshot.getRef().removeValue();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, "onCancelled", databaseError.toException());
                    }
                });
            }
        });
    }
}