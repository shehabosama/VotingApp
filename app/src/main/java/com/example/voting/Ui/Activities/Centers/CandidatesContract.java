package com.example.voting.Ui.Activities.Centers;

import com.example.voting.common.model.CandidatesResponse;
import com.example.voting.common.model.CenterResponse;

public interface CandidatesContract {

    interface Model {
        interface onFinishedListener {
            void onFinished(String result);
            void loadCandidatesData(CandidatesResponse centerResponse);
            void onFailuer(Throwable t);

        }
    }

    interface View {
        void showProgress();

        void hideProgress();
    }

    interface Presenter {
        void performGetAllCandidates(String centerId);

        void updateCandidStatus(String candidId,String userId);
    }

}
