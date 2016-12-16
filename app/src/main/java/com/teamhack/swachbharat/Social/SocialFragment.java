package com.teamhack.swachbharat.Social;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamhack.swachbharat.Feed.Feed;
import com.teamhack.swachbharat.Feed.FeedAdapter;
import com.teamhack.swachbharat.Feed.NewFeedDialog;
import com.teamhack.swachbharat.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SocialFragment extends Fragment {

    RecyclerView rv_feed;
    FeedAdapter feedAdapter;
    List<Feed> feedList;
    LinearLayoutManager linearLayoutManager;
    private DatabaseReference databaseReference;
    ValueEventListener feedListener;
    final static String FEED_CHILD="Feed";
    ProgressDialog progressDialog;
    NewFeedDialog newFeedDialog;

    public SocialFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_feed, container, false);
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading . . .");
        progressDialog.show();
        rv_feed= (RecyclerView) v.findViewById(R.id.rv_feed);
        feedList=new ArrayList<>();
        feedAdapter=new FeedAdapter(getActivity(),feedList);
        linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rv_feed.setAdapter(feedAdapter);
        rv_feed.setLayoutManager(linearLayoutManager);
        databaseReference= FirebaseDatabase.getInstance().getReference().child(FEED_CHILD);
        feedListener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                feedList.clear();
                for(DataSnapshot feedSnapshot:dataSnapshot.getChildren()){
                    feedList.add(feedSnapshot.getValue(Feed.class));
                    feedAdapter.notifyDataSetChanged();
                }
                progressDialog.hide();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.hide();
                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };
        databaseReference.addValueEventListener(feedListener);
        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab_new_feed);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newFeedDialog=new NewFeedDialog(getActivity());
                newFeedDialog.show();
            }
        });
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        databaseReference.removeEventListener(feedListener);
    }
}
