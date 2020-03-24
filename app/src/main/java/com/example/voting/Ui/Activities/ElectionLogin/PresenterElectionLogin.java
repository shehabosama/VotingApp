package com.example.voting.Ui.Activities.ElectionLogin;

import android.text.TextUtils;
import android.widget.TextView;

import com.example.voting.common.model.MainResponse;
import com.example.voting.common.network.WebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterElectionLogin implements ElectionLoginContract.Presenter {
    private ElectionLoginContract.View mView;
    private ElectionLoginContract.Model.onFinishedListener mModel;

    public PresenterElectionLogin(ElectionLoginContract.Model.onFinishedListener mModel, ElectionLoginContract.View mView) {
        this.mView = mView;
        this.mModel = mModel;
    }


    @Override
    public void performElectionLogin(String registerCode) {
        mView.showProgress();
        if(TextUtils.isEmpty(registerCode)){
            mModel.onFinished("Please Write the code");
            mView.showProgress();
        }else{
            WebService.getInstance().getApi().electionLogin(registerCode).enqueue(new Callback<MainResponse>() {
                @Override
                public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                    mModel.onFinished(String.valueOf(response.body().status));
                    mView.hideProgress();
                }

                @Override
                public void onFailure(Call<MainResponse> call, Throwable t) {
                    mView.hideProgress();
                }
            });
        }
    }
}