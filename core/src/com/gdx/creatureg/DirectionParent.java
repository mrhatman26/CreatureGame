package com.gdx.creatureg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Rectangle;

import java.awt.*;

public class DirectionParent{
    protected Texture sprite;
    protected Texture[] altSprites;
    protected Rectangle moveRect;
    private ShapeRenderer shapeRenderer;
    protected int speed, targetX, targetY, targetHalfWidth, targetHalfHeight, id, moveTimer, spriteWidth, spriteHeight, halfSpriteHeight, halfSpriteWidth;
    protected double direction;
    protected boolean movingToTarget;

    DirectionParent(int startPosX, int startPosY, double startDir, int id){
        this.sprite = staticMethods.spriteTest(Gdx.files.internal("spr_ball.png"));
        this.moveRect = new Rectangle();
        this.moveRect.x = startPosX;
        this.moveRect.y = startPosY;
        this.speed = 0;
        this.direction = startDir;
        this.targetX = 0;
        this.targetY = 0;
        this.targetHalfWidth = 5;
        this.targetHalfHeight = 5;
        this.shapeRenderer = new ShapeRenderer();
        this.movingToTarget = false;
        this.id = id;
        this.moveTimer = staticMethods.getRandom(50, 150);
        this.spriteWidth = sprite.getWidth();
        this.spriteHeight = sprite.getHeight();
        this.halfSpriteWidth = this.sprite.getWidth();
        this.halfSpriteHeight = this.sprite.getHeight();
        this.altSprites = new Texture[4];
    }

    public Vector2 getPos(){
        return new Vector2(this.moveRect.x, this.moveRect.y);
    }

    public void correctDirection(){
        if (this.direction > 360){
            this.direction -= 360;
        }
        if (this.direction < 0){
            this.direction += 360;
        }
    }

    public void screenBounce(){
        if (this.moveRect.x < 0 || this.moveRect.x + this.spriteWidth > Gdx.graphics.getWidth() || this.moveRect.y < 0 || this.moveRect.y + this.spriteHeight > Gdx.graphics.getHeight()){
            this.direction += 180;
        }
        correctDirection();
    }

    public void offScreen(){
        if (this.moveRect.x < 0 - this.spriteWidth || this.moveRect.x > Gdx.graphics.getWidth() + this.spriteWidth || this.moveRect.y < 0 - this.spriteHeight || this.moveRect.y > Gdx.graphics.getHeight() + this.spriteHeight){
            CreatureHandler.deleteCreature(this.id);
        }
    }

    public boolean checkForClick(boolean checkRightButton, boolean checkShift){
        if (checkRightButton){
            if (checkShift){
                if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) && Gdx.input.isButtonPressed(Input.Buttons.RIGHT)){
                    if (Gdx.input.getX() >= this.moveRect.x && Gdx.input.getX() <= this.moveRect.x + this.sprite.getWidth() && Gdx.graphics.getHeight() - Gdx.input.getY() >= this.moveRect.y && Gdx.graphics.getHeight() - Gdx.input.getY() <= this.moveRect.y + this.sprite.getHeight()){
                        return true;
                    }
                }
            }
            else{
                if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)){
                    if (Gdx.input.getX() >= this.moveRect.x && Gdx.input.getX() <= this.moveRect.x + this.sprite.getWidth() && Gdx.graphics.getHeight() - Gdx.input.getY() >= this.moveRect.y && Gdx.graphics.getHeight() - Gdx.input.getY() <= this.moveRect.y + this.sprite.getHeight()){
                        return true;
                    }
                }
            }
        }
        else{
            if (checkShift){
                if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) && Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                    if (Gdx.input.getX() >= this.moveRect.x && Gdx.input.getX() <= this.moveRect.x + this.sprite.getWidth() && Gdx.graphics.getHeight() - Gdx.input.getY() >= this.moveRect.y && Gdx.graphics.getHeight() - Gdx.input.getY() <= this.moveRect.y + this.sprite.getHeight()){
                        return true;
                    }
                }
            }
            else{
                if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                    if (Gdx.input.getX() >= this.moveRect.x && Gdx.input.getX() <= this.moveRect.x + this.sprite.getWidth() && Gdx.graphics.getHeight() - Gdx.input.getY() >= this.moveRect.y && Gdx.graphics.getHeight() - Gdx.input.getY() <= this.moveRect.y + this.sprite.getHeight()){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void randomMovement(){
        this.moveTimer--;
        if (this.moveTimer < 1){
            direction = staticMethods.getRandom(0, 360);
            speed = staticMethods.getRandom(4, 8);
            this.moveTimer = staticMethods.getRandom(50, 150);
        }
    }

    public void move(){
        correctDirection();
        this.moveRect.x += this.speed * Math.cos(Math.toRadians(direction));
        this.moveRect.y += this.speed * Math.sin(Math.toRadians(direction));
        if (movingToTarget){
            reachedTarget();
        }
    }

    public void setTarget(int targetX, int targetY, int targetHalfWidth, int targetHalfHeight, boolean moveImmediately){
        this.targetX = targetX;
        this.targetY = targetY;
        this.targetHalfWidth = targetHalfWidth;
        this.targetHalfHeight = targetHalfHeight;
        if (moveImmediately){
            this.movingToTarget = true;
            this.speed = staticMethods.getRandom(4, 8);
        }
    }

    public void reachedTarget(){ //This works on the idea that we are always targeting the centre of an object.
        if (this.moveRect.x + this.halfSpriteWidth >= this.targetX - this.targetHalfWidth && this.moveRect.x + this.halfSpriteWidth <= this.targetX + this.targetHalfWidth && this.moveRect.y + this.halfSpriteHeight >= this.targetY - this.targetHalfHeight && this.moveRect.y + this.halfSpriteHeight <= this.targetY + this.targetHalfHeight){
            this.targetX = 0;
            this.targetY = 0;
            this.movingToTarget = false;
        }
    }

    public void pointDirection(boolean fromThisCentre){ //Avoid making this false.
        if (fromThisCentre){
            this.direction = Math.atan2(this.targetY - (this.moveRect.y + this.halfSpriteHeight), this.targetX - (this.moveRect.x + this.halfSpriteWidth));
        }
        else {
            this.direction = Math.atan2(this.targetY - this.moveRect.y, this.targetX - this.moveRect.x);
        }
        this.direction = Math.toDegrees(this.direction);
    }

    public void drawTargetLine(SpriteBatch batch){
        batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(255, 255, 255, 1);
        shapeRenderer.rectLine(new Vector2(this.moveRect.x, this.moveRect.y), new Vector2(this.targetX, this.targetY), 1);
        shapeRenderer.setColor(0, 0, 0, 1);
        shapeRenderer.end();
        batch.begin();
    }

    public void dispose(){
        this.sprite.dispose();
        for (int i = 0; i < this.altSprites.length; i++){
            this.altSprites[i].dispose();
        }
    }
}
