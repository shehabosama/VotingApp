package com.example.voting.Ui.Activities.Register;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.voting.common.model.CenterResponse;
import com.example.voting.common.model.MainResponse;
import com.example.voting.common.model.User;
import com.example.voting.common.network.WebService;

import org.w3c.dom.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class PresenterRegister implements RegisterContract.Presenter  {

    private RegisterContract.View mLoginView;
    private RegisterContract.Model.onFinishedListener mModel;
    private Context context;
    PresenterRegister(RegisterContract.View mLoginView ,RegisterContract.Model.onFinishedListener mModel, Context context) {
        this.mLoginView = mLoginView;
        this.context = context;
        this.mModel = mModel;
    }

    @Override
    public void performRegister(String userName, String email, String password, String confirmPassword, String SsId,String country,String detailsAddress,String gender,String age) {
        mLoginView.showProgress();

        if (TextUtils.isEmpty(userName) ||
                TextUtils.isEmpty(email) ||
                TextUtils.isEmpty(password) ||
                TextUtils.isEmpty(SsId)||
                TextUtils.isEmpty(country)||
                TextUtils.isEmpty(detailsAddress)||
                TextUtils.isEmpty(gender)||
                TextUtils.isEmpty(age)
        ) {
            mModel.onFinished("Please Complete All data");
            mLoginView.hideProgress();
            mLoginView.loginValidations();
        } else if (!password.equals(confirmPassword)) {
            mLoginView.loginValidations();
            mLoginView.hideProgress();
        } else if(!isEmailValid(email)){
            mLoginView.emailInvalid();
            mLoginView.hideProgress();
        }else if (country.equals("0")){
            mModel.onFinished("Please Select the center");
            mLoginView.hideProgress();
        }else if(gender.equals("0")){
            mModel.onFinished("Please Select the gender");
            mLoginView.hideProgress();
        }else {
            User user = new User();
            user.username = userName;
            user.email = email;
            user.password = password;
            user.ssid = Integer.parseInt(SsId);
            user.address = country;
            user.details_address = detailsAddress;
            user.age = age;
            user.gender = Integer.parseInt(gender);


            WebService.getInstance().getApi().registerUser(user).enqueue(new Callback<MainResponse>() {
                @Override
                public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                    if (response.body().status == 2) {
                        mLoginView.hideProgress();
                        mModel.onFinished(response.body().message);
                    } else if (response.body().status == 1) {
                        Log.d(TAG, "onResponse: "+response.body().message);
                        mLoginView.hideProgress();
                        mLoginView.loginSuccess();
                        mModel.onFinished(response.body().message);
                    }
                }

                @Override
                public void onFailure(Call<MainResponse> call, Throwable t) {
                    mLoginView.hideProgress();
                    mModel.onFinished(t.getMessage());
                }
            });
        }
    }

    @Override
    public void performGetAllCenters() {
        mLoginView.showProgress();
        WebService.getInstance().getApi().getAllCenters().enqueue(new Callback<CenterResponse>() {
            @Override
            public void onResponse(Call<CenterResponse> call, Response<CenterResponse> response) {
                mModel.loadCentersDate(response.body());
                mLoginView.hideProgress();
            }

            @Override
            public void onFailure(Call<CenterResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.getMessage() );
                mLoginView.hideProgress();
            }
        });
    }

    private boolean isEmailValid(String email)
    {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);


        return matcher.matches();
    }

}
