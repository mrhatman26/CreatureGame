package com.gdx.creatureg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import org.w3c.dom.css.Rect;

import java.awt.*;

public class Creature extends DirectionParent{
    private static final int MAX_HUNGER = 1000;
    private static final int MAX_LIFETIME = 10000;
    private int creatureType, hunger, hungerDamageTimer, hungerIncreaseAmount, lifeTime, health;
    private float energy, energyMax;
    private boolean sleeping;

    Creature(int startX, int startY, double startDir, int creatureType, int creatureID){
        super(startX, startY, startDir, creatureID);
        this.creatureType = creatureType;
        switch (this.creatureType){
            case 0: //Orange creature
                this.sprite = staticMethods.spriteTest(Gdx.files.internal("spr_creature_orange.png"));
                break;
            case 1: //Greenish
                this.sprite = staticMethods.spriteTest(Gdx.files.internal("spr_creature_greenish.png"));
                break;
            case 2: //Blue
                this.sprite = staticMethods.spriteTest(Gdx.files.internal("spr_creature_blue.png"));
                break;
        }
        this.direction = staticMethods.getRandom(0, 360);
        this.health = 100;
        this.hunger = 0;
        this.lifeTime = 0;
        this.spriteWidth = sprite.getWidth();
        this.spriteHeight = sprite.getHeight();
        this.speed = staticMethods.getRandom(4, 8);
        this.energy = (float) 1000;
        this.energyMax = (float) 1000;
        this.sleeping = false;
        this.hungerDamageTimer = 100;
        this.hungerIncreaseAmount = 1;
    }

    private void endOfLife(){
        CreatureHandler.deleteCreature(this.id);
    }

    private void checkForDeleteClick(){ //Debug, remove this eventually.
        if (checkForClick(true, true)){
            endOfLife();
        }
    }

    private void updateLifetime(){
        this.lifeTime += 1;
        if (this.lifeTime > this.MAX_LIFETIME){
            endOfLife();
        }
    }

    public void damageHealth(int damageAmount){
        this.health -= damageAmount;
    }

    public void drawOwnText(SpriteBatch batch, BitmapFont font){
        if (CreatureGame.debug) {
            font.draw(batch, "Health: " + String.valueOf(this.health), this.moveRect.x, this.moveRect.y + 40);
            font.draw(batch, "CreatureType: " + String.valueOf(this.creatureType), this.moveRect.x, this.moveRect.y + 80);
            font.draw(batch, "LifeTime: " + String.valueOf(this.lifeTime), this.moveRect.x, this.moveRect.y + 120);
            font.draw(batch, "energy: " + String.valueOf(this.energy), this.moveRect.x, this.moveRect.y + 160);
            font.draw(batch, "energyMax: " + String.valueOf(this.energyMax), this.moveRect.x, this.moveRect.y + 200);
            font.draw(batch, "sleeping: " + String.valueOf(this.sleeping), this.moveRect.x, this.moveRect.y + 240);
            font.draw(batch, "hunger: " + String.valueOf(this.hunger), this.moveRect.x, this.moveRect.y + 280);
            font.draw(batch, "hungerIncreaseAmount: " + String.valueOf(this.hungerIncreaseAmount), this.moveRect.x, this.moveRect.y + 320);
            font.draw(batch, "hungerDamageTimer: " + String.valueOf(this.hungerDamageTimer), this.moveRect.x, this.moveRect.y + 360);
        }
    }

    public void updateEnergy(){
        this.energy--;
        if (this.energy < 1){
            this.sleeping = true;
        }
    }

    public void sleep(){
        this.speed = 0;
        this.energy++;
        if (this.energy >= this.energyMax){
            this.energy = this.energyMax;
            this.sleeping = false;
            this.energyMax -= ((float) this.lifeTime / (float) MAX_LIFETIME) * 100;
        }
    }

    public void updateHunger(){
        this.hunger += hungerIncreaseAmount;
        if (this.hunger >= MAX_HUNGER){
            this.hunger = MAX_HUNGER;
            this.hungerDamageTimer--;
            if (this.hungerDamageTimer < 1){
                damageHealth(5);
                this.hungerDamageTimer = 100;
                this.hungerIncreaseAmount = Math.round(((float) this.lifeTime / (float) MAX_LIFETIME) * 100);
            }
        }
    }

    private void checkHealth(){
        if (this.health <= 0){
            endOfLife();
        }
    }

    public void update(SpriteBatch batch){
        batch.draw(sprite, moveRect.x, moveRect.y);
        updateLifetime();
        updateHunger();
        if (!sleeping) {
            updateEnergy();
            checkHealth();
            if (!this.movingToTarget) {
                randomMovement();
            } else {
                drawTargetLine(batch);
                pointDirection();
            }
            screenBounce();
            move();
        }
        else{
            sleep();
        }
        checkForDeleteClick();
    }

    public int getCreatureType(){
        return this.creatureType;
    }
}
