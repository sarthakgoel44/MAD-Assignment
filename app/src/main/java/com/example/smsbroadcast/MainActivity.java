package com.example.smsbroadcast;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;
public class SmsBroadcastReceiver extends BroadcastReceiver {
    public static final String SMS_BUNDLE = "pdus";
    public void onReceive(Context context, Intent intent) {
        Bundle intentExtras = intent.getExtras();
        if (intentExtras != null) {
            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
            String smsBody="",address="";
            for (int i = 0; i < sms.length; ++i) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);
                smsBody = smsMessage.getMessageBody().toString();
                address = smsMessage.getOriginatingAddress();
            }
//this will update the UI with message
            Intent activityIntent = new Intent(context,SMSDialog.class);
            activityIntent.putExtra("msg",smsBody);
            activityIntent.putExtra("from",address);
            activityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(activityIntent);
        } }}
SMSDialog
        package com.example.smsnotifier;
        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;
public class SMSDialog extends AppCompatActivity {
    TextView tvFrom;
    TextView tvMsg;
    Button click;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        tvFrom = (TextView)findViewById(R.id.tvFrom);
        tvMsg = (TextView)findViewById(R.id.tvMsg);
        click = (Button)findViewById(R.id.btnClick);
        Intent intent = getIntent();
        String txt = intent.getStringExtra("msg");
        tvMsg.setText(txt);
        tvFrom.setText("From:"+getIntent().getStringExtra("from"));
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            } }); }}
build.gradle
        apply plugin: 'com.android.application'
        android {
        compileSdkVersion 22
        buildToolsVersion "22.0.0"
        defaultConfig {
        applicationId "com.example.smsnotifier"
        minSdkVersion 17
        targetSdkVersion 23
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