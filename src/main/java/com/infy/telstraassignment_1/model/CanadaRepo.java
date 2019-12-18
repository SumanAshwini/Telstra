package com.infy.telstraassignment_1.model;

import com.infy.telstraassignment_1.model.Canada;
import com.infy.telstraassignment_1.roomdb.RoomEntity;

import java.util.ArrayList;
import java.util.List;

public interface CanadaRepo {
    void getCanadasList();

    void prepareLocalDbList(ArrayList<Canada> canadasModelArrayList);

    void list(List<RoomEntity> roomEntityList);
}
