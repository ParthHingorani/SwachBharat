package com.teamhack.swachbharat.Feed;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.teamhack.swachbharat.Profile.User;
import com.teamhack.swachbharat.R;

import java.util.Date;

/**
 * Created by Rishi on 08-12-2016.
 */

public class NewFeedDialog extends Dialog implements View.OnClickListener {

    Context context;
    final static String FEED_CHILD="Feed";
    EditText et_title,et_content;
    Button bt_post,bt_cancel;
    ProgressDialog progressDialog;
    Button cameraButton;

    public NewFeedDialog(Context context) {
        super(context);
        this.context=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_new_feed);
        et_content= (EditText) findViewById(R.id.et_feed_content);
        et_title= (EditText) findViewById(R.id.et_feed_title);
        bt_post= (Button) findViewById(R.id.bt_feed_dialog_post);
        bt_cancel= (Button) findViewById(R.id.bt_feed_dialog_cancel);
        bt_cancel.setOnClickListener(this);
        bt_post.setOnClickListener(this);

        cameraButton = (Button) findViewById(R.id.cameraButton);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_feed_dialog_post:
                writeFeed();
                break;
            case R.id.bt_feed_dialog_cancel:
                dismiss();
                break;
        }
    }

    private void writeFeed() {
        if(validateForm()){
            showProgressBar();
            FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
            Feed feed=new Feed();
            feed.title=et_title.getText().toString();
            feed.content=et_content.getText().toString();
            feed.user=new User();
            feed.user.name=firebaseUser.getDisplayName();
            feed.user.email=firebaseUser.getEmail();
            feed.user.uid=firebaseUser.getUid();
            java.text.SimpleDateFormat simpleDateFormat=new java.text.SimpleDateFormat("dd/MM/yyyy  HH:mm");
            feed.time=simpleDateFormat.format(new Date());
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
    }

    private void hideProgressBar() {
        progressDialog.hide();
    }

    private void showProgressBar() {
        progressDialog=new ProgressDialog(context);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Posting . . .");
        progressDialog.show();
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
