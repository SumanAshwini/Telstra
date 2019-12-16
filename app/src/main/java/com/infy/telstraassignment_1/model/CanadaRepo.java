package com.infy.telstraassignment_1.model;

import com.infy.telstraassignment_1.mvp.canadaslist.CanadasListContract;

public interface CanadaRepo {

    void getCanadaList(CanadasListContract.OnResponseCallback callback);

}
