package com.example.voting.Ui.Activities.ElectionLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.voting.R;
import com.example.voting.common.HelperStuffs.UiUtilities;
import com.example.voting.common.base.BaseActivity;
public class ElectionLogin extends BaseActivity implements ElectionLoginContract.Model.onFinishedListener,ElectionLoginContract.View{

    EditText textView;
    ProgressDialog progressDialog;
    Button btnLogin;
    PresenterElectionLogin presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_election_login);
        initializeViews();
        setListeners();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.admin_login,menu);
    }

    @Override
    protected void initializeViews() {
        textView = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.btn_login);
        progressDialog = new ProgressDialog(this);
        presenter = new PresenterElectionLogin(this,this);
    }

    @Override
    protected void setListeners() {
     btnLogin.setOnClickListener(btnLoginListener);
    }

    private View.OnClickListener btnLoginListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            presenter.performElectionLogin(textView.getText().toString());
        }
    };
    @Override
    public void showProgress() {
        progressDialog.setTitle("wait minuet..");//title which will show  on the dialog box
        progressDialog.setMessage("login now...");//message which will show  on the dialog box
        // progressDialog.setCancelable(false);// not allow the user to cancel the dialog box even done the process
        progressDialog.show();// turn on the dialog box
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void onFinished(String result) {
        if(result.equals("2")){
            UiUtilities.showToast(getApplicationContext(),"Successfully");
        }else{
            UiUtilities.showToast(getApplicationContext(),"Something went wrong..");

        }
    }

    @Override
    public void onFailuer(Throwable t) {

    }
}
