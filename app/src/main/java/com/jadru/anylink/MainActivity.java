package com.jadru.anylink;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    String linktxt;
    SharedPreferences pref;
    TextView tv;
    Switch sw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tv=(TextView)findViewById(R.id.tv);
        sw=(Switch)findViewById(R.id.sw) ;
        pref = getSharedPreferences("pref", MODE_PRIVATE);
        sw.setChecked(pref.getBoolean("onoff", true));
        tv.setText(pref.getString("link", "http://www.google.com"));
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Test the action", Snackbar.LENGTH_SHORT).show();
                String link = pref.getString("link","http://www.google.com");
                boolean onoff = pref.getBoolean("onoff", true);
                if(!onoff){
                    Intent it  = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                    startActivity(it);
                }else{
                    CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                    builder.setToolbarColor(getResources().getColor(android.R.color.black));
                    CustomTabsIntent customTabsIntent = builder.build();
                    customTabsIntent.launchUrl(MainActivity.this, Uri.parse(link));
                }
            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

                alert.setTitle("Link");
                alert.setMessage("Set your link");

                // Set an EditText view to get user inputt
                final EditText input = new EditText(MainActivity.this);
                input.setSingleLine();
                FrameLayout container = new FrameLayout(MainActivity.this);
                FrameLayout.LayoutParams params = new  FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.leftMargin= 160; // remember to scale correctly
                input.setLayoutParams(params);
                input.setText(pref.getString("link", "http://www.google.com"));
                alert.setView(input);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        linktxt = input.getText().toString();
                        String txt = linktxt.toString();
                        if(txt.equals("")){
                            txt = pref.getString("link", "http://www.google.com");
                        }if(!(txt.startsWith("http"))){
                            txt = "http://" + txt;
                        }
                        // Do something with value!
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("link", txt);
                        editor.apply();
                        tv.setText(pref.getString("link", "http://www.google.com"));
                    }
                });
                alert.show();
            }
        });
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = pref.edit();
                if (isChecked) {
                    editor.putBoolean("onoff" ,isChecked);
                    editor.apply();
                    // do something when check is selected
                } else {
                    editor.putBoolean("onoff" ,isChecked);
                    editor.apply();
                }
            }
        });
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d("TestAppActivity", "onStop");
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("TestAppActivity", "onPause");
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("onPostCreate", "onDestroy");
        finish();
    }
}