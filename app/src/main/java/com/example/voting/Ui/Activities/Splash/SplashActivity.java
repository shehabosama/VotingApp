package com.example.voting.Ui.Activities.Splash;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ProgressBar;

import com.example.voting.R;
import com.example.voting.Ui.Activities.Main.MainActivity;
import com.example.voting.common.SqlHelper.myDbAdapter;
import com.example.voting.common.base.BaseActivity;

public class SplashActivity  extends BaseActivity {

    ProgressBar progressBar;
    myDbAdapter helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initializeViews();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    protected void initializeViews() {
        helper = new myDbAdapter(this);
        progressBar = (ProgressBar)findViewById(R.id.progress_bar_);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#6200EE"),
                android.graphics.PorterDuff.Mode.MULTIPLY);

        Thread thread = new Thread()
        {

            @Override
            public void run() {

                try{
                    sleep(3000);
                }catch (Exception e){

                    e.printStackTrace();

                }

                finally
                {
                    if(helper.getData().length()>0){
                        startActivity(new Intent(getApplicationContext(), MainActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK));
                    }
                    startActivity(new Intent(getApplicationContext(), MainActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK));
                }

            }
        };thread.start();
    }

    @Override
    protected void setListeners() {

    }
}
