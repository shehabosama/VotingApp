package com.example.voting.Ui.Activities.Register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.voting.R;
import com.example.voting.Ui.Activities.Login.LoginActivity;
import com.example.voting.common.HelperStuffs.UiUtilities;
import com.example.voting.common.base.BaseActivity;
import com.example.voting.common.model.CenterResponse;
import com.example.voting.common.model.Centers;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends BaseActivity implements RegisterContract.View,RegisterContract.Model.onFinishedListener {


    ProgressDialog progressDialog;
    private PresenterRegister presenter;
    private EditText editTextUserName,editTextEmail,editTextPassword,editTextConfirmPassword,editTextAge,editTextSsId,editTextDetailsAddress;
    private RadioGroup radioGroup;
    private Button btnRegister;
    private int genderType=0;
    private Spinner spin;
    private List<Centers> centers;
    private int centerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initializeViews();
        setListeners();
    }

    public void loginLink(View view){
        startActivity(new Intent(getApplicationContext(), LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    @Override
    public void showProgress() {
        progressDialog.setTitle("wait minuet..");//title which will show  on the dialog box
        progressDialog.setMessage("login now...");//message which will show  on the dialog box
        progressDialog.setCancelable(false);// not allow the user to cancel the dialog box even done the process
        progressDialog.show();// turn on the dialog box
    }

    @Override
    public void hideProgress() {

        progressDialog.dismiss();
    }

    @Override
    public void loginValidations() {

        UiUtilities.showToast(RegisterActivity.this, "password is not mismatch");
    }

    @Override
    public void loginSuccess() {

        UiUtilities.showToast(RegisterActivity.this, "Welcome");
        LoginActivity.startActivity(RegisterActivity.this);

    }

    @Override
    public void loginError() {
        UiUtilities.showToast(RegisterActivity.this, "Something Went wrong.");
    }

    @Override
    public void emailInvalid() {
        UiUtilities.showToast(RegisterActivity.this, "Please this is not Email make sure from your email.");
    }

    @Override
    public void setUserName(String username) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    protected void initializeViews() {
        editTextUserName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        editTextSsId = findViewById(R.id.editTextSsId);
        editTextAge = findViewById(R.id.editTextAgeNumber);
        spin = findViewById(R.id.spin_center);
        editTextDetailsAddress = findViewById(R.id.editTextAddressDetails);
        radioGroup = findViewById(R.id.radioGroup);
        btnRegister = findViewById(R.id.btn_register);
        centers = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        presenter = new PresenterRegister(this,this,this);
        presenter.performGetAllCenters();
    }


    @Override
    protected void setListeners() {
        btnRegister.setOnClickListener(btnRegisterListener);
        radioGroup.setOnCheckedChangeListener(radioGroupListener);
        spin.setOnItemSelectedListener(spinListener);
    }
    private RadioGroup.OnCheckedChangeListener radioGroupListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            View radioButton = radioGroup.findViewById(i);
            int index = radioGroup.indexOfChild(radioButton);

            switch (index) {
                case 0:
                    genderType = 1;
                    break;
                case 1:
                    genderType = 2;
                    break;
            }
        }
    };
    private View.OnClickListener btnRegisterListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            presenter.performRegister(editTextUserName.getText().toString(),
                    editTextEmail.getText().toString(),
                    editTextPassword.getText().toString(),
                    editTextConfirmPassword.getText().toString(),
                    editTextSsId.getText().toString(),
                   String.valueOf(centerId),
                    editTextDetailsAddress.getText().toString(),
                    String.valueOf(genderType),
                    editTextAge.getText().toString());
        }
    };

    private AdapterView.OnItemSelectedListener spinListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Centers centersObj = centers.get(position);
            centerId = centersObj.getId();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    @Override
    public void onFinished(String result) {
        UiUtilities.showToast(RegisterActivity.this,result);
        hideProgress();
    }

    @Override
    public void onFailuer(Throwable t) {

    }

    @Override
    public void loadCentersDate(CenterResponse body) {
        centers.clear();
        Centers obj = new Centers();
        obj.setId(0);
        obj.setName("اختر مركزك");
        centers.add(obj);
        centers.addAll(body.getCenters());
        CenterAdapter adapter = new CenterAdapter(this,centers);
        spin.setAdapter(adapter);
    }
}
