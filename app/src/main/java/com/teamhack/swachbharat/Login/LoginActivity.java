package com.teamhack.swachbharat.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamhack.swachbharat.MainActivity;
import com.teamhack.swachbharat.Profile.User;
import com.teamhack.swachbharat.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    private static final String USER_CHILD = "User";

    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;
    ProgressDialog progressDialog;
    private GoogleApiClient mGoogleApiClient;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        }
        findViewById(R.id.layout_choose).setVisibility(View.INVISIBLE);
        findViewById(R.id.bt_individual).setOnClickListener(this);
        findViewById(R.id.bt_ngo).setOnClickListener(this);
        findViewById(R.id.bt_govt).setOnClickListener(this);
        databaseReference= FirebaseDatabase.getInstance().getReference().child(USER_CHILD);
        findViewById(R.id.sign_in_button).setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
               // Log.d("user",user.getEmail());
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                updateUI(user);
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Toast.makeText(LoginActivity.this, "activity result outside", Toast.LENGTH_SHORT).show();
        if (requestCode == RC_SIGN_IN) {
            //Toast.makeText(LoginActivity.this, "activity result inside", Toast.LENGTH_SHORT).show();
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Log.d("signin",result.getStatus().toString());
            if (result.isSuccess()) {
                //Toast.makeText(LoginActivity.this, "activity result success", Toast.LENGTH_SHORT).show();
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                //Toast.makeText(LoginActivity.this, "activity result failed", Toast.LENGTH_SHORT).show();
                updateUI(null);
            }
        }
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        showProgressDialog();

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }else {
                            writeUser();
                        }

                    }
                });
    }

    private void showProgressDialog() {
        progressDialog=new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Logging you in");
        progressDialog.setMessage("Loading Details. . .");
        progressDialog.show();
    }

    private void hideProgressDialog() {
        progressDialog.hide();
    }

    private void writeUser() {
        final FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        //Toast.makeText(LoginActivity.this, "came here", Toast.LENGTH_SHORT).show();
        ValueEventListener userListener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                hideProgressDialog();
                if(dataSnapshot.child(firebaseUser.getUid()).exists()){
                    //Toast.makeText(LoginActivity.this, "came here inside", Toast.LENGTH_SHORT).show();
                    Log.e("LoginActivity","came here");
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }else {
                    showCategoryChooser();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        databaseReference.addListenerForSingleValueEvent(userListener);


    }

    private void showCategoryChooser() {
        findViewById(R.id.layout_login).setVisibility(View.GONE);
        findViewById(R.id.layout_choose).setVisibility(View.VISIBLE);
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        mAuth.signOut();

        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        updateUI(null);
                    }
                });
    }

    private void revokeAccess() {
        mAuth.signOut();

        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        updateUI(null);
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        //hideProgressDialog();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.sign_in_button) {
            signIn();
        }
        switch (v.getId()){
            case R.id.bt_individual:
                InsertInDB(getResources().getString(R.string.type_individual));
                break;
            case R.id.bt_ngo:
                InsertInDB(getResources().getString(R.string.type_individual));
                break;
            case R.id.bt_govt:
                InsertInDB(getResources().getString(R.string.type_individual));
                break;
        }
    }

    private void InsertInDB(String type) {
        showProgressDialog();
        User user=new User();
        final FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        user.uid=firebaseUser.getUid();
        user.name=firebaseUser.getDisplayName();
        user.email=firebaseUser.getEmail();
        user.type=type;
        user.posts=0;
        user.completed=0;
        databaseReference.child(user.uid).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                hideProgressDialog();
                if(task.isSuccessful()){
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this, "Couldn't update the database", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
