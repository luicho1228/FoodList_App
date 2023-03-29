package com.example.appitemlist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class Receiver extends BroadcastReceiver {
    private Context context;
    private static final String ACTION_CUSTOM_BROADCAST = "com.example.I_AM_HOME";
    private ArrayList<MealItem> mealItems;
    private int randomIndex;
    private MealItem randomRecipe;

    public Receiver(ArrayList<MealItem> mealItems){
        this.mealItems = mealItems;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        String intentAction = intent.getAction();
        displayToastForIntentAction(intentAction);
    }

    private void displayToastForIntentAction(String intentAction) {
        switch (intentAction){
            case "com.example.I_AM_HOME":
                Log.d("Broadcast Received", "Custom Broadcast");
                randomIndex = generateIndex();
                randomRecipe = mealItems.get(randomIndex);
                Toast.makeText(context, "Happy Coocking " + randomRecipe.getTitle() , Toast.LENGTH_LONG).show();
                sendIntent();
                break;
            case Intent.ACTION_POWER_CONNECTED:
                Log.d("Broadcast Received", "Power Connected");
                Toast.makeText(context, "Power Connected", Toast.LENGTH_SHORT).show();
                break;
            case Intent.ACTION_POWER_DISCONNECTED:
                Log.d("Broadcast Received", "Power Disconnected");
                Toast.makeText(context, "Power Disconnected", Toast.LENGTH_SHORT).show();
                break;
            case Intent.ACTION_AIRPLANE_MODE_CHANGED:
                Log.d("Broadcast Received", "Airplane Mode Change");
                Toast.makeText(context,"Airplane Mode Change", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(context, "Unknow Broadcast", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void sendIntent(){
        Intent intent = new Intent(context, MealItemActivity.class);
        intent.putExtra("random_meal", randomRecipe);
        String randomPos = Integer.toString(randomIndex);
        intent.putExtra("random_index", randomPos);
        context.startActivity(intent);
    }

    public int generateIndex(){
        int randNumber =(int) (Math.random() * mealItems.size());
     return randNumber;
    }

}
