package com.teamhack.swachbharat.Profile;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamhack.swachbharat.Feed.Feed;
import com.teamhack.swachbharat.Feed.FeedAdapter;
import com.teamhack.swachbharat.Login.LoginActivity;
import com.teamhack.swachbharat.MainActivity;
import com.teamhack.swachbharat.R;
import com.teamhack.swachbharat.Share.Share;
import com.teamhack.swachbharat.Statistics.TasksStatus;

import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.services.concurrency.AsyncTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private static final String FEED_CHILD = "Feed";
    private final static String SHARE_CHILD="Share";
    private static final String USER_CHILD="User";
    ImageView img_user,bt_delete_account;
    TextView txt_user,txt_posts,txt_locations,txt_no_post,txt_taken,txt_completed,txt_info;
    RecyclerView rv_my_posts;
    FeedAdapter feedAdapter;
    List<Feed> feedList;
    DatabaseReference myPostReference,locationReference,userReference;
    ValueEventListener myPostListener,locationListener,userListener;
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
        final View v= inflater.inflate(R.layout.fragment_profile, container, false);
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        feedList=new ArrayList<>();
        img_user= (ImageView) v.findViewById(R.id.img_user);
        txt_user= (TextView) v.findViewById(R.id.txt_profile_username);
        txt_posts= (TextView) v.findViewById(R.id.txt_no_of_post);
        txt_locations= (TextView) v.findViewById(R.id.txt_no_of_locations);
        txt_no_post= (TextView) v.findViewById(R.id.txt_no_post);
        rv_my_posts= (RecyclerView) v.findViewById(R.id.rv_my_posts);
        txt_taken= (TextView) v.findViewById(R.id.txt_no_of_tasks_taken);
        txt_completed= (TextView) v.findViewById(R.id.txt_no_of_tasks_completed);
        txt_info= (TextView) v.findViewById(R.id.txt_profile_info);
        bt_delete_account= (ImageView) v.findViewById(R.id.bt_delete_account);
        txt_user.setText(firebaseUser.getDisplayName());
        Glide.with(getActivity()).load(firebaseUser.getPhotoUrl()).into(img_user);


        userReference= FirebaseDatabase.getInstance().getReference().child(USER_CHILD);
        userListener= new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot userSnapshot:dataSnapshot.getChildren())
                {
                    User user = userSnapshot.getValue(User.class);
                    if(user!=null && user.getUid().contentEquals(firebaseUser.getUid()))
                    {
                        txt_info.setText(user.info);
                        new TasksStatus(user, txt_completed, txt_taken, v).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        userReference.addListenerForSingleValueEvent(userListener);

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
//                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };
        myPostReference.addListenerForSingleValueEvent(myPostListener);

        locationReference=FirebaseDatabase.getInstance().getReference().child(SHARE_CHILD);
        locationListener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                no_of_location=0;
                for(DataSnapshot shareSnapshot:dataSnapshot.getChildren()) {
                    Share s = shareSnapshot.getValue(Share.class);
                    if (s.getCreatedBy().uid.contentEquals(firebaseUser.getUid())) {
                        no_of_location++;
                        txt_locations.setText("Number of locations marked by you: "+no_of_location);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        locationReference.addListenerForSingleValueEvent(locationListener);

        bt_delete_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                builder.setTitle("Delete Account")
                        .setMessage("Do you really want to delete this account?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                userReference.child(firebaseUser.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        FirebaseAuth.getInstance().signOut();
                                        startActivity(new Intent(getActivity(),LoginActivity.class));
                                        getActivity().finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getActivity(), "Couldn't delete Account. Try again!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });

        feedAdapter=new FeedAdapter(getActivity(),feedList);
        rv_my_posts.setAdapter(feedAdapter);
        linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rv_my_posts.setLayoutManager(linearLayoutManager);
        return v;
    }

}
