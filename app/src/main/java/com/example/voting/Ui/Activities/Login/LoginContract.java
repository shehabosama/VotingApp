package com.example.voting.Ui.Activities.Login;


import com.example.voting.common.model.User;

public interface LoginContract
{
    interface Model {
        interface onFinishedListener {
            void onFinished(User user);
            void onFinished(String result);
            void onFailuer(Throwable t);
        }
        void Login(onFinishedListener onFinishedListener, String email, String password);
    }
    interface View{
        void showProgress();
        void hideProgress();
        void loginValidations();
        void loginError();
        void emailInvalid(String erorr);

    }
    interface Presenter{
        void performLogin(String email, String password);

    }
}
