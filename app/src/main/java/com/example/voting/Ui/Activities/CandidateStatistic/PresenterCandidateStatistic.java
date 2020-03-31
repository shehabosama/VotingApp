package com.example.voting.Ui.Activities.CandidateStatistic;

import android.text.TextUtils;
import android.util.Log;

import com.example.voting.common.model.CandidatesResponse;
import com.example.voting.common.network.WebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class PresenterCandidateStatistic implements CandidateStatisticContract.Presenter {
    private CandidateStatisticContract.View mView;
    private CandidateStatisticContract.Model.onFinishedListener mModel;

    public PresenterCandidateStatistic(CandidateStatisticContract.Model.onFinishedListener mModel, CandidateStatisticContract.View mView) {
        this.mView = mView;
        this.mModel = mModel;
    }


    @Override
    public void performGetCandidateData(String candidId) {

        mView.showProgress();
        if(TextUtils.isEmpty(candidId)){
            mModel.onFinished("Something went Wrong");
            mView.hideProgress();
        }else{
            WebService.getInstance().getApi().getInformationAboutCandidates(Integer.parseInt(candidId)).enqueue(new Callback<CandidatesResponse>() {
                @Override
                public void onResponse(Call<CandidatesResponse> call, Response<CandidatesResponse> response) {
                    mModel.onFinished(response.body());
                    mView.hideProgress();
                }

                @Override
                public void onFailure(Call<CandidatesResponse> call, Throwable t) {

                    Log.e(TAG, "onFailure: "+t.getMessage());
                    mView.hideProgress();
                }
            });
        }
    }
}