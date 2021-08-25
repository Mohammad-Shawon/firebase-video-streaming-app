package com.shawon.myvideostream;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.shawon.myvideostream.Common.AddVideo;

public class Dashboard extends AppCompatActivity {

    /////////////////// Varriable ///////////////
    FloatingActionButton videoAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        /////////////////// Hooks /////////////////
        videoAdd = findViewById(R.id.videoAdd);
        videoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddVideo.class));
            }
        });
    }
}