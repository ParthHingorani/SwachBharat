package com.teamhack.swachbharat.Social;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.teamhack.swachbharat.R;

/**
 * Created by ParthHingorani on 15-12-2016.
 */

public class SocialDialog extends Dialog implements View.OnClickListener {

    Context context;
    private final static String SOCIAL_CHILD="Social";
    private Button bt_post,bt_cancel;
    private ProgressDialog progressDialog;
    private Social social;

    public SocialDialog(Context context) {
        super(context);
        this.context=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_social);
        bt_post= (Button) findViewById(R.id.bt_feed_dialog_post);
        bt_cancel= (Button) findViewById(R.id.bt_feed_dialog_cancel);
        bt_cancel.setOnClickListener(this);
        bt_post.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_feed_dialog_post:
                //writeFeed();
                break;
            case R.id.bt_feed_dialog_cancel:
                dismiss();
                break;
        }

    }


}
