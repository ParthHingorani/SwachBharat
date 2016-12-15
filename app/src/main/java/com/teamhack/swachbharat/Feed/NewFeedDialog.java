package com.teamhack.swachbharat.Feed;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.teamhack.swachbharat.MainActivity;
import com.teamhack.swachbharat.Profile.User;
import com.teamhack.swachbharat.R;

import java.io.ByteArrayOutputStream;
import java.util.Date;

/**
 * Created by Rishi on 08-12-2016.
 */

public class NewFeedDialog extends Dialog implements View.OnClickListener {

    Context context;
    final static String FEED_CHILD="Feed";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    EditText et_title,et_content;
    Button bt_post,bt_cancel;
    ProgressDialog progressDialog;
    Button cameraButton;
    ImageView img_cam;
    Boolean img_flag=false;
    Bitmap imageBitmap;
    Feed feed;

    public NewFeedDialog(Context context) {
        super(context);
        this.context=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_new_feed);
        img_cam= (ImageView) findViewById(R.id.clickedImage);
        et_content= (EditText) findViewById(R.id.et_feed_content);
        et_title= (EditText) findViewById(R.id.et_feed_title);
        bt_post= (Button) findViewById(R.id.bt_feed_dialog_post);
        bt_cancel= (Button) findViewById(R.id.bt_feed_dialog_cancel);
        bt_cancel.setOnClickListener(this);
        bt_post.setOnClickListener(this);

        cameraButton = (Button) findViewById(R.id.cameraButton);
        cameraButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cameraButton:
                dispatchTakePictureIntent();
                break;
            case R.id.bt_feed_dialog_post:
                writeFeed();
                break;
            case R.id.bt_feed_dialog_cancel:
                dismiss();
                break;
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            //((MainActivity)getOwnerActivity()).saveFeedDialogState(et_title.getText().toString(),et_content.getText().toString());
            MainActivity.saveFeedDialogState(et_title.getText().toString(),et_content.getText().toString());
            ((MainActivity)context).startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            dismiss();
        }
    }

    public void setData(String title,String content,Intent data){
        et_content.setText(content);
        et_title.setText(title);
        Bundle extras = data.getExtras();
        imageBitmap = (Bitmap) extras.get("data");
        img_cam.setImageBitmap(imageBitmap);
        img_flag=true;

    }

    private void writeFeed() {
        if(validateForm()){
            showProgressBar();
            FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
            feed=new Feed();
            feed.title=et_title.getText().toString();
            feed.content=et_content.getText().toString();
            feed.user=new User();
            feed.user.name=firebaseUser.getDisplayName();
            feed.user.email=firebaseUser.getEmail();
            feed.user.uid=firebaseUser.getUid();
            feed.imgurl="NIL";
            java.text.SimpleDateFormat simpleDateFormat=new java.text.SimpleDateFormat("dd/MM/yyyy  HH:mm");
            feed.time=simpleDateFormat.format(new Date());
            if (img_flag){
                uploadImage();
            }else {
                insertIntoDB();
            }

        }
    }

    private void uploadImage() {
        StorageReference imageRef= FirebaseStorage.getInstance().getReference().child("images").child(feed.title+".jpg");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask=imageRef.putBytes(data);
        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                progressDialog.setProgress((int) progress);
            }
        });
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "There was some error your feed wasn't posted.", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                feed.imgurl=taskSnapshot.getDownloadUrl().toString();
                insertIntoDB();
            }
        });
    }


    private void insertIntoDB() {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child(FEED_CHILD);
        feed.key=databaseReference.push().getKey();
        databaseReference.child(feed.key).setValue(feed).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                hideProgressBar();
                if(task.isSuccessful()){
                    Toast.makeText(context, "Feed posted successfully", Toast.LENGTH_SHORT).show();
                    dismiss();
                }else {
                    Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void hideProgressBar() {
        progressDialog.hide();
    }

    private void showProgressBar() {
        if(img_flag){
            progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setMessage("Posting . . .");
            progressDialog.show();
        }else {
            progressDialog = new ProgressDialog(context);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Posting . . .");
            progressDialog.show();
        }
    }

    private boolean validateForm() {
        if(et_title.getText().toString().length()>0){
            if (et_content.getText().toString().length()>0){
                if(et_content.getText().toString().length()<200){
                    return true;
                }else {
                    et_content.setError("Max Limit 200 chars");
                }
            }else {
                et_content.setError("Please enter something");
            }
        }else {
            et_title.setError("Please enter the Title");
        }
        return false;
    }
}
