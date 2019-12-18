package com.infy.telstraassignment_1.mvp.canadaslist;

import android.os.Bundle;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.infy.telstraassignment_1.R;


public class CanadasListActivity extends AppCompatActivity implements View {


    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameLayout = findViewById(R.id.framelayout);
        replaceFragment();
    }


    @Override
    public void replaceFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new MainFragment())
                .addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.framelayout);
        if (fragment instanceof MainFragment){
            finish();
        }else {
            super.onBackPressed();
        }
    }
}