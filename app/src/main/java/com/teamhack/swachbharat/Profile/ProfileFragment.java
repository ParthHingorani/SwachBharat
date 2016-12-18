package com.teamhack.swachbharat.Profile;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamhack.swachbharat.Feed.Feed;
import com.teamhack.swachbharat.Feed.FeedAdapter;
import com.teamhack.swachbharat.R;
import com.teamhack.swachbharat.Share.Share;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private static final String FEED_CHILD = "Feed";
    private final static String SHARE_CHILD="Share";
    ImageView img_user;
    TextView txt_user,txt_posts,txt_locations,txt_no_post;
    RecyclerView rv_my_posts;
    FeedAdapter feedAdapter;
    List<Feed> feedList;
    DatabaseReference myPostReference,locationReference;
    ValueEventListener myPostListener,locationListener;
    FirebaseUser firebaseUser;
    LinearLayoutManager linearLayoutManager;
    int no_of_posts,no_of_location;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_profile, container, false);
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        feedList=new ArrayList<>();
        img_user= (ImageView) v.findViewById(R.id.img_user);
        txt_user= (TextView) v.findViewById(R.id.txt_profile_username);
        txt_posts= (TextView) v.findViewById(R.id.txt_no_of_post);
        txt_locations= (TextView) v.findViewById(R.id.txt_no_of_locations);
        txt_no_post= (TextView) v.findViewById(R.id.txt_no_post);
        rv_my_posts= (RecyclerView) v.findViewById(R.id.rv_my_posts);
        Glide.with(getActivity()).load(firebaseUser.getPhotoUrl()).into(img_user);
        myPostReference= FirebaseDatabase.getInstance().getReference().child(FEED_CHILD);
        myPostListener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                feedList.clear();
                no_of_posts=0;
                for(DataSnapshot feedSnapshot:dataSnapshot.getChildren()){
                    Feed f=feedSnapshot.getValue(Feed.class);
                    if(f.getUser().uid.contentEquals(firebaseUser.getUid())){
                        feedList.add(f);
                        feedAdapter.notifyDataSetChanged();
                        no_of_posts++;
                        txt_posts.setText("Number of posts posted by you: "+no_of_posts);
                        txt_no_post.setVisibility(View.INVISIBLE);
                    }
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };
        myPostReference.addValueEventListener(myPostListener);
        locationReference=FirebaseDatabase.getInstance().getReference().child(SHARE_CHILD);
        locationListener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                no_of_location=0;
                for(DataSnapshot shareSnapshot:dataSnapshot.getChildren()) {
                    Share s = shareSnapshot.getValue(Share.class);
                    if (s.getUser().uid.contentEquals(firebaseUser.getUid())) {
                        no_of_location++;
                        txt_locations.setText("Number of locations marked by you: "+no_of_location);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        locationReference.addValueEventListener(locationListener);
        feedAdapter=new FeedAdapter(getActivity(),feedList);
        rv_my_posts.setAdapter(feedAdapter);
        linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rv_my_posts.setLayoutManager(linearLayoutManager);
        return v;
    }

}
