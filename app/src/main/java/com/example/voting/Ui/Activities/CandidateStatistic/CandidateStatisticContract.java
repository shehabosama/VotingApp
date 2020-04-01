package com.example.voting.Ui.Activities.CandidateStatistic;

import com.example.voting.common.model.CandidatesResponse;

public interface CandidateStatisticContract {

    interface Model {
        interface onFinishedListener {
            void onFinished(String result);
            void onFinished(CandidatesResponse candidatesResponse);
            void onFailuer(Throwable t);

        }
    }

    interface View {
        void showProgress();

        void hideProgress();
    }

    interface Presenter {
        void performGetCandidateData(String candidId);
        void updateCandidStatus(String candidId,String userId);
    }

}
