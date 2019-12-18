package com.infy.telstraassignment_1.mvp.canadaslist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.inputmethodservice.Keyboard;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.infy.telstraassignment_1.R;
import com.infy.telstraassignment_1.model.Canada;
import com.infy.telstraassignment_1.roomdb.CanadaRoomDb;
import com.infy.telstraassignment_1.util.Utility;
import com.infy.telstraassignment_1.model.CanadaRepo;
import com.infy.telstraassignment_1.roomdb.RoomEntity;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment implements CanadasListContract{

    RecyclerView canadaList;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView netWorkStatus;

    CanadaRepo interpreter;
    CanadasAdapter canadasAdapter;
    BroadcastReceiver networkReceiver;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.frag_main, container, false);
        canadaList = rootView.findViewById(R.id.mRcvTitlesList);
        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout);
        netWorkStatus = rootView.findViewById(R.id.netWorkStatus);
        interpreter = new CanadaPresenter(this);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                checkInternetStatus();
            }
        });
        checkInternetStatus();
        return rootView;
    }

    @Override
    public boolean checkIntentConnection(){
        return Utility.checkIntenetConnection(getActivity());
    }


    @Override
    public void setActionBarTitle(String title){
        if (getActivity()!=null)
            ((CanadasListActivity) getActivity()).getSupportActionBar().setTitle(title);
    }


    @Override
    public void setList(final ArrayList<Canada> rowArrayList){
        canadasAdapter = new CanadasAdapter(getActivity(), rowArrayList, new CanadasAdapter.IOnRowClickListener() {
            @Override
            public void onRowClick(int position) {
            }
        });
        canadaList.setLayoutManager(new LinearLayoutManager(getActivity()));
        canadaList.setItemAnimator(new DefaultItemAnimator());
        canadaList.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        canadaList.setAdapter(canadasAdapter);
        canadaList.setHasFixedSize(true);
        canadaList.setItemViewCacheSize(20);
        canadaList.setDrawingCacheEnabled(true);
    }

    @Override
    public void getCanadaListFromLocal() throws Throwable{
        class GetTasks extends AsyncTask<Void, Void, List<RoomEntity>> {

            @Override
            protected List<RoomEntity> doInBackground(Void... voids) {
                if (getActivity()!=null) {
                    List<RoomEntity> taskList = CanadaRoomDb
                            .getInstance(getActivity().getApplicationContext()).canadaDao().getCanadasList();
                    return taskList;
                } else {
                    return null;
                }

            }

            @Override
            protected void onPostExecute(List<RoomEntity> tasks) {
                if (tasks!=null)
                    super.onPostExecute(tasks);
                interpreter.list(tasks);
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();

    }



    @Override
    public void showLoading(){
        swipeRefreshLayout.setRefreshing(true);
    }


    @Override
    public void hideRefreshing(){
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showToast(String message) throws Throwable{
        if (getActivity()!=null)
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void setList(final List<RoomEntity> roomEntityList) throws Throwable{

        class InsertTitles extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                if (getActivity()!=null)
                    CanadaRoomDb.getInstance(getActivity().getApplicationContext()).canadaDao().
                            insertListOfUsers(roomEntityList);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }

        InsertTitles st = new InsertTitles();
        st.execute();
    }


    @Override
    public void clearLocalDb(final List<RoomEntity> roomEntityList) throws Throwable{
        class DeleteTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                if (getActivity()!=null)
                    CanadaRoomDb.getInstance(getActivity().getApplicationContext()).canadaDao().getCanadasList();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                try {
                    setList(roomEntityList);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        }

        DeleteTask dt = new DeleteTask();
        dt.execute();
    }

    public void checkInternetStatus(){
        if (networkReceiver == null){
            networkReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Bundle extras = intent.getExtras();

                    NetworkInfo info = (NetworkInfo) extras
                            .getParcelable("networkInfo");

                    NetworkInfo.State state = info.getState();
                    if (state == NetworkInfo.State.CONNECTED) {
                        netWorkStatus.setVisibility(View.GONE);
                        interpreter.getCanadasList();
                    } else {
                        netWorkStatus.setVisibility(View.VISIBLE);
                        netWorkStatus.setText("Your internet Connection is Disabled");
                        try {
                            getCanadaListFromLocal();
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }
                }
            };
            final IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            getActivity().registerReceiver((BroadcastReceiver) networkReceiver, intentFilter);
        } else {
            hideRefreshing();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (networkReceiver != null){
            getActivity().unregisterReceiver(networkReceiver);
        }
    }
}
