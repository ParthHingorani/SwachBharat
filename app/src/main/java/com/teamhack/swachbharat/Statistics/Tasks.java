package com.teamhack.swachbharat.Statistics;

import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamhack.swachbharat.Profile.User;
import com.teamhack.swachbharat.Share.Share;

/**
 * Created by neptune on 20/12/16.
 */

public class Tasks {

    public int completed, taken;

    public void taskCompleted(final User user, final TextView completedText)
    {
        final DatabaseReference completedReference= FirebaseDatabase.getInstance().getReference();
        ValueEventListener completedEventListener = completedReference.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                completed =0;
                for(DataSnapshot shareSnapshot: dataSnapshot.getChildren())
                {
                    Share s=shareSnapshot.getValue(Share.class);
                    if(s.getTakenBy()!=null&&s.getTakenBy().uid.contentEquals(user.getUid())&&s.getStatus().contentEquals("Completed"))
                    {
                        completed++;
                    }
                }
                int newcomplete=user.getCompleted()+completed;
                completedReference.child("User").child(user.getUid()).child("completed").setValue(newcomplete);
                completedText.setText("Tasks Completed : "+completed);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        completedReference.child("Share").addValueEventListener(completedEventListener);
    }

    public void taskTaken(final User user, final TextView takenText)
    {
        DatabaseReference takenReference= FirebaseDatabase.getInstance().getReference().child("Share");
        ValueEventListener takenEventListener = takenReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                taken =0;
                for(DataSnapshot shareSnapshot: dataSnapshot.getChildren())
                {
                    Share s=shareSnapshot.getValue(Share.class);
                    if(s.getTakenBy()!=null&&s.getTakenBy().uid.contentEquals(user.getUid())&&s.getStatus().contentEquals("Taken"))
                    {
                        taken++;
                    }
                }
                takenText.setText("Tasks Taken :"+ taken);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        takenReference.addValueEventListener(takenEventListener);
    }

}
