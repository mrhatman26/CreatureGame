package com.gdx.creatureg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class FoodHandler {
    private static Array<Food> foods;
    private static int foodSpawnTimer;
    FoodHandler(){
        foods = new Array<Food>();
        foodSpawnTimer = staticMethods.getRandom(100, 1000);
    }

    public static void createNewFood(int startX, int startY){
        foods.add(new Food(startX, startY, foods.size));
    }

    public static void deleteFood(int foodID){
        for (int i = 0; i < foods.size; i++){
            if (foods.get(i).getFoodID() == foodID){
                foods.removeIndex(i);
            }
        }
    }

    public static void update(SpriteBatch batch, BitmapFont font){
        for (Food food: foods){
            food.update(batch);
        }
        foodSpawnTimer--;
        if (foodSpawnTimer < 1){
           createNewFood(staticMethods.getRandom(64, Gdx.graphics.getWidth() - 64), staticMethods.getRandom(64, Gdx.graphics.getHeight() - 64));
            foodSpawnTimer = staticMethods.getRandom(100, 1000);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            createNewFood(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
        }
        font.draw(batch, "foodSpawnTimer: " + String.valueOf(foodSpawnTimer), 32, Gdx.graphics.getHeight() - 64);
    }

    public static void dispose(){
        for (Food food: foods){
            food.dispose();
        }
    }
}
