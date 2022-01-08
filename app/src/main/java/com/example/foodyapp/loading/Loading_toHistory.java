package com.example.foodyapp.loading;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.example.foodyapp.Layout_History;
import com.example.foodyapp.R;
import com.example.foodyapp.activities.Activity_Login;
import com.example.foodyapp.activities.Notification;

import java.util.Date;

public class Loading_toHistory extends AppCompatActivity {

    Handler wtf = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_to_history);

        Intent intent = getIntent();
        String infoname = intent.getExtras().getString("info");
        int total = intent.getIntExtra("total", 0);

        int rand = (int) (30 + (Math.random() * 30));
        int finalnumberrate = Math.round(rand);

        TextView tvtimeship = (TextView) findViewById(R.id.timeship);
        tvtimeship.setText("Đơn hàng sẽ được giao trong " + finalnumberrate + " phút");
        sendNotification(finalnumberrate);

        //Auto intent to login
        wtf.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Loading_toHistory.this, Layout_History.class);
                intent.putExtra("info", infoname);
                intent.putExtra("total", total);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_back, R.anim.anim_back_2);
                finish();
            }
        }, 3000);
    }

    private void sendNotification(int timeship){

        android.app.Notification notification = new NotificationCompat.Builder(this, Notification.CHANNEL_ID)
                .setContentTitle("Đặt hàng thành công!")
                .setContentText("Đơn hàng sẽ được giao đến trong vòng " + timeship + " phút")
                .setSmallIcon(R.drawable.ic_baseline_check_circle_24)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null)
            notificationManager.notify(getNotificationID(), notification);
    }
    private int getNotificationID(){
        return (int) new Date().getTime();
    }
}