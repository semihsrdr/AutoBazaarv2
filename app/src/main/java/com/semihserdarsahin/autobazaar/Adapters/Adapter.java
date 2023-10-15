package com.semihserdarsahin.autobazaar.Adapters;

import android.annotation.SuppressLint;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.semihserdarsahin.autobazaar.Objects.Car;
import com.semihserdarsahin.autobazaar.Activities.DetailsActivity;
import com.semihserdarsahin.autobazaar.databinding.RecyclerRowBinding;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class Adapter extends RecyclerView.Adapter<Adapter.CarHolder> {
    ArrayList<Car> carList;


    public Adapter(ArrayList<Car> carList) {
        this.carList = carList;
    }

    @NonNull
    @Override
    public CarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRowBinding recyclerRowBinding=RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new CarHolder(recyclerRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CarHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.recyclerRowBinding.textViewTitle.setText(carList.get(position).title);
        holder.recyclerRowBinding.textViewBrand.setText(carList.get(position).brand);
        holder.recyclerRowBinding.textViewYear.setText(carList.get(position).year+" ");

        DecimalFormat df = new DecimalFormat("#,###");
        int price=Integer.parseInt(carList.get(position).price);
        String customizedPrice=df.format(price);

        holder.recyclerRowBinding.textView8.setText(customizedPrice+"TL");

        Picasso.get().load(carList.get(position).firstLink).resize(250,150)
                .centerCrop()
                .into(holder.recyclerRowBinding.imageView);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent=new Intent(holder.itemView.getContext(), DetailsActivity.class);
                intent.putExtra("selectedCar",carList.get(position));
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public class CarHolder extends RecyclerView.ViewHolder {
        RecyclerRowBinding recyclerRowBinding;
        public CarHolder(RecyclerRowBinding recyclerRowBinding) {
            super(recyclerRowBinding.getRoot());
            this.recyclerRowBinding=recyclerRowBinding;
        }
    }
}
