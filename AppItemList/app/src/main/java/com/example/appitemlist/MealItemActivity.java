package com.example.appitemlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MealItemActivity extends AppCompatActivity {
    public static final String TAG = "MealItemActivity";
   private TextView title;
   private TextView calories;
   private TextView description;
   private TextView ingredients;
   private MealItem mealItem;
   private Button back;
   private Button edit;
   private ImageView image;
   private MealItem updateItem;
   private static int position;
   private TextView link;
   private Bitmap imageBit;
   private MealItem randomMeal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_item);
        Log.d(TAG, "onCreate: ON  MealItemActivity");
        getIntentData();
        loadViews();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                if(updateItem != null){
                    Log.d(TAG, "onClick: updatedItem title is: " + updateItem.getTitle());
                    intent.putExtra("updatedItem", updateItem);
                    intent.putExtra("index", position);
                }
                startActivity(intent);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editIntent = new Intent(v.getContext(), addMealItem.class);
                editIntent.putExtra("Meal_Item", mealItem);
                String index = Integer.toString(position);
                editIntent.putExtra("position", index);
                startActivity(editIntent);
            }
        });


    }

    public void loadViews(){
        title = (TextView) findViewById(R.id.mealActTitle);
        calories = (TextView) findViewById(R.id.mealActCal);
        description = (TextView) findViewById(R.id.mealActDesc);
        image = (ImageView) findViewById(R.id.imageView);
        link = (TextView) findViewById(R.id.linkRecipe);
        ingredients =(TextView) findViewById(R.id.ingredients);
        image.setImageResource(mealItem.getImage());
        title.setText(getResources().getString(R.string.name)+mealItem.getTitle());
        String caloriesToString = Integer.toString(mealItem.getCalories());
        Log.d(TAG, "onCreate: calories set to item in detail acivity is: " + caloriesToString);
        calories.setText(getResources().getString(R.string.calories)+caloriesToString + "Kcal");
        description.setText(getResources().getString(R.string.description)+mealItem.getDescription());
        ingredients.setText(getResources().getString(R.string.ingredients));
        String ingredient = mealItem.getIngredients();
        ingredients.append(ingredient);
        link.setText(getResources().getString(R.string.link_to_recipe) + mealItem.getLinkToRecipe());
        back= (Button) findViewById(R.id.backbutton);
        edit = (Button) findViewById(R.id.editbutton);
    }

    public void getIntentData(){

        Intent intent = getIntent();
        if (intent != null){
            mealItem = (MealItem) intent.getSerializableExtra("mealItem");
            updateItem = (MealItem)  intent.getSerializableExtra("updateItem");
            randomMeal = (MealItem) intent.getSerializableExtra("random_meal");
            String index = intent.getStringExtra("index");
            try {
                position = Integer.parseInt(index);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            if(updateItem != null){
                String editedIndex =  intent.getStringExtra("editedIndex");
                position = Integer.parseInt(editedIndex);

                Log.d(TAG, "onCreate: updateItem is not null" );
                Log.d(TAG, "onCreate: position is: " + position );
                mealItem = updateItem;
            }

           if(randomMeal != null){
               String randomIndex = intent.getStringExtra("random_index");
               position = Integer.parseInt(randomIndex);
               mealItem = randomMeal;
           }
        }

    }

}

