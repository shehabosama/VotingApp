package com.example.voting.Ui.Activities.Register;

import com.example.voting.common.model.CenterResponse;
import com.example.voting.common.model.User;

public interface RegisterContract {
    interface Model {
        interface onFinishedListener {
            void onFinished(String result);

            void onFailuer(Throwable t);

            void loadCentersDate(CenterResponse body);
        }
        void Login(onFinishedListener onFinishedListener, String email, String password);
    }
    interface View{
        void showProgress();
        void hideProgress();
        void loginValidations();
        void loginSuccess();
        void loginError();
        void emailInvalid();
        void setUserName(String username);
    }
    interface Presenter{
        void performRegister(String userName, String email, String password, String confirmPassword, String SsId,String country,String detailsAddress,String gender,String age);
        void performGetAllCenters();
    }
}
