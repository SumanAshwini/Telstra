package com.infy.telstraassignment_1.roomdb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface CanadaDao {

    @Query("Select * from Canadalist")
    List<RoomEntity> getCanadasList();

    @Query("Delete from CanadaList")
    void deleteTitle();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(RoomEntity roomEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertListOfUsers(List<RoomEntity> titlesArrayList);
}

