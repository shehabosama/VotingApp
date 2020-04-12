package com.example.voting.common.base;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.Menu;
import android.view.MenuInflater;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.voting.R;
import com.example.voting.common.HelperStuffs.UiUtilities;


public abstract class BaseActivity extends AppCompatActivity {

    private Toolbar myToolbar;

    protected void printemanintoast(){
        UiUtilities.showToast(getApplicationContext(),"eman is here");
    }
    protected void setToolbar(Toolbar toolbar, String title, boolean showUpButton, boolean withElevation , boolean showAppIcon) {
        myToolbar = toolbar;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && withElevation) {
            toolbar.setElevation(getResources().getDimension(R.dimen.padding_small));
        }
        if (myToolbar != null) {
            myToolbar.setTitle(title);
            setSupportActionBar(myToolbar);
        }
        if (showUpButton) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }
        if(showAppIcon)
        {
            ActionBar actionBar = getSupportActionBar();
            if(actionBar != null)
            {
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setIcon(R.mipmap.ic_launcher_round);
            }

        }
    }

    public interface eman{
        void emanOnClick();
    }
    protected void setToolbarColor(Toolbar toolbar, int color) {
        toolbar.setBackgroundColor(color);
    }

    public void setToolbarTitle(String title) {
        if (myToolbar != null)
            myToolbar.setTitle(title);
    }

    public void setToolbarSubTitle(String subTitle) {
        if (myToolbar != null) {
            myToolbar.setSubtitle(subTitle);
        }

    }


    @SuppressLint("RestrictedApi")
    public abstract void onCreateOptionsMenu(Menu menu, MenuInflater inflater);

    protected abstract void initializeViews();

    protected abstract void setListeners();


}


