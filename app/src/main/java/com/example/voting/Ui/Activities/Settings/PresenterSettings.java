package com.example.voting.Ui.Activities.Settings;

import android.text.TextUtils;
import android.util.Log;

import com.example.voting.common.model.CandidatesResponse;
import com.example.voting.common.model.MainResponse;
import com.example.voting.common.network.WebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class PresenterSettings implements SettingsContract.Presenter {
    private SettingsContract.View mView;
    private SettingsContract.Model.onFinishedListener mModel;

    public PresenterSettings(SettingsContract.Model.onFinishedListener mModel, SettingsContract.View mView) {
        this.mView = mView;
        this.mModel = mModel;
    }


    @Override
    public void performUpdateInformation(String userId,String userName, String password, String addressDetails) {
        mView.showProgress();
        if(TextUtils.isEmpty(userName)||TextUtils.isEmpty(password)||TextUtils.isEmpty(addressDetails)||TextUtils.isEmpty(userId)){
            mModel.onFinished("Something Went Wrong");
            mView.hideProgress();
        }else{
            WebService.getInstance().getApi().updateUserInformation(Integer.parseInt(userId),userName,password,addressDetails).enqueue(new Callback<MainResponse>() {
                @Override
                public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                    mModel.onFinished(response.body().message);
                    mView.hideProgress();
                }

                @Override
                public void onFailure(Call<MainResponse> call, Throwable t) {
                    Log.e(TAG, "onFailure: "+t.getMessage() );
                    mView.hideProgress();
                }
            });
        }
    }
}