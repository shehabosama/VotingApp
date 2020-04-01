package com.example.voting.Ui.Activities.Settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.voting.R;
import com.example.voting.common.HelperStuffs.AppPreferences;
import com.example.voting.common.HelperStuffs.Constants;
import com.example.voting.common.HelperStuffs.UiUtilities;
import com.example.voting.common.base.BaseActivity;

public class SettingsActivity extends BaseActivity implements SettingsContract.Model.onFinishedListener,SettingsContract.View{

    private EditText editTextUserName,editTextPassword,editTextDetailsAddress;
    private Button btnUpdate;
    private PresenterSettings presenter;
    private ProgressDialog dialog;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initializeViews();
        setListeners();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    protected void initializeViews() {
        toolbar = findViewById(R.id.tool_Bar);
        setToolbar(toolbar,"تحديث البيانات",false,true,false);
        editTextUserName = findViewById(R.id.user_name);
        editTextPassword = findViewById(R.id.password);
        editTextDetailsAddress = findViewById(R.id.address);
        btnUpdate = findViewById(R.id.btn_update);
        editTextDetailsAddress.setText(AppPreferences.getString(Constants.AppPreferences.ADDRESS_DETAILS,SettingsActivity.this,""));
        editTextPassword.setText(AppPreferences.getString(Constants.AppPreferences.PASSWORD,SettingsActivity.this,""));
        editTextUserName.setText(AppPreferences.getString(Constants.AppPreferences.USER_NAME,SettingsActivity.this,""));
        dialog = new ProgressDialog(this);
        presenter = new PresenterSettings(this,this);
    }

    @Override
    protected void setListeners() {
        btnUpdate.setOnClickListener(btnUpdateListener);
    }
    private View.OnClickListener btnUpdateListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
                  presenter.performUpdateInformation(AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,SettingsActivity.this,"0"),
                          editTextUserName.getText().toString(),
                          editTextPassword.getText().toString(),
                          editTextDetailsAddress.getText().toString());
        }
    };

    @Override
    public void onFinished(String result) {
        UiUtilities.showToast(SettingsActivity.this,result);
    }

    @Override
    public void onFailuer(Throwable t) {

    }

    @Override
    public void showProgress() {
        dialog.setTitle("wait minuet..");//title which will show  on the dialog box
        dialog.setMessage("login now...");//message which will show  on the dialog box
        // progressDialog.setCancelable(false);// not allow the user to cancel the dialog box even done the process
        dialog.show();// turn on the dialog box
    }

    @Override
    public void hideProgress() {

        dialog.dismiss();
    }
}
