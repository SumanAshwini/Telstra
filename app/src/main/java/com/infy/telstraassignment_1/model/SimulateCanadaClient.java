package com.infy.telstraassignment_1.model;

import android.os.Handler;

import com.infy.telstraassignment_1.mvp.canadaslist.CanadasListContract;
import com.infy.telstraassignment_1.util.Utility;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Random;


public final class SimulateCanadaClient implements CanadaRepo {



    static final Random RANDOM = new Random();





    public void getCanadaList(final CanadasListContract.OnResponseCallback callback)  {
        // To imitate network request delay
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList<Canada> canadalist = new ArrayList<>();
                canadalist.add(new Canada("123","ABC"));
                canadalist.add(new Canada("123","ABC"));
                canadalist.add(new Canada("123","ABC"));
                canadalist.add(new Canada("123","ABC"));
                canadalist.add(new Canada("123","ABC"));
                canadalist.add(new Canada("123","ABC"));

                callback.onResponse(canadalist);
            }
        }, 1500);

    }

}
