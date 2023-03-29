package com.example.appitemlist;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class MealItem implements Serializable {

    //-------------------Global Variables-------------------//
    private static final String TAG = "MealItem";
    private String title;
    private Integer image;
    private String description;
    private int calories;
    private String ingredients;
    private String linkToRecipe;
    private byte[] imageFromGallery;


    //------------------Constructors------------------------//
    public MealItem(){
        this("New Item", 0, "no-ingredients provided","no-description",0,"no-Link to recipe" );}
    public MealItem(String title){
        this(title, 0, "no-ingredients provided","no-description",0,"no-Link to recipe" );}
    public MealItem(String title, int calories){
        this(title, 0, "no-ingredients provided" ,"no-description",calories,"no-Link to recipe" );}
    public MealItem(String title, int calories, String ingredients){
        this(title, 0, ingredients ,"no-description",calories,"no-Link to recipe" );}
    public MealItem(String title, Integer image,String ingredients, String description, int calories, String linkToRecipe){
        this.title = title;
        this.image = image;
        this.description = description;
        this.calories = calories;
        this.linkToRecipe = linkToRecipe;
        this.ingredients = ingredients;
        if(this.ingredients == null){
            this.ingredients = "No ingredients provided";
        }
    }

    //----------------Setters------------------------///
    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setLinkToRecipe(String linkToRecipe) {
        this.linkToRecipe = linkToRecipe;
    }

    //--------------------Getters------------------//
    public String getTitle() {
        return title;
    }

    public Integer getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public String getIngredients() {
        return ingredients;
    }

    public int getCalories() {
        return calories;
    }

    public String getLinkToRecipe() {
        return linkToRecipe;
    }

    //--------------------Feature Methods--------------------//
    public void setAllEqualTo(MealItem m){
        setTitle(m.getTitle());
        setCalories(m.getCalories());
        setDescription(m.getDescription());
        setIngredients(m.getIngredients());
        setLinkToRecipe(m.getLinkToRecipe());
        setImage(m.getImage());
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MealItem)) return false;
        MealItem mealItem = (MealItem) o;
        return calories == mealItem.calories
                && Objects.equals(title, mealItem.title)
                && Objects.equals(image, mealItem.image)
                && Objects.equals(description, mealItem.description)
                && Objects.equals(ingredients, mealItem.ingredients)
                && Objects.equals(linkToRecipe, mealItem.linkToRecipe)
                && Arrays.equals(imageFromGallery, mealItem.imageFromGallery);
    }
}
