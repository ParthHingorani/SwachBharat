package com.teamhack.swachbharat.Social;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.teamhack.swachbharat.PermissionUtils;
import com.teamhack.swachbharat.Profile.User;
import com.teamhack.swachbharat.R;

import java.util.Date;

/**
 * Created by ParthHingorani on 15-12-2016.
 */

public class SocialDialog extends Dialog implements View.OnClickListener, OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    Context context;
    final static String SOCIAL_CHILD = "Social";
    Button bt_post, bt_cancel;
    String lat, lng;
    ProgressDialog progressDialog;
    Social social;
    RadioGroup radiogroup;
    EditText detail;
    MapView mapView;
    GoogleMap googleMap;
    TextView txt_mylocation;

    public SocialDialog(Context context) {
        super(context);
        this.context = context;
        lat = new String();
        lng = new String();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_social);
        bt_post = (Button) findViewById(R.id.postSocial);
        bt_cancel = (Button) findViewById(R.id.cancelSocial);
        radiogroup = (RadioGroup) findViewById(R.id.radioGroupSocial);
        detail = (EditText) findViewById(R.id.socialDetails);
        txt_mylocation = (TextView) findViewById(R.id.txt_mylocation);
        bt_cancel.setOnClickListener(this);
        bt_post.setOnClickListener(this);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(null);
        mapView.getMapAsync(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.postSocial:
                writeSocial();
                break;
            case R.id.cancelSocial:
                dismiss();
                break;
        }
    }

    public void setData(String details, Intent data) {
        detail.setText(details);
    }

    public String getCategory() {
        int id = radiogroup.getCheckedRadioButtonId();
        RadioButton r_bt = (RadioButton) findViewById(id);
        return r_bt.getText().toString();
    }

    private void hideProgressBar() {
        progressDialog.hide();
    }

    private void showProgressBar() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Posting . . .");
        progressDialog.show();
    }

    private boolean validateFrom() {

        boolean result=true;
        if (detail.getText().toString().length() == 0) {
            detail.setError("Please enter details.");
            result=false;
        }
        if (txt_mylocation.getText().toString().length()==0){
            txt_mylocation.setText("Please choose a location");
            result=false;
        }


        return result;
    }

    private void writeSocial() {
        if (validateFrom()) {

            showProgressBar();
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference socialReference = FirebaseDatabase.getInstance().getReference().child(SOCIAL_CHILD);
            social = new Social();

            social.latitude = lat;
            social.longitude = lng;
            social.category = getCategory();
            social.socialDetail = detail.getText().toString();
            java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("dd/MM/yyyy  HH:mm");
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
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Posted successfully", Toast.LENGTH_SHORT).show();
                    dismiss();
                } else {
                    Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap mMap) {
        MapsInitializer.initialize(context);
        this.googleMap = mMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        enableMyLocation();
        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                googleMap.clear();
                txt_mylocation.setText("Lat: " + latLng.latitude + ", Lng: " + latLng.longitude);
                googleMap.addMarker(new MarkerOptions().title("Marked Location")
                        .position(latLng));
                lat = String.valueOf(latLng.latitude);
                lng = String.valueOf(latLng.longitude);

            }
        });
        mapView.onResume();
    }

    public void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission((AppCompatActivity) getContext(), LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (context != null) {
            // Access to the location has been granted to the app.
            googleMap.setMyLocationEnabled(true);
        }
    }
}
