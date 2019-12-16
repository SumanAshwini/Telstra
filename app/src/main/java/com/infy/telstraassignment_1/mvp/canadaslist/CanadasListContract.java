package com.infy.telstraassignment_1.mvp.canadaslist;

import com.infy.telstraassignment_1.model.Canada;

import java.util.List;

public interface CanadasListContract {
    // implemented by CanadasListActivity to provide concrete implementation
    interface View {
        void showProgress(); // display progress bar

        void hideProgress(); // hide progress bar

        void showCanadaList(List<Canada> canadaList); // receive response to display

        void showLoadingError(String errMsg); // display error
    }

    // implemented by CanadasPresenter to handle user event
    interface Presenter{
        void loadCanadaList();

        void dropView();
    }

    // implemented by CanadaPresenter to receive response from asynchronous processes
    interface OnResponseCallback{
        void onResponse(List<Canada> canadaList);

        void onError(String errMsg);
    }
}
