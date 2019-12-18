package com.infy.telstraassignment_1.mvp.canadaslist;

import com.infy.telstraassignment_1.model.Canada;
import com.infy.telstraassignment_1.model.CanadaModel;
import com.infy.telstraassignment_1.roomdb.RoomEntity;

import java.util.ArrayList;
import java.util.List;

public interface CanadasListContract {


    void getCanadaListFromLocal() throws Throwable;

    void setList(List<RoomEntity> roomEntityList) throws Throwable;

    void clearLocalDb(List<RoomEntity> roomEntityList) throws Throwable;

    boolean checkIntentConnection();

    void setActionBarTitle(String title);

    void setList(ArrayList<Canada> rowArrayList);

    void showLoading();

    void hideRefreshing();

    void showToast(String message) throws Throwable;
}

