package com.example.alarm;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.GregorianCalendar;
public class AlarmExample extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_example);
        final EditText mEtTime = (EditText)findViewById(R.id.editText);
        Button btnClick = (Button)findViewById(R.id.btnSchedule);
        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(mEtTime.getText())) {
                    Toast.makeText(AlarmExample.this, "Please enter the no. of seconds you want to be reminded
                            after", Toast.LENGTH_SHORT).show();
                    return;
                }
                try{
                    int secs = Integer.parseInt(mEtTime.getText().toString());
                    Long time= new GregorianCalendar().getTimeInMillis()+secs*1000;
                    Intent intentAlarm= new Intent(AlarmExample.this, AlarmReceiver.class);
                    AlarmManager alarmManager =(AlarmManager)
                            getSystemService(Context.ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, time,
                            PendingIntent.getBroadcast(AlarmExample.this, 1, intentAlarm,
                                    PendingIntent.FLAG_UPDATE_CURRENT));
                    Toast.makeText(AlarmExample.this, "Alarm Scheduled for secs"+secs,
                            Toast.LENGTH_LONG).show();
                }catch(Exception e){
                    Toast.makeText(AlarmExample.this, "Please enter a valid input",
                            Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } } }); }}
AlarmPlayer.java
        package com.example.alarm;
        import android.app.AlarmManager;
        import android.app.PendingIntent;
        import android.content.Context;
        import android.content.Intent;
        import android.content.res.AssetFileDescriptor;
        import android.media.MediaPlayer;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.text.TextUtils;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;
        import java.util.GregorianCalendar;
public class AlarmPlayer extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_player);
        mediaPlayer = new MediaPlayer();
        Button btnClick = (Button)findViewById(R.id.btnOk);
        playAudio();
        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                finish();
            } }); }
    private void playAudio() {
        try {
            AssetFileDescriptor descriptor = getAssets().openFd("happy.mp3");
            mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(),
                    descriptor.getLength());
            descriptor.close();
            mediaPlayer.prepare();
            mediaPlayer.setVolume(1f, 1f);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        } }}
AlarmReciever
        package com.example.alarm;
        import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.telephony.SmsManager;
        import android.widget.Toast;
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
// TODO Auto-generated method stub
        Intent newIntent = new Intent(context,AlarmPlayer.class);
        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(newIntent);
    }}
build.gradle
        apply plugin: 'com.android.application'
        android {
        compileSdkVersion 22
        buildToolsVersion "22.0.0"
        defaultConfig {
        applicationId "com.example.alarm"
        minSdkVersion 17
        targetSdkVersion 22
        versionCode 1
        versionName "1.0"
        }
        buildTypes {
        release {
        minifyEnabled false
        proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        } }}
        dependencies {
        compile fileTree(dir: 'libs', include: ['*.jar'])
        compile 'com.android.support:appcompat-v7:22.1.0'
        }