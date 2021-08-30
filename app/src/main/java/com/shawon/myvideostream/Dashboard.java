package com.shawon.myvideostream;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.shawon.myvideostream.Adapter.VideoAdapter;
import com.shawon.myvideostream.Common.AddVideo;
import com.shawon.myvideostream.Model.VideoModel;

public class Dashboard extends AppCompatActivity {

    /////////////////// Varriable ///////////////
    FloatingActionButton videoAdd;
    VideoAdapter adapter;
    ViewPager2 viewPager2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        /////////////////// Hooks /////////////////
        videoAdd = findViewById(R.id.videoAdd);
        viewPager2 = findViewById(R.id.viewPager);



        videoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddVideo.class));
            }
        });
        videoProccess();
    }
    public void videoProccess() {

        Query query = FirebaseDatabase.getInstance().getReference().child("myvideos");
        FirebaseRecyclerOptions<VideoModel> options =
                new FirebaseRecyclerOptions.Builder<VideoModel>()
                        .setQuery(query, VideoModel.class)
                        .build();
        adapter = new VideoAdapter(options);
        viewPager2.setAdapter(adapter);

    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}