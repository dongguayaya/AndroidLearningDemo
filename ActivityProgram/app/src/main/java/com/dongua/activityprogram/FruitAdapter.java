package com.dongua.activityprogram;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {
    private List<Fruit> mFruitList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //将viewholder的
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.fruit_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.fruitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position =holder.getAdapterPosition();
                Fruit fruit=mFruitList.get(position);
                Toast.makeText(view.getContext(),"你点击了View"+fruit.getName(),Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Fruit fruit=mFruitList.get(position);
    holder.fruitImage.setImageResource(fruit.getImageId());
    holder.fruitName.setText(fruit.getName());
    }

    @Override
    public int getItemCount() {
        return mFruitList.size();
    }

    public FruitAdapter(List<Fruit> mFruitList) {
        this.mFruitList = mFruitList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        View fruitView;
        ImageView fruitImage;
        TextView fruitName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fruitView=itemView;
            fruitImage=itemView.findViewById(R.id.fruit_image);
            fruitName=itemView.findViewById(R.id.fruit_name);
        }
    }
}
