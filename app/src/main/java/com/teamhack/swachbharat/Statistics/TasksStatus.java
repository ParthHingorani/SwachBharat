package com.teamhack.swachbharat.Statistics;

import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamhack.swachbharat.Profile.User;
import com.teamhack.swachbharat.R;
import com.teamhack.swachbharat.Share.Share;

/**
 * Created by neptune on 20/12/16.
 */

public class TasksStatus extends AsyncTask<Void, Void, Void>{

    private int completed, taken, newcomplete, flag;
    private User user;
    private TextView completedText, takenText;
    private DatabaseReference completedReference= FirebaseDatabase.getInstance().getReference();
    private DatabaseReference takenReference= FirebaseDatabase.getInstance().getReference().child("Share");
    private ValueEventListener completedEventListener,takenEventListener;

    public TasksStatus(User user, TextView completedText, TextView takenText, View rv)
    {
        this.user=user;
        this.completedText=completedText;
        this.takenText=takenText;
        if(completedText == rv.findViewById(R.id.txt_no_of_tasks_completed)&&takenText == rv.findViewById(R.id.txt_no_of_tasks_taken))
        {
            flag=1;
        }
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
                    if(flag==1)
                    {
                        completedText.setText(""+completed);
                    }
                    else
                    {
                        completedText.setText("Tasks Completed : "+completed);
                    }
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

                    if(flag==1)
                    {
                        takenText.setText(""+taken);
                    }
                    else
                    {
                        takenText.setText("Tasks Taken : "+ taken);
                    }
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
