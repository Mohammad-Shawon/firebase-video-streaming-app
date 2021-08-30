package com.shawon.myvideostream.Adapter;

import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.shawon.myvideostream.Model.VideoModel;
import com.shawon.myvideostream.R;

import org.jetbrains.annotations.NotNull;

public class VideoAdapter extends FirebaseRecyclerAdapter<VideoModel,VideoAdapter.viewHolder> {

    public VideoAdapter(@NonNull @NotNull FirebaseRecyclerOptions<VideoModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull viewHolder holder, int position, @NonNull @NotNull VideoModel model) {
        holder.setVideo(model);
    }

    @NonNull
    @NotNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlevideoview,parent,false);
        return new viewHolder(view);
    }

    class viewHolder extends RecyclerView.ViewHolder {
        VideoView videoView;
        TextView title;
        ProgressBar pb;

        public viewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            videoView = itemView.findViewById(R.id.videoView);
            title = itemView.findViewById(R.id.vTitle);
            pb = itemView.findViewById(R.id.progressBar);

        }

        void setVideo(VideoModel obj) {

            videoView.setVideoPath(obj.getVideoUrl());
            title.setText(obj.getVideoTitle());

            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    pb.setVisibility(View.INVISIBLE);
                    mp.start();
                }
            });
            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.start();
                }
            });

        }

    }
}
