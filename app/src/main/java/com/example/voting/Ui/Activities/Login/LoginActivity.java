package com.example.voting.Ui.Activities.Login;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;

import com.example.voting.R;
import com.example.voting.Ui.Activities.Candidate.CandidateActivity;
import com.example.voting.Ui.Activities.ElectionLogin.ElectionLogin;
import com.example.voting.Ui.Activities.Register.RegisterActivity;
import com.example.voting.common.HelperStuffs.AppPreferences;
import com.example.voting.common.HelperStuffs.Constants;
import com.example.voting.common.HelperStuffs.UiUtilities;
import com.example.voting.common.base.BaseActivity;
import com.example.voting.common.model.User;

public class LoginActivity extends BaseActivity implements LoginContract.Model.onFinishedListener, LoginContract.View  {
    Button btnLogin;
    EditText editTextEmail,editTextPassword;
    ProgressDialog progressDialog;
    PresenterLogin presenter;
    Toolbar mToolBar;

    public static void startActivity(Context context){
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_acitvity);
        initializeViews();
        setListeners();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    protected void initializeViews() {

            editTextEmail = findViewById(R.id.editTextEmail);
            editTextPassword = findViewById(R.id.editTextPassword);
            btnLogin = findViewById(R.id.btn_login);
            mToolBar = findViewById(R.id.tool_Bar);
            setToolbar(mToolBar,"Login",false,true,false);
            progressDialog = new ProgressDialog(this);
            presenter = new PresenterLogin(this,this,this);


    }
    @Override
    protected void setListeners() {
        btnLogin.setOnClickListener(btnLoginListener);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.login_as_admin){
            startActivity(new Intent(getApplicationContext(), ElectionLogin.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_login,menu);
        return super.onCreateOptionsMenu(menu);

    }


    private View.OnClickListener btnLoginListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            String strEmail = editTextEmail.getText().toString();
            String strPassword = editTextPassword.getText().toString();
            if(TextUtils.isEmpty(strEmail)||TextUtils.isEmpty(strPassword)){
                UiUtilities.showToast(getApplicationContext(),"Please Enter Email and Password");
                hideProgress();
            }else {
                showProgress();
                //
                //123456
                presenter.performLogin(strEmail,strPassword);
            }
        }
    };

    public void viewRegisterClicked(View view)
    {
        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
    }


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
    public void loginValidations() {
        UiUtilities.showToast(LoginActivity.this,"pleas fill all the flied");
    }



    @Override
    public void loginError() {
        hideProgress();
        UiUtilities.showToast(LoginActivity.this,"Login Failure");
    }

    @Override
    public void emailInvalid(String erorr) {
        hideProgress();
        UiUtilities.showToast(LoginActivity.this,erorr);
    }
    @Override
    public void onFinished(User user) {
        AppPreferences.setString(Constants.AppPreferences.LOGGED_IN_USER_KEY,String.valueOf(user.id),this);
    }
    @Override
    public void onFinished(String result) {
        UiUtilities.showToast(LoginActivity.this,result);
        hideProgress();
        if(result.equals("1")){
            startActivity(new Intent(getApplicationContext(), CandidateActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }else{
            UiUtilities.showToast(getApplicationContext(),"Something Went Wrong");
        }

    }

    @Override
    public void onFailuer(Throwable t) {

    }


}
