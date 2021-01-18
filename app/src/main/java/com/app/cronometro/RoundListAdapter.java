package com.app.cronometro;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

public class RoundListAdapter extends RecyclerView.Adapter<RoundListAdapter.RoundListAdapterViewHolder> {

    private Activity activity;
    private ArrayList<ArrayList<String>> rounds;

    public RoundListAdapter(Activity activity, ArrayList<ArrayList<String>> rounds) {
        this.activity = activity;
        this.rounds = rounds;
    }

    @NonNull
    @Override
    public RoundListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.cardview_round, parent, false);
        return new RoundListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoundListAdapterViewHolder holder, int position) {
        final ArrayList<String> round = rounds.get(position);

        holder.eTxtPosition.setText(String.valueOf(position + 1));
        holder.eTxtCrono.setText(round.get(0));
        holder.eTxtTime.setText(round.get(1));
    }

    public void update(ArrayList<ArrayList<String>> rounds) {
        this.rounds = rounds;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return rounds.size();
    }

    public class RoundListAdapterViewHolder extends RecyclerView.ViewHolder {

        private TextView eTxtPosition, eTxtCrono, eTxtTime;

        public RoundListAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            eTxtPosition = itemView.findViewById(R.id.cViewRoundPosition);
            eTxtCrono = itemView.findViewById(R.id.cViewRoundCrono);
            eTxtTime = itemView.findViewById(R.id.cViewRoundTime);
        }
    }
}
