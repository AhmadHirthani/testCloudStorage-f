package com.example.cloud_storage;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//
//public class MainActivity extends AppCompatActivity implements View.OnClickListener{
//
//    private static final int PICK_IMAGE_REQUEST = 55 ;
//    Button buttonChoose;
//    Button buttonUpload;
//    ImageView imageView;
//
//    Uri filePath;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        buttonChoose=findViewById(R.id.buttonChoose);
//        buttonUpload=findViewById(R.id.buttonUpload);
//
//        imageView.findViewById(R.id.imageView);
//
//        buttonChoose.setOnClickListener(this);
//        buttonUpload.setOnClickListener(this);
//    }
//
//    private void pickFile(){
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent.createChooser(intent,"pick an image"),PICK_IMAGE_REQUEST);
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
//            filePath =  data.getData();
//            imageView.setImageURI(filePath);
//        }
//    }
//
//    @Override
//    public void onClick(View view) {
//        if(view == buttonChoose ){
//            //run pick file function
//            pickFile();
//        }
//        else if(view == buttonUpload){
//            //run upload function
//        }
//    }
//}

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int PICK_IMAGE_REQUEST = 234;
    Button buttonChoose,buttonUpload;
    ImageView imageView;
    Uri filePath;
    StorageReference firebaseStorage;
    String x;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView=findViewById(R.id.imageView);
        buttonChoose=findViewById(R.id.buttonChoose);
        buttonUpload=findViewById(R.id.buttonUpload);
        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);

        firebaseStorage = FirebaseStorage.getInstance().getReference();
    }


    private void showFileChooser(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent.createChooser(intent,"Select an image"),PICK_IMAGE_REQUEST);
    }


    private void uploadFile(){
        StorageReference fileRef = firebaseStorage.child("images/img");
        fileRef.putFile(filePath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d("TAG", "Success: ");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "Failure: " +e);

                    }
                });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data != null && data.getData() != null){
            filePath=data.getData();
            imageView.setImageURI(filePath);

        }
    }


    @Override
    public void onClick(View view) {
        if(view==buttonChoose){
            //open file chooser
            showFileChooser();
        }
        else if (view==buttonUpload){
            //
            uploadFile();
        }
    }
}
