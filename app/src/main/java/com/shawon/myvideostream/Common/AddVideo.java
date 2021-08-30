package com.shawon.myvideostream.Common;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.shawon.myvideostream.R;

public class AddVideo extends AppCompatActivity {
    ////////////////// Varriable //////////////

    VideoView videoView;
    TextView videoBrowse,videoUpload;
    Uri videoUri;
    EditText videoTitle;
    StorageReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_video);

        /////////////////// Hooks /////////////////

        videoView = findViewById(R.id.videoView);
        videoBrowse = findViewById(R.id.videoBrowse);
        videoUpload = findViewById(R.id.videoUpload);
        videoTitle = findViewById(R.id.videoTitle);

        /////////////////// Firebase Database /////////////
        reference = FirebaseStorage.getInstance().getReference();


        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        videoView.start();

        videoBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dexter.withContext(getApplicationContext())
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent = new Intent();
                                intent.setType("video/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(intent,101);
                            }

                            @Override public void onPermissionDenied(PermissionDeniedResponse response) {
                                Toast.makeText(AddVideo.this, "Permission denied", Toast.LENGTH_SHORT).show();
                            }
                            @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();



            }

        });
        videoUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proccesvideoupload();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == RESULT_OK){
            videoUri = data.getData();
            videoView.setVideoURI(videoUri);
        }


    }
    public void proccesvideoupload() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Media Uploader");
        pd.show();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        String fileExt = mimeTypeMap.getExtensionFromMimeType(getContentResolver().getType(videoUri));

        StorageReference storageReference = reference.child("myvideo/"+System.currentTimeMillis()+"."+fileExt);
        storageReference.putFile(videoUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        pd.dismiss();
                        videoUploaderModel videoUploaderModel = new videoUploaderModel(videoTitle.getText().toString(),uri.toString());

                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("myvideos");
                        databaseReference.child(databaseReference.push().getKey()).setValue(videoUploaderModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(AddVideo.this, "Success", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddVideo.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(AddVideo.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                float per = (100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                pd.setMessage("Uploaded: "+(int)per+"%");
            }
        });

                
    }
}