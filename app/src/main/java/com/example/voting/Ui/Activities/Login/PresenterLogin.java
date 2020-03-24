package com.example.voting.Ui.Activities.Login;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;


import com.example.voting.common.HelperStuffs.AppPreferences;
import com.example.voting.common.HelperStuffs.Constants;
import com.example.voting.common.model.LoginResponse;
import com.example.voting.common.model.User;
import com.example.voting.common.network.WebService;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class PresenterLogin implements LoginContract.Presenter {

    private LoginContract.View mLoginView;
    private LoginContract.Model.onFinishedListener mModel;
    private Context context;
    PresenterLogin(LoginContract.View mLoginView,LoginContract.Model.onFinishedListener mModel , Context context ) {
        this.mLoginView = mLoginView;
        this.context = context;
        this.mModel = mModel;
    }

    @Override
    public void performLogin(final String email, final String password) {

        if(TextUtils.isEmpty(email))
        {
            mModel.onFinished("Please write your email address");
            mLoginView.hideProgress();
        }else if (TextUtils.isEmpty(password))
        {
            mLoginView.hideProgress();
               mLoginView.loginValidations();
               mModel.onFinished("Please Write your password");
        }else if(!isEmailValid(email)){
            mLoginView.emailInvalid();
            mLoginView.hideProgress();
        }else
        {
            final User user = new User();
            user.email = email;
            user.password = password;

            WebService.getInstance().getApi().loginUser(user).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                    assert response.body() != null;
                    if (response.body().status == 0) {
                        mModel.onFinished(response.body().message);
                        mLoginView.hideProgress();

                    } else if (response.body().status == 1) {
                        user.username = response.body().user.username;
                        user.id = Integer.parseInt(response.body().user.id);
                        user.ssid = response.body().user.ssid;
                        user.address = response.body().user.address;
                        user.gender   = response.body().user.gender;
                        AppPreferences.setString(Constants.AppPreferences.LOGGED_IN_USER_KEY,String.valueOf(user.id),context);
                        mModel.onFinished(user);
                        mModel.onFinished(String.valueOf(response.body().status));
                        mLoginView.hideProgress();


                    }
                }

                @Override
                public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                    mLoginView.loginError();
                    mLoginView.hideProgress();
                    mModel.onFailuer(t);
                    Log.e(TAG, "onFailure: "+t.getLocalizedMessage() );
                }
            });
        }
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

        Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);


        return matcher.matches();
    }



}
