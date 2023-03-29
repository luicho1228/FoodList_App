package com.example.appitemlist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements RecycleViewInterface {

    public static final String TAG = "MainActivity";
    private static final String ACTION_CUSTOM_BROADCAST = "com.example.I_AM_HOME";
    private static ArrayList<MealItem> mealItems;
    private FloatingActionButton fab;
    private Intent detailIntent;
    private Intent addIntent;
    private MealItem newItem;
    private int gridColumnCount;
    private RecyclerView myRecycleView;
    private MealItemAdapter adapter;
    private ItemTouchHelper itemTouchHelper;
    private int dragDirection = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT |ItemTouchHelper.UP | ItemTouchHelper.DOWN;
    private int swipeDirection = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
    private Receiver myreceiver;

    @SuppressLint({"NotifyDataSetChanged", "NotifyItemRemoved","notifyItemMoved"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: ON Main Activity");
        gridColumnCount = getResources().getInteger(R.integer.grid_column_count);
        if(gridColumnCount > 1){
            swipeDirection = 0;}
        loadData();
        loadComponents();
        recycleViewSetUp();
        loadReceiver();
    }

    private void recycleViewSetUp(){
        myRecycleView = findViewById(R.id.MyRecicleView);
        String cal = getResources().getString(R.string.calories);
        adapter = new MealItemAdapter(this, mealItems,this ,cal );
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, gridColumnCount, GridLayoutManager.VERTICAL, false);
        myRecycleView.setLayoutManager(gridLayoutManager);
        itemTouchHelper.attachToRecyclerView(myRecycleView);
        myRecycleView.setHasFixedSize(false);
        myRecycleView.setAdapter(adapter);
    }

    private void loadData(){
        if(mealItems == null) {
            mealItems = new ArrayList<>();
            MealItem mealItem;
            String[] titles = getResources().getStringArray(R.array.meal_titles);
            TypedArray images = getResources().obtainTypedArray(R.array.meal_images);
            int[] image = getResources().getIntArray(R.array.meal_images);
            String[] ingredients = getResources().getStringArray(R.array.igredients);
            String[] description = getResources().getStringArray(R.array.meal_descriptions);
            int[] calories = getResources().getIntArray(R.array.items_caories);
            String linkToRecipe = "no-link-provided";
            if (mealItems.size() == 0) {
                for (int i = 0; i < 5; i++) {
                   mealItem = new MealItem(titles[i],images.getResourceId(i,0),ingredients[i],description[i],calories[i],linkToRecipe);
                   mealItems.add(mealItem);
                }
            }
        }
    }

    private void loadComponents(){
        itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(dragDirection, swipeDirection) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();
                Collections.swap(mealItems,from,to);
                adapter.notifyItemMoved(from, to);
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mealItems.remove(viewHolder.getAdapterPosition());
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });
        Intent intent = getIntent();
        newItem = (MealItem) intent.getSerializableExtra("new Item");
        MealItem updatedItem = (MealItem) intent.getSerializableExtra("updatedItem");
        int position = intent.getIntExtra("index", 0);

        if (newItem != null) {
            Log.d(TAG, "loadComponents:  new Item is: " + newItem.getTitle());
            mealItems.add(newItem);
        }
        if(updatedItem != null){
            Log.d(TAG, "loadComponents:  updated Item is: " + updatedItem.getTitle());
            mealItems.get(position).setAllEqualTo(updatedItem);
        }
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addIntent = new Intent(v.getContext(), addMealItem.class);
                startActivity(addIntent);
            }
        });
    }
    @Override
    public void onItemClick(int position) {
        detailIntent = new Intent(this, MealItemActivity.class);
        detailIntent.putExtra("mealItem", mealItems.get(position));
        String index = Integer.toString(position);
        detailIntent.putExtra("index", index);
        startActivity(detailIntent);
    }
    @Override
    public void onLongItemClick(int position) {
        mealItems.remove(position);
        adapter.notifyItemRemoved(position);
    }

    public void loadReceiver(){
        myreceiver = new Receiver(mealItems);
        registerReceiver(myreceiver, addIntentFilters());
    }

    public IntentFilter addIntentFilters(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.I_AM_HOME");
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        intentFilter.addAction(Intent.ACTION_BATTERY_OKAY);
        intentFilter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        return intentFilter;
    }
}