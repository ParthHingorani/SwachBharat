package com.teamhack.swachbharat.Social;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
 * Created by ParthHingorani on 15-12-2016.
 */

public class SocialDialog extends Dialog implements View.OnClickListener {

    Context context;
    final static String SOCIAL_CHILD="Social";
    Button bt_post,bt_cancel;
    String lat,lng;
    ProgressDialog progressDialog;
    Social social;
    RadioGroup radiogroup;
    EditText detail;

    public SocialDialog(Context context) {
        super(context);
        this.context=context;
        this.lat = lat;
        this.lng = lng;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_social);
        bt_post= (Button) findViewById(R.id.postSocial);
        bt_cancel= (Button) findViewById(R.id.cancelSocial);
        radiogroup= (RadioGroup) findViewById(R.id.radiogroup_share);
        detail= (EditText) findViewById(R.id.socialDetails);
        bt_cancel.setOnClickListener(this);
        bt_post.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.postSocial:
                writeSocial();
                break;
            case R.id.cancelSocial:
                dismiss();
                break;
        }
    }

    public void setData(String details,Intent data){
        detail.setText(details);
    }

    public String getCategory() {
        int id=radiogroup.getCheckedRadioButtonId();
        RadioButton r_bt= (RadioButton) findViewById(id);
        return r_bt.getText().toString();
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

    private boolean validateFrom()  {
        if (detail.getText().toString().length()<200)
            return true;
        else
            detail.setError("Please enter details.");
        return false;
    }

    private void writeSocial()  {
        if (validateFrom()) {

            showProgressBar();
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference socialReference = FirebaseDatabase.getInstance().getReference().child(SOCIAL_CHILD);
            social = new Social();

            social.latitude = lat;
            social.longitude = lng;
            social.category=getCategory();
            social.socialDetail=detail.getText().toString();
            java.text.SimpleDateFormat simpleDateFormat=new java.text.SimpleDateFormat("dd/MM/yyyy  HH:mm");
            social.time = simpleDateFormat.format(new Date());

            social.user = new User();
            social.user.name = firebaseUser.getDisplayName();
            social.user.email = firebaseUser.getEmail();
            social.user.uid = firebaseUser.getUid();

            insertIntoDB();
        }
    }

    private void insertIntoDB() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(SOCIAL_CHILD);
        social.key = databaseReference.push().getKey();
        databaseReference.child(social.key).setValue(social).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                hideProgressBar();
                if(task.isSuccessful()){
                    Toast.makeText(context, "Posted successfully", Toast.LENGTH_SHORT).show();
                    dismiss();
                }else {
                    Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
