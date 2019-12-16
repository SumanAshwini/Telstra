package com.infy.telstraassignment_1.mvp.canadaslist;

import com.infy.telstraassignment_1.model.Canada;
import com.infy.telstraassignment_1.model.CanadaRepo;

import com.infy.telstraassignment_1.util.EspressoTestingIdlingResource;
import com.infy.telstraassignment_1.model.SimulateCanadaClient;
import com.infy.telstraassignment_1.mvp.canadaslist.CanadasListContract.OnResponseCallback;
import org.mockito.Mock;
import java.util.List;

import java.util.List;

public final class CanadaPresenter implements CanadasListContract.Presenter  {
    // to keep reference to view
    private CanadasListContract.View view;

    // Repository pattern, mclient holds reference to concrete data retrieval implementation

    private CanadaRepo mclient;

    public CanadaPresenter(CanadasListContract.View view,CanadaRepo client) {
        this.view = view;

        mclient = client;
    }

    @Override
    public void dropView() {
        view = null;
    }

    // would be triggered by MovieListActivity
    @Override
    public void loadCanadaList() {
        view.showProgress();

        mclient.getCanadaList(callback);

        // required for espresso UI testing

        // to wait till response occurred

//        EspressoTestingIdlingResource.increment();
    }

    // callback mechanism , onResponse will be triggered with response

    // by simulatemovieclient(or your network or database process) and pass the response to view
    private final CanadasListContract.OnResponseCallback callback = new CanadasListContract.OnResponseCallback() {
        @Override
        public void onResponse(List<Canada> canadaList) {

            view.showCanadaList(canadaList);

            view.hideProgress();

//            EspressoTestingIdlingResource.decrement();
        }

        @Override
        public void onError(String errMsg) {

            view.hideProgress();

            view.showLoadingError(errMsg);

//            EspressoTestingIdlingResource.decrement();
        }
    };
}
