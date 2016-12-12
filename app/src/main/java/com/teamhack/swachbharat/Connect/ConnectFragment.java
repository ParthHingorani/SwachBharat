package com.teamhack.swachbharat.Connect;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewGroup;

import com.teamhack.swachbharat.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConnectFragment extends Fragment {

    TextView news,a1,a2,a3,a4,a5,a6,r1,r2,r3,r4,v1,v2,v3,v4,v5,join;
    
    public ConnectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_connect, container, false);
        
        news = (TextView) findViewById(R.id.news1);
        a1 = (TextView) findViewById(R.id.iAuth1);
        a2 = (TextView) findViewById(R.id.iAuth2);
        a3 = (TextView) findViewById(R.id.iAuth3);
        a4 = (TextView) findViewById(R.id.iAuth4);
        a5 = (TextView) findViewById(R.id.iAuth5);
        a6 = (TextView) findViewById(R.id.iAuth6);
        r1 = (TextView) findViewById(R.id.iArt1);
        r2 = (TextView) findViewById(R.id.iArt2);
        r3 = (TextView) findViewById(R.id.iArt3);
        r4 = (TextView) findViewById(R.id.iArt4);
        v1 = (TextView) findViewById(R.id.iVideo1);
        v2 = (TextView) findViewById(R.id.iVideo2);
        v3 = (TextView) findViewById(R.id.iVideo3);
        v4 = (TextView) findViewById(R.id.iVideo4);
        v5 = (TextView) findViewById(R.id.iVideo5);
        join = (TextView) findViewById(R.id.follow1);

        news.setMovementMethod(LinkMovementMethod.getInstance());
        a1.setMovementMethod(LinkMovementMethod.getInstance());
        a2.setMovementMethod(LinkMovementMethod.getInstance());
        a3.setMovementMethod(LinkMovementMethod.getInstance());
        a4.setMovementMethod(LinkMovementMethod.getInstance());
        a5.setMovementMethod(LinkMovementMethod.getInstance());
        a6.setMovementMethod(LinkMovementMethod.getInstance());
        r1.setMovementMethod(LinkMovementMethod.getInstance());
        r2.setMovementMethod(LinkMovementMethod.getInstance());
        r3.setMovementMethod(LinkMovementMethod.getInstance());
        r4.setMovementMethod(LinkMovementMethod.getInstance());
        v1.setMovementMethod(LinkMovementMethod.getInstance());
        v2.setMovementMethod(LinkMovementMethod.getInstance());
        v3.setMovementMethod(LinkMovementMethod.getInstance());
        v4.setMovementMethod(LinkMovementMethod.getInstance());
        v5.setMovementMethod(LinkMovementMethod.getInstance());
        join.setMovementMethod(LinkMovementMethod.getInstance());
        
        return view;
    }

}
