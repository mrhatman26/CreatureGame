package com.gdx.creatureg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.awt.*;

public class FoodHandler {
    private static Array<Food> foods;
    private static int foodSpawnTimer;
    FoodHandler(){
        foods = new Array<Food>();
        foodSpawnTimer = staticMethods.getRandom(100, 1000);
    }

    public static Array<Food> getFoods(){
        return foods;
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

    public static Food getClosestFood(Creature creature){
        Food closestFood = null;
        float lowestDist = -1;
        float newDist = -1;
        if (!foods.isEmpty()) {
            for (int i = 0; i < foods.size; i++){
                if (lowestDist < 0){
                    closestFood = foods.get(i);
                    lowestDist = foods.get(i).getPos(true).dst(creature.getPos(true));
                }
                else{
                    newDist = foods.get(i).getPos(true).dst(creature.getPos(true));
                    if (newDist <= lowestDist){
                        closestFood = foods.get(i);
                        lowestDist = newDist;
                    }
                }
            }
            return closestFood;
        }
        else{
            return null;
        }
    }

    public static void update(SpriteBatch batch, BitmapFont font){
        for (Food food: foods){
            food.update(batch, font);
        }
        foodSpawnTimer--;
        if (foodSpawnTimer < 1){
           createNewFood(staticMethods.getRandom(64, Gdx.graphics.getWidth() - 64), staticMethods.getRandom(64, Gdx.graphics.getHeight() - 64));
            foodSpawnTimer = staticMethods.getRandom(100, 1000);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            createNewFood(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_RIGHT)){
            for (int i = 0; i < foods.size; i++){
                foods.removeIndex(i);
            }
        }
        if (CreatureGame.debug) {
            font.draw(batch, "foodSpawnTimer: " + String.valueOf(foodSpawnTimer), 32, Gdx.graphics.getHeight() - 64);
        }
    }

    public static void dispose(){
        for (Food food: foods){
            food.dispose();
        }
    }
}
