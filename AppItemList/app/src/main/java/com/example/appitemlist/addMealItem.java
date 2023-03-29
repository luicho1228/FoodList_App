package com.example.appitemlist;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class addMealItem extends AppCompatActivity implements TextWatcher {

    public static final String TAG = "addMealActivity";
    private Integer image;
    private EditText titleInput;
    private EditText caloriesInput;
    private EditText descInput;
    private EditText linkInput;
    private EditText ingredientsInput;
    private ImageView imageView;
    private Button add;
    private MealItem newItem;
    private Button picButton;
    private MealItem transferItem;
    private  Bitmap selectedImageBitmap;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        Log.d(TAG, "onCreate: ON addMealitem Activity");
        viewsInitializer();
        getIntentData();
    }

    private void getIntentData(){
        Intent intent = getIntent();
        transferItem = (MealItem) intent.getSerializableExtra("Meal_Item");
        String index = intent.getStringExtra("position");
        if( intent.getStringExtra("position") != null){
            position = Integer.parseInt(index);
            Log.d(TAG, "getIntentData: position is: " + position);
        }
    }

    private void viewsInitializer(){
        titleInput = (EditText) findViewById(R.id.titleInput);
        titleInput.addTextChangedListener((TextWatcher) this);
        caloriesInput = (EditText) findViewById(R.id.caloriesInput);
        caloriesInput.addTextChangedListener((TextWatcher) this);
        descInput = (EditText) findViewById(R.id.descInput);
        descInput.addTextChangedListener((TextWatcher) this);
        ingredientsInput = (EditText) findViewById(R.id.ingredientsInput);
        ingredientsInput.addTextChangedListener((TextWatcher) this);
        linkInput = (EditText) findViewById(R.id.linkInput);
        linkInput.addTextChangedListener((TextWatcher) this);
        imageView = (ImageView) findViewById(R.id.addImage);
        newItem = new MealItem();
        add = (Button) findViewById(R.id.addButton);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(transferItem != null){
                    Log.d(TAG, "onClick: uodate ite  is: " + transferItem.getTitle());
                    Intent updateIntent = new Intent(v.getContext(), MealItemActivity.class);
                    updateIntent.putExtra("updateItem", transferItem);
                    updateIntent.putExtra("bitmap", selectedImageBitmap);
                    Log.d(TAG, "onClick:  before sending intent position is: " + position);
                    String index = Integer.toString(position);
                    updateIntent.putExtra("editedIndex",index);
                    startActivity(updateIntent);
                }
                else if (transferItem == null) {
                    Log.d(TAG, "onClick: new item is: " + newItem.getTitle());
                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    intent.putExtra("new Item", newItem);
                    startActivity(intent);
                }
            }
        });
        picButton = (Button) findViewById(R.id.picButton);
        picButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });
    }


    private void imageChooser(){
        ActivityResultLauncher<Intent> launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result ->{
                    if (result.getResultCode()==Activity.RESULT_OK){
                        Intent data = result.getData();
                        if (data != null && data.getData() != null){
                            Uri selectedImageUri = data.getData();
                            selectedImageBitmap = null;
                            try {
                                selectedImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                            }catch (IOException e) {
                                e.printStackTrace();
                            }
                            imageView.setImageBitmap(selectedImageBitmap);
                        }
                    }
                });

        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        launcher.launch(i);

    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}
    @Override
    public void afterTextChanged(Editable s) {
        if(s == titleInput.getEditableText()){
            newItem.setTitle(titleInput.getText().toString());
            if(transferItem != null) {
                transferItem.setTitle(titleInput.getText().toString());
            }
        }
        else if (s == caloriesInput.getEditableText()){
            String caloriesText = caloriesInput.getText().toString();
            try {
                int newCalories = Integer.parseInt(caloriesText);
                newItem.setCalories(newCalories);
                if(transferItem != null) {
                    transferItem.setCalories(newCalories);
                }
            }catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        else if(s == descInput.getEditableText()){
            newItem.setDescription(descInput.getText().toString());
            if(transferItem != null) {
                transferItem.setDescription(descInput.getText().toString());
            }
        }
        else if (s == ingredientsInput.getEditableText()){
            String ingredients = ingredientsInput.getText().toString();
            newItem.setIngredients(ingredients);
            if(transferItem != null){
                transferItem.setIngredients(ingredients);
            }
        }
        else if (s == linkInput.getEditableText()){
            newItem.setLinkToRecipe(linkInput.getText().toString());
            if (transferItem != null){
                transferItem.setLinkToRecipe(linkInput.getText().toString());
            }
        }
    }
}