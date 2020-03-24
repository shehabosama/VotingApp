package com.example.voting.Ui.Activities.Centers;

import android.util.Log;

import com.example.voting.common.model.CenterResponse;
import com.example.voting.common.network.WebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class PresenterCenters implements CentersContract.Presenter {
    private CentersContract.View mView;
    private CentersContract.Model.onFinishedListener mModel;

    public PresenterCenters(CentersContract.Model.onFinishedListener mModel, CentersContract.View mView) {
        this.mView = mView;
        this.mModel = mModel;
    }


    @Override
    public void performGetAllCenters() {
        mView.showProgress();
        WebService.getInstance().getApi().getAllCenters().enqueue(new Callback<CenterResponse>() {
            @Override
            public void onResponse(Call<CenterResponse> call, Response<CenterResponse> response) {
                mModel.loadCentersDate(response.body());
                mView.hideProgress();
            }

            @Override
            public void onFailure(Call<CenterResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.getMessage() );
                mView.hideProgress();
            }
        });
    }
}