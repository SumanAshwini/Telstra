package com.infy.telstraassignment_1.mvp.canadaslist;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.infy.telstraassignment_1.R;
import com.infy.telstraassignment_1.model.Canada;
import com.infy.telstraassignment_1.model.CanadaModel;

import java.util.ArrayList;

public class CanadasAdapter extends RecyclerView.Adapter<CanadasAdapter.CanadaListHolder> {

    Context context;
    ArrayList<Canada> rowArrayList;
    IOnRowClickListener listener;

    public interface IOnRowClickListener {
        void onRowClick(int position);
    }

    public CanadasAdapter(Context context, ArrayList<Canada> rowArrayList, IOnRowClickListener listener) {
        this.context = context;
        this.rowArrayList = rowArrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CanadaListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_canada_model, parent, false);
        return new CanadaListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CanadaListHolder canadalistholder, final int position) {
        if (rowArrayList.get(position).getTitle() != null){
            canadalistholder.tvTitle.setText(rowArrayList.get(position).getTitle());
        } else {
            canadalistholder.tvTitle.setText("No Title");
        }

        if (rowArrayList.get(position).getDescription() !=null){
            canadalistholder.tvItem.setText(rowArrayList.get(position).getDescription());
        } else {
            canadalistholder.tvItem.setText("No Description");
        }

        String imgUrl = rowArrayList.get(position).getImageHref();
        if (imgUrl!=null && imgUrl.contains("http://")){
            imgUrl = imgUrl.replace("http://", "https://");
        }
        if (imgUrl !=null) {
            try {
                Glide.with(context)
                        .load(imgUrl)
                        .error(R.drawable.no_preview)
                        .placeholder(R.drawable.placeholder)
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                        Target<Drawable> target, boolean isFirstResource) {
                                canadalistholder.pgBar.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model,
                                                           Target<Drawable> target, DataSource dataSource,
                                                           boolean isFirstResource) {
                                canadalistholder.pgBar.setVisibility(View.GONE);
                                return false;
                            }
                        }).into(canadalistholder.imageItem)
                ;

            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            canadalistholder.imageItem.setImageResource(R.drawable.no_preview);
        }

    }

    @Override
    public int getItemCount() {
        return rowArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class CanadaListHolder extends RecyclerView.ViewHolder {
        ImageView imageHref, imageItem;
        TextView tvTitle, tvItem;
        RelativeLayout relList;
        ProgressBar pgBar;

        public CanadaListHolder(@NonNull View itemView) {
            super(itemView);
            imageHref = itemView.findViewById(R.id.imageItem);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvItem = itemView.findViewById(R.id.tvItem);
            imageItem = itemView.findViewById(R.id.imageItems);
            relList = itemView.findViewById(R.id.relListItem);
            pgBar = itemView.findViewById(R.id.pgBar);
        }
    }
}
