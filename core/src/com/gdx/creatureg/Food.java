package com.gdx.creatureg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.awt.*;

public class Food {
    private Texture sprite;
    private Rectangle foodRect;
    private int foodAmount, foodID;
    Food(int startX, int startY, int foodID){
        this.sprite = staticMethods.spriteTest(Gdx.files.internal("spr_food.png"));
        this.foodRect = new Rectangle();
        this.foodRect.x = startX;
        this.foodRect.y = startY;
        this.foodAmount = 100;
        this.foodID = foodID;
    }

    private void checkFoodAmount(){
        if (this.foodAmount < 1){
            FoodHandler.deleteFood(this.foodID);
        }
    }

    public Vector2 getPos(){
        return new Vector2(this.foodRect.x, this.foodRect.y);
    }

    public int getFoodID(){
        return this.foodID;
    }

    public void update(SpriteBatch batch){
        batch.draw(this.sprite, this.foodRect.x, this.foodRect.y, 32, 32);
        checkFoodAmount();
    }

    public void dispose(){
        this.sprite.dispose();
    }
}
