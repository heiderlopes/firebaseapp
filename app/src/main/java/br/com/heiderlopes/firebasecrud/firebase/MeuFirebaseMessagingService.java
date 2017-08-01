package br.com.heiderlopes.firebasecrud.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import br.com.heiderlopes.firebasecrud.MainActivity;
import br.com.heiderlopes.firebasecrud.R;

public class MeuFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        // Verifica se a mensagem contém uma carga útil de dados.
        if (remoteMessage.getData().size() > 0) {
            showNotification(remoteMessage.getData().get("descricao"), remoteMessage.getData().get("descricao"));
        }

        // Verifica se a mensagem contem payload.
        if (remoteMessage.getNotification() != null) {

        }
    }

    private void showNotification(String titulo, String mensagem) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(titulo)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(mensagem)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}