package com.example.voting.Ui.Activities.Centers;

import com.example.voting.common.model.CenterResponse;

public interface CentersContract {

    interface Model {
        interface onFinishedListener {
            void onFinished(String result);
            void loadCentersDate(CenterResponse centerResponse);
            void onFailuer(Throwable t);

        }
    }

    interface View {
        void showProgress();

        void hideProgress();
    }

    interface Presenter {
        void performGetAllCenters();
    }

}
