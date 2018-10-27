package com.example.user.notic;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences mPref;
    private SharedPreferences.Editor mPrefEdit;
    Switch CallSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        CallSwitch = (Switch) findViewById(R.id.switch1);
        mPref = getSharedPreferences("setting", 0);
        mPrefEdit = mPref.edit();

        CallSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mPrefEdit.putString("switch","1");
                    mPrefEdit.commit();
                    makeNoti();
                } else {
                    mPrefEdit.putString("switch","0");
                    mPrefEdit.commit();
                    makeNoti();
                    NotificationManager notifiyMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notifiyMgr.cancel(0);
                }
            }
        });

        if(mPref.getString("switch","") == "1"){
            CallSwitch.setChecked(true);
        }else{
            CallSwitch.setChecked(false);
        }


    }

    void makeNoti() {
        Bitmap mLargeIconForNoti = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background);

        NotificationCompat.Builder mBuilder;

        mBuilder = new NotificationCompat.Builder(MainActivity.this).setSmallIcon(R.drawable.ic_launcher_background).setContentTitle("노티제목").setContentText("노티내용")
                .setDefaults(Notification.DEFAULT_SOUND)
                .setLargeIcon(mLargeIconForNoti)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setOngoing(true);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());
    }
}
