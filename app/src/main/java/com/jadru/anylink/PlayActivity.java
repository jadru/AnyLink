package com.jadru.anylink;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        String link = pref.getString("link","http://www.google.com");
        boolean onoff = pref.getBoolean("onoff", true);
        if(!onoff){
            Intent it  = new Intent(Intent.ACTION_VIEW,Uri.parse(link));
            startActivity(it);
        }else{
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            builder.setToolbarColor(getResources().getColor(android.R.color.black));
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.launchUrl(this, Uri.parse(link));
        }
        finish();
    }
}
