package com.exam.loginapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {

    private ArrayList<String> data;
    private static Context context;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            tView = itemView.findViewById(R.id.textViewLayoutRecycle);


            tView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                Fragment df = new DescriptionFragment();
                Bundle instanceState = new Bundle();
                instanceState.putString("ID", tView.getText().toString());
                df.setArguments(instanceState);
                FragmentTransaction ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.HomeContainerId, df).commit();
                RecyclerView rv = ((FragmentActivity) context).findViewById(R.id.RecycleViewHome);
                rv.setVisibility(view.GONE);
                TextView welcomeHome = ((FragmentActivity)context).findViewById(R.id.HomeHeaderLabel);
                welcomeHome.setVisibility(view.GONE);
                }
            });
        }

        public TextView getTview() { return this.tView; }
    }

    public RecycleAdapter(ArrayList<String> data) { this.data = data;}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        ArrayList<String> datas = new ArrayList<String>();

        holder.getTview().setText(data.get(position));
//        datas.add("test");
//        datas.add("test");
//        datas.add("test");datas.add("test");

//        for (Map.Entry<String, androidVersion> entry: data.entrySet()
//             ) {
////            datas.add(entry.getKey());
//            holder.getTview().setText(entry.getKey());
//        }

//        String AndroidID = datas.get(position);




    }

    @Override
    public int getItemCount() {
        return data.size();
    }


}
