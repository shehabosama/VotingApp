package com.example.voting.Ui.Activities.CandidateStatistic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.voting.R;
import com.example.voting.common.HelperStuffs.AppPreferences;
import com.example.voting.common.HelperStuffs.Constants;
import com.example.voting.common.HelperStuffs.UiUtilities;
import com.example.voting.common.base.BaseActivity;
import com.example.voting.common.model.CandidatesResponse;
import com.example.voting.common.network.Urls;
import com.squareup.picasso.Picasso;

public class CandidateStatisticsActivity extends BaseActivity implements CandidateStatisticContract.Model.onFinishedListener,CandidateStatisticContract.View{

    private TextView name,age,electoralProgram,symbol,centerName,countOfVoter;
    private ImageView imageView;
    private Button btnVoteToMyself;
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
        btnVoteToMyself = findViewById(R.id.btn_vote);
        if(AppPreferences.getBoolean(AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_CANDIDATE_KEY,CandidateStatisticsActivity.this,"0"),this,false)){
            btnVoteToMyself.setVisibility(View.INVISIBLE);
        }else{
            btnVoteToMyself.setVisibility(View.VISIBLE);
        }
        presenter = new PresenterCandidateStatistic(this,this);
        presenter.performGetCandidateData(AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_CANDIDATE_KEY,CandidateStatisticsActivity.this,"0"));
    }

    @Override
    protected void setListeners() {
        btnVoteToMyself.setOnClickListener(btnVoteToMyselfListener);
    }

    private View.OnClickListener btnVoteToMyselfListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            presenter.updateCandidStatus(AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_CANDIDATE_KEY,CandidateStatisticsActivity.this,"0"),"2");
            AppPreferences.setBoolean(AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_CANDIDATE_KEY,CandidateStatisticsActivity.this,"0"),true,CandidateStatisticsActivity.this);
            btnVoteToMyself.setVisibility(View.INVISIBLE);
        }
    };
    @Override
    public void onFinished(String result) {
        UiUtilities.showToast(getApplicationContext(),result);
    }

    @Override
    public void onFinished(CandidatesResponse candidatesResponse) {
        Picasso.with(this)
                .load(Urls.MAIN_URL+candidatesResponse.getCandidates().get(0).getImage()).placeholder(R.drawable.ic_launcher_foreground)
                .into(imageView);
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
