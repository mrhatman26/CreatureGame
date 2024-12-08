package com.gdx.creatureg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;

import java.awt.*;

public class Food {
    private Texture sprite;
    private Rectangle foodRect;
    private int foodAmount, foodID, spriteWidth, spriteHeight, halfSpriteWidth, halfSpriteHeight;
    Food(int startX, int startY, int foodID){
        this.sprite = staticMethods.spriteTest(Gdx.files.internal("spr_food.png"));
        this.foodRect = new Rectangle();
        this.foodRect.width = this.sprite.getWidth();
        this.foodRect.height = this.sprite.getHeight();
        this.spriteWidth = this.sprite.getWidth();
        this.spriteHeight = this.sprite.getHeight();
        this.halfSpriteWidth = this.spriteWidth / 2;
        this.halfSpriteHeight = this.spriteHeight / 2;
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

    public int getFoodAmount(){
        return this.foodAmount;
    }

    public Rectangle getFoodRect(){
        return this.foodRect;
    }

    public Vector2 getPos(boolean getCentre){
        if (getCentre){
            return new Vector2(this.foodRect.x + this.halfSpriteWidth, this.foodRect.y + this.halfSpriteHeight);
        }
        else {
            return new Vector2(this.foodRect.x, this.foodRect.y);
        }
    }

    public int getHalfSpriteWidth(){
        return this.halfSpriteWidth;
    }

    public int getHalfSpriteHeight(){
        return this.halfSpriteHeight;
    }

    public int getFoodID(){
        return this.foodID;
    }

    public void setFoodAmount(int newFoodAmount){
        this.foodAmount = newFoodAmount;
    }

    public void update(SpriteBatch batch, BitmapFont font){
        font.draw(batch, "foodAmount: " + String.valueOf(foodAmount), this.foodRect.x, this.foodRect.y + 80);
        batch.draw(this.sprite, this.foodRect.x, this.foodRect.y, 32, 32);
        checkFoodAmount();
    }

    public void dispose(){
        this.sprite.dispose();
    }
}
