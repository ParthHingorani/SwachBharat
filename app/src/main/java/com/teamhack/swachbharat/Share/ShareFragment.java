package com.teamhack.swachbharat.Share;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamhack.swachbharat.PermissionUtils;
import com.teamhack.swachbharat.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShareFragment extends Fragment implements OnMapReadyCallback,GoogleMap.OnMarkerClickListener{

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    GoogleMap googleMap;
    List<Share> shareList;
    DatabaseReference shareReference;
    ValueEventListener shareListener;
    final static String SHARE_CHILD="Share";
    HashMap<Marker,Share> shareMap;

    public ShareFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shareList=new ArrayList<>();
        shareMap=new HashMap<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_share, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        shareReference= FirebaseDatabase.getInstance().getReference().child(SHARE_CHILD);
        shareListener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot shareSnapshot:dataSnapshot.getChildren()){
                    Share s=shareSnapshot.getValue(Share.class);
                    Marker marker;
                    if(shareList.isEmpty() || !shareList.contains(s)){
                        shareList.add(s);
                        switch (s.category){
                            case "Garbage Collection Point":
                            case "A garbage collection point":
                                marker=googleMap.addMarker(new MarkerOptions().title(s.category)
                                        .position(new LatLng(Double.parseDouble(s.latitude),Double.parseDouble(s.longitude)))
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.dustbin)));
                                break;
                            case "An open manhole":
                                marker=googleMap.addMarker(new MarkerOptions().title(s.category)
                                        .position(new LatLng(Double.parseDouble(s.latitude),Double.parseDouble(s.longitude)))
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.manhole))
                                        .snippet("Status: "+s.status));
                                break;
                            case "Untidy Place":
                            case "An untidy place":
                            case "A location which is never cleaned":
                                marker=googleMap.addMarker(new MarkerOptions().title(s.category)
                                        .position(new LatLng(Double.parseDouble(s.latitude),Double.parseDouble(s.longitude)))
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.broom))
                                        .snippet("Status: "+s.status));
                                break;
                            default:
                                marker=googleMap.addMarker(new MarkerOptions().title(s.category)
                                        .position(new LatLng(Double.parseDouble(s.latitude),Double.parseDouble(s.longitude)))
                                        .snippet("Status: "+s.status));
                                break;
                        }

                        shareMap.put(marker,s);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap=googleMap;
        shareReference.addValueEventListener(shareListener);
        enableMyLocation();
        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                new ShareDialog(getActivity(),Double.toString(latLng.latitude),Double.toString(latLng.longitude)).show();
            }
        });

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        switch (marker.getTitle()){
            case "An open manhole":
            case "Untidy Place":
            case "An untidy place":
            case "A location which is never cleaned":
                new TaskDialog(getActivity(),shareMap.get(marker),marker).show();
        }
        return false;
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission((AppCompatActivity) getContext(), LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (getActivity() != null) {
            // Access to the location has been granted to the app.
            googleMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            enableMyLocation();
        } else {
        }
    }

}
