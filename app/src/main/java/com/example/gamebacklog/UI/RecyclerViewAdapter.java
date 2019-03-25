package com.example.gamebacklog.UI;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gamebacklog.Model.Game;
import com.example.gamebacklog.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Game> mData;

    RecyclerViewAdapter(List<Game> data) {
        this.mData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        final Game game = mData.get(position);
        holder.tvTitle.setText(game.getMTitle());
        holder.tvStatus.setText(game.getMStatus());
        holder.tvDate.setText(game.getMDate());
        holder.tvPlatform.setText(game.getMPlatform());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void swapList(List<Game> newList) {
        mData = newList;
        if (newList != null) {
            this.notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvStatus;
        TextView tvDate;
        TextView tvPlatform;
        CardView cardView;

        public ViewHolder(View v) {
            super(v);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvPlatform = itemView.findViewById(R.id.tvPlatform);
            cardView = itemView.findViewById(R.id.cvGames);
            cardView.setClickable(true);
        }
    }
}
