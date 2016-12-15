package com.teamhack.swachbharat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.teamhack.swachbharat.Connect.ConnectFragment;
import com.teamhack.swachbharat.Feed.FeedFragment;
import com.teamhack.swachbharat.Feed.NewFeedDialog;
import com.teamhack.swachbharat.Login.LoginActivity;
import com.teamhack.swachbharat.Profile.ProfileFragment;
import com.teamhack.swachbharat.Share.ShareFragment;
import com.teamhack.swachbharat.Social.SocialFragment;
import com.teamhack.swachbharat.Statistics.StatisticsFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView usr_img;
    TextView txt_nav_username,txt_nav_email;
    FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
    static String feedTitle,feedContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        Fragment fragment= new  FeedFragment();
        transaction.replace(R.id.content_main,fragment);
        transaction.commit();

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        Uri i=firebaseUser.getPhotoUrl();
        String sn=firebaseUser.getDisplayName();
        String se=firebaseUser.getEmail();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        usr_img= (ImageView) navigationView.getHeaderView(0).findViewById(R.id.imageView);
        txt_nav_email= (TextView) navigationView.getHeaderView(0).findViewById(R.id.txt_nav_email);
        txt_nav_username= (TextView) navigationView.getHeaderView(0).findViewById(R.id.txt_nav_username);
        Glide.with(this).load(i).into(usr_img).onLoadStarted(getResources().getDrawable(R.drawable.ic_account_circle_black_48dp));
        txt_nav_username.setText(sn);
        txt_nav_email.setText(se);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Class fragmentClass=null;

        if (id == R.id.nav_feed) {
            fragmentClass= FeedFragment.class;
        } else if (id == R.id.nav_share) {
            fragmentClass= ShareFragment.class;
        } else if (id == R.id.nav_social) {
            fragmentClass= SocialFragment.class;
        } else if (id == R.id.nav_statistics) {
            fragmentClass= StatisticsFragment.class;
        } else if (id == R.id.nav_connect) {
            fragmentClass= ConnectFragment.class;
        } else if (id == R.id.nav_profile) {
            fragmentClass= ProfileFragment.class;
        } else if (id == R.id.nav_logout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            finish();
        }

        if(fragmentClass!=null){

            try {
                FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                Fragment fragment= (Fragment) fragmentClass.newInstance();
                transaction.replace(R.id.content_main,fragment);
                transaction.commit();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            NewFeedDialog newFeedDialog=new NewFeedDialog(MainActivity.this);
            newFeedDialog.show();
            newFeedDialog.setData(feedTitle,feedContent,data);
        }
    }

    public static void saveFeedDialogState(String title, String content){
        feedTitle=new String(title);
        feedContent=new String(content);
    }
}
