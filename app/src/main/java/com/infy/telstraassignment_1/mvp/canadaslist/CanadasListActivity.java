package com.infy.telstraassignment_1.mvp.canadaslist;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.infy.telstraassignment_1.R;
import com.infy.telstraassignment_1.model.Canada;
import com.infy.telstraassignment_1.model.SimulateCanadaClient;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
//import static android.support.v4.widget.SwipeRefreshLayout.*;


public class CanadasListActivity extends AppCompatActivity implements CanadasListContract.View{
    private RecyclerView recyclerView;

    private CanadasAdapter canadalistAdapter;

    private SwipeRefreshLayout swipeLayout;

    private CanadasListContract.Presenter presenter;

    private TextView tv_empty_msg;



    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_canada_list);

        initViews();
    }



    private void initViews(){

        presenter = new CanadaPresenter(this, new SimulateCanadaClient());

        recyclerView = (RecyclerView) findViewById(R.id.canadalist_recycler_list); // list

        tv_empty_msg = (TextView)findViewById(R.id.swipe_msg_tv); // empty message

        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // for vertical liner list

        canadalistAdapter = new CanadasAdapter(this);

        recyclerView.setAdapter(canadalistAdapter);

        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);

        swipeLayout.setOnRefreshListener(listener);

        swipeLayout.setColorSchemeColors( // colors for progress dialog

                ContextCompat.getColor(CanadasListActivity.this, R.color.colorPrimary),

                ContextCompat.getColor(CanadasListActivity.this, R.color.colorAccent),

                ContextCompat.getColor(CanadasListActivity.this, android.R.color.holo_green_light)

        );

    }



    private final SwipeRefreshLayout.OnRefreshListener listener = new SwipeRefreshLayout.OnRefreshListener() {

        @Override

        public void onRefresh() {

            presenter.loadCanadaList();

        }

    };



    // for future, to show progress

    @Override

    public void showProgress() {

        swipeLayout.setRefreshing(true);

    }



    @Override

    public void hideProgress() {

        swipeLayout.setRefreshing(false);

    }



    // toggle the visibility of empty textview or list

    // display list only when response it not empty

    private void showORHideListView(boolean flag){

        if (flag){

            tv_empty_msg.setVisibility(View.GONE);

            recyclerView.setVisibility(View.VISIBLE);

        }else {

            tv_empty_msg.setVisibility(View.VISIBLE);

            recyclerView.setVisibility(View.GONE);

        }

    }



    @Override

    public void showCanadaList(List<Canada> canadaList) {

        if (!canadaList.isEmpty()){

            canadalistAdapter.setList(canadaList);

            showORHideListView(true);

        }

    }



    @Override

    public void showLoadingError(String errMsg) {

        hideProgressAndShowErr(errMsg);

        showORHideListView(false);

    }



    private void hideProgressAndShowErr(String msg){

        tv_empty_msg.setVisibility(View.VISIBLE);

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

        showORHideListView(false);

    }



    @Override

    protected void onDestroy() {

        super.onDestroy();

        presenter.dropView();

    }

}
