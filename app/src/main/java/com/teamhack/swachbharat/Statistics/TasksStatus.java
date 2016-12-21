package com.teamhack.swachbharat.Statistics;

import android.os.AsyncTask;
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

public class TasksStatus extends AsyncTask<Void, Void, Void>{

    private int completed, taken, newcomplete;
    private User user;
    private TextView completedText, takenText;
    private DatabaseReference completedReference= FirebaseDatabase.getInstance().getReference();
    private DatabaseReference takenReference= FirebaseDatabase.getInstance().getReference().child("Share");
    private ValueEventListener completedEventListener,takenEventListener;

    TasksStatus(User user, TextView completedText, TextView takenText)
    {
        this.user=user;
        this.completedText=completedText;
        this.takenText=takenText;
    }

    protected Void doInBackground(Void... params)
    {

            completedEventListener = completedReference.addValueEventListener(new ValueEventListener(){
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
                    newcomplete=user.getCompleted()+completed;
                    completedReference.child("User").child(user.getUid()).child("completed").setValue(newcomplete);
                    completedText.setText("Tasks Completed : "+completed);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            completedReference.child("Share").addValueEventListener(completedEventListener);
        if(takenText!=null)
        {
            takenEventListener = takenReference.addValueEventListener(new ValueEventListener() {
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
            
            takenReference.removeEventListener(takenEventListener);
        }

        completedReference.removeEventListener(completedEventListener);

        return null;
    }

}
