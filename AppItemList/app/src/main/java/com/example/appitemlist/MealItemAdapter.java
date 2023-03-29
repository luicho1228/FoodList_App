package com.example.appitemlist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MealItemAdapter extends RecyclerView.Adapter<MealItemAdapter.MealItemHolder> {

    public static final String TAG = "MealItemAdapter";
    private Context context;
    public ArrayList<MealItem> mealItems;
    private final RecycleViewInterface recycleViewInterface;
    private String title;
    private Integer image;
    private String caloriesToString;
    private  String calories;

    public MealItemAdapter(Context context, ArrayList<MealItem> mealItems, RecycleViewInterface recycleViewInterface, String cal ) {
        this.context = context;
        this.mealItems = mealItems;
        Log.d(TAG, "Size of mealItems in mealAdapter: " + this.mealItems.size());
        this.recycleViewInterface = recycleViewInterface;
        calories = cal;
    }

    @NonNull
    @Override
    public MealItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycle_view_comp, parent, false);
        return new MealItemHolder(v, recycleViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MealItemHolder holder, int position) {
        if (mealItems.get(position) != null) {
            title = mealItems.get(position).getTitle();
            image = mealItems.get(position).getImage();
            caloriesToString = Integer.toString(mealItems.get(position).getCalories());
            holder.titleView.setText(title);
            holder.myImageView.setImageResource(image);
            holder.myImageView.setImageResource(image);
            holder.calories.setText(calories + caloriesToString + "Kcal");
        }
    }
    @Override
    public int getItemCount() {
        return mealItems.size();
    }

    public static class MealItemHolder extends RecyclerView.ViewHolder{
        ImageView myImageView;
        TextView titleView;
        TextView calories;
        public MealItemHolder(@NonNull View itemView, RecycleViewInterface recycleViewInterface) {
            super(itemView);
            titleView = itemView.findViewById(R.id.titleId);
            myImageView = itemView.findViewById(R.id.Item_image);
            calories = itemView.findViewById(R.id.caloriesId);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recycleViewInterface != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            recycleViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(recycleViewInterface != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            recycleViewInterface.onLongItemClick(pos);
                        }
                    }
                    return false;
                }
            });
        }
    }
}
