package com.gdx.creatureg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.awt.*;

public class DirectionParent{
    protected Texture sprite;
    protected Rectangle moveRect;
    private ShapeRenderer shapeRenderer;
    protected int speed, targetX, targetY, id;
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
        this.shapeRenderer = new ShapeRenderer();
        this.movingToTarget = false;
        this.id = id;
    }

    public void correctDirection(){
        if (this.direction > 360){
            this.direction -= 360;
        }
        if (this.direction < 0){
            this.direction += 360;
        }
    }

    public void screenWrap(){
        if (this.moveRect.x < (0 - this.sprite.getWidth())){
            this.moveRect.x = Gdx.graphics.getWidth();
        }
        if (this.moveRect.x > Gdx.graphics.getWidth()){
            this.moveRect.x = (0 - this.sprite.getWidth());
        }
        if (this.moveRect.y < (0 - this.sprite.getHeight())){
            this.moveRect.y = Gdx.graphics.getHeight();
        }
        if (this.moveRect.y > Gdx.graphics.getHeight()){
            this.moveRect.y = (0 - this.sprite.getHeight());
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

    public void move(){
        correctDirection();
        this.moveRect.x += this.speed * Math.cos(Math.toRadians(direction));
        this.moveRect.y += this.speed * Math.sin(Math.toRadians(direction));
        if (this.moveRect.x == targetX && this.moveRect.y == targetY && movingToTarget){
            this.speed = 0;
        }
        screenWrap();
    }

    public void pointDirection(int targetX, int targetY){
        this.targetX = targetX;
        this.targetY = targetY;
        this.direction = Math.atan2(this.targetY - this.moveRect.y, this.targetX - this.moveRect.x);
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

    public void drawOwnText(SpriteBatch batch, BitmapFont font, int creatureType){
        if (CreatureGame.debug) {
            font.draw(batch, String.valueOf(this.targetX) + " | " + String.valueOf(this.targetY), this.moveRect.x, this.moveRect.y + 80);
            font.draw(batch, String.valueOf(this.direction), this.moveRect.x, this.moveRect.y + 40);
            font.draw(batch, String.valueOf(creatureType), this.moveRect.x, this.moveRect.y + 120);
        }
    }

    public void dispose(){
        this.sprite.dispose();
    }
}