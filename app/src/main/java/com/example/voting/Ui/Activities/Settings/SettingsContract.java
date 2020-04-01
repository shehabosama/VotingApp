package com.example.voting.Ui.Activities.Settings;

public interface SettingsContract {

    interface Model {
        interface onFinishedListener {
            void onFinished(String result);

            void onFailuer(Throwable t);

        }
    }

    interface View {
        void showProgress();

        void hideProgress();
    }

    interface Presenter {
        void performUpdateInformation(String userId,String userName, String password, String addressDetails);
    }

}
