package com.teamhack.swachbharat.Share;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
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
 * Created by Rishi on 12-12-2016.
 */

public class ShareDialog extends Dialog implements View.OnClickListener {

    private static final String SHARE_CHILD = "Share";
    Button bt_ok,bt_cancel;
    String lat,lng;
    Context context;
    ProgressDialog progressDialog;
    RadioGroup radiogroup;

    public ShareDialog(Context context,String lat,String lng) {
        super(context);
        this.context=context;
        this.lat=lat;
        this.lng=lng;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_share);
        bt_cancel= (Button) findViewById(R.id.bt_share_dialog_cancel);
        bt_ok= (Button) findViewById(R.id.bt_share_dialog_ok);
        bt_ok.setOnClickListener(this);
        bt_cancel.setOnClickListener(this);
        radiogroup= (RadioGroup) findViewById(R.id.radiogroup_share);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.bt_share_dialog_cancel:
                dismiss();
                break;
            case R.id.bt_share_dialog_ok:
                writeShare();
                dismiss();
                break;
        }

    }

    private void writeShare() {
        showProgressBar();
        DatabaseReference shareReference= FirebaseDatabase.getInstance().getReference().child(SHARE_CHILD);
        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        Share share=new Share();
        share.latitude=lat;
        share.longitude=lng;
        share.user= new User();
        share.user.name= firebaseUser.getDisplayName();
        share.user.email=firebaseUser.getEmail();
        share.user.uid=firebaseUser.getUid();
        java.text.SimpleDateFormat simpleDateFormat=new java.text.SimpleDateFormat("dd/MM/yyyy  HH:mm");
        share.time=simpleDateFormat.format(new Date());
        share.category=getCategory();
        shareReference.push().setValue(share).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    hideProgressBar();
                }else {
                    hideProgressBar();
                    Toast.makeText(context, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
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

    public String getCategory() {
        int id=radiogroup.getCheckedRadioButtonId();
        RadioButton r_bt= (RadioButton) findViewById(id);
        return r_bt.getText().toString();
    }
}
