package com.infy.telstraassignment_1.mvp.canadaslist;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.infy.telstraassignment_1.R;
import com.infy.telstraassignment_1.model.Canada;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CanadasAdapter extends RecyclerView.Adapter<CanadasAdapter.CanadaHolder> {



    private final Context context;

    private final List<Canada> canStrings = new ArrayList<>();

    private static final String TAG = "CanadasAdapter";



    public CanadasAdapter(Context context) {

        this.context = context;

    }



    @Override

    public CanadaHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_canada_model,parent,false);

        return new CanadaHolder(view);

    }




    @Override

    public void onBindViewHolder(CanadaHolder holder, final int position) {

        Log.e(TAG, "onBindViewHolder: "+position);

        holder.canadaTitle.setText(canStrings.get(position).getTitle());

        holder.canadadescription.setText(canStrings.get(position).getDescription());

        holder.canadalistLayout.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                Toast.makeText(context, canStrings.get(position).toString(), Toast.LENGTH_SHORT).show();

            }

        });

    }



    public void setList(List<Canada> list){

        canStrings.clear();

        canStrings.addAll(list);

        notifyDataSetChanged();

        Log.e(TAG, "onNext: "+canStrings.size() );

    }





    @Override

    public int getItemCount() {

        return canStrings.size();

    }



    public static class CanadaHolder extends RecyclerView.ViewHolder {

        TextView canadaTitle;

        TextView canadadescription;

        TextView view;

        LinearLayout canadalistLayout;



        public CanadaHolder(View v) {

            super(v);

            canadalistLayout = (LinearLayout) v.findViewById(R.id.canadalist_layout);

            canadaTitle = (TextView) v.findViewById(R.id.title);

            canadadescription = (TextView) v.findViewById(R.id.description);

        }

    }

}
