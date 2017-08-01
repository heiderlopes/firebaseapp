package br.com.heiderlopes.firebasecrud.firebase;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MeuFirebaseInstanceIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        // Obter o novo InstanceID
        String firebaseToken = FirebaseInstanceId.getInstance().getToken();
        //String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String uid = FirebaseInstanceId.getInstance().getToken();

        FirebaseDatabase.getInstance().getReference().child("utilizadores/"+uid+"/FirebaseToken")
                .setValue(firebaseToken);

    }
}