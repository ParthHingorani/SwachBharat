package com.teamhack.swachbharat.Share;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamhack.swachbharat.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShareFragment extends Fragment implements OnMapReadyCallback{

    GoogleMap googleMap;
    List<Share> shareList;
    DatabaseReference shareReference;
    ValueEventListener shareListener;
    final static String SHARE_CHILD="Share";

    public ShareFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shareList=new ArrayList<>();
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
                    if(shareList.isEmpty() || !shareList.contains(s)){
                        shareList.add(s);
                        googleMap.addMarker(new MarkerOptions().title(s.category)
                                .position(new LatLng(Double.parseDouble(s.latitude),Double.parseDouble(s.longitude))));
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
        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                new ShareDialog(getActivity(),Double.toString(latLng.latitude),Double.toString(latLng.longitude)).show();
            }
        });
    }
}
