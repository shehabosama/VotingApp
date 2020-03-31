package com.example.voting.Ui.Activities.CandidateStatistic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.voting.R;
import com.example.voting.common.HelperStuffs.AppPreferences;
import com.example.voting.common.HelperStuffs.Constants;
import com.example.voting.common.HelperStuffs.UiUtilities;
import com.example.voting.common.base.BaseActivity;
import com.example.voting.common.model.CandidatesResponse;

public class CandidateStatisticsActivity extends BaseActivity implements CandidateStatisticContract.Model.onFinishedListener,CandidateStatisticContract.View{

    private TextView name,age,electoralProgram,symbol,centerName,countOfVoter;
    private ImageView imageView;
    private PresenterCandidateStatistic presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_statistics);
        initializeViews();
        setListeners();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    protected void initializeViews() {
        name = findViewById(R.id.candidName);
        age = findViewById(R.id.age);
        electoralProgram = findViewById(R.id.electoralProgram);
        centerName = findViewById(R.id.centerName);
        countOfVoter = findViewById(R.id.countOfVoter);
        symbol = findViewById(R.id.symbolName);
        imageView = findViewById(R.id.imageView);
        presenter = new PresenterCandidateStatistic(this,this);
        presenter.performGetCandidateData(AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_CANDIDATE_KEY,CandidateStatisticsActivity.this,"0"));
    }

    @Override
    protected void setListeners() {

    }

    @Override
    public void onFinished(String result) {
        UiUtilities.showToast(getApplicationContext(),result);
    }

    @Override
    public void onFinished(CandidatesResponse candidatesResponse) {
        name.setText("الاسم : "+candidatesResponse.getCandidates().get(0).getName());
        age.setText("السن : "+candidatesResponse.getCandidates().get(0).getAge());
        electoralProgram.setText("البرنامج الانتخابي : "+candidatesResponse.getCandidates().get(0).getElectoral_program());
        centerName.setText("المركز : "+candidatesResponse.getCandidates().get(0).getCenterName());
        symbol.setText("الرمز : "+candidatesResponse.getCandidates().get(0).getSymbol());
        countOfVoter.setText("عدد الاصوات : "+candidatesResponse.getCandidates().get(0).getCount());
    }

    @Override
    public void onFailuer(Throwable t) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}
