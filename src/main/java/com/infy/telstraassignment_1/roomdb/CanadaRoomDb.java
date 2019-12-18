package com.infy.telstraassignment_1.roomdb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = RoomEntity.class, exportSchema = false, version = 2)
public abstract class CanadaRoomDb extends RoomDatabase {

    public abstract CanadaDao canadaDao();

    private static final String DB_NAME = "Canada.db";
    private static CanadaRoomDb instance;


    public static CanadaRoomDb getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), CanadaRoomDb.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}

