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
import com.badlogic.gdx.math.Rectangle;

import java.awt.*;

public class Creature extends DirectionParent{
    private Food targetFood;
    private static final int MAX_HUNGER = 2500;
    private static final int MAX_LIFETIME = 10000;
    private int creatureType, hunger, hungerDamageTimer, hungerIncreaseAmount, eatTimer, lifeTime, health;
    private float energy, energyMax;
    private boolean sleeping, eating, reproduction;

    Creature(int startX, int startY, double startDir, int creatureType, int creatureID){
        super(startX, startY, startDir, creatureID);
        this.creatureType = creatureType;
        switch (this.creatureType){
            case 0: //Orange creature
                this.sprite = staticMethods.spriteTest(Gdx.files.internal("creature_orange/spr_creature_orange.png"));
                this.altSprites[0] = staticMethods.spriteTest(Gdx.files.internal("creature_orange/spr_creature_orange.png")); //Excited
                this.altSprites[1] = staticMethods.spriteTest(Gdx.files.internal("creature_orange/spr_creature_orange_hungry.png")); //Hungry
                this.altSprites[2] = staticMethods.spriteTest(Gdx.files.internal("creature_orange/spr_creature_orange_sleeping.png")); //Sleeping
                this.altSprites[3] = staticMethods.spriteTest(Gdx.files.internal("creature_orange/spr_creature_orange_tired.png")); //Tired
                break;
            case 1: //Greenish
                this.sprite = staticMethods.spriteTest(Gdx.files.internal("creature_green/spr_creature_greenish.png"));
                this.altSprites[0] = staticMethods.spriteTest(Gdx.files.internal("creature_green/spr_creature_greenish.png")); //Excited
                this.altSprites[1] = staticMethods.spriteTest(Gdx.files.internal("creature_green/spr_creature_greenish_hungry.png")); //Hungry
                this.altSprites[2] = staticMethods.spriteTest(Gdx.files.internal("creature_green/spr_creature_greenish_sleeping.png")); //Sleeping
                this.altSprites[3] = staticMethods.spriteTest(Gdx.files.internal("creature_green/spr_creature_greenish_tired.png")); //Tired
                break;
            case 2: //Blue
                this.sprite = staticMethods.spriteTest(Gdx.files.internal("creature_blue/spr_creature_blue.png"));
                this.altSprites[0] = staticMethods.spriteTest(Gdx.files.internal("creature_blue/spr_creature_blue.png")); //Excited
                this.altSprites[1] = staticMethods.spriteTest(Gdx.files.internal("creature_blue/spr_creature_blue_hungry.png")); //Hungry
                this.altSprites[2] = staticMethods.spriteTest(Gdx.files.internal("creature_blue/spr_creature_blue_sleeping.png")); //Sleeping
                this.altSprites[3] = staticMethods.spriteTest(Gdx.files.internal("creature_blue/spr_creature_blue_tired.png")); //Tired\
                break;
        }
        this.moveRect.width = this.sprite.getWidth();
        this.moveRect.height = this.sprite.getHeight();
        this.halfSpriteWidth = this.sprite.getWidth();
        this.halfSpriteHeight = this.sprite.getHeight();
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
        this.eating = false;
        this.hungerDamageTimer = 100;
        this.hungerIncreaseAmount = 1;
        this.eatTimer = 100;
        this.reproduction = false;
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
            font.draw(batch, "targetFood: " + String.valueOf(this.targetFood), this.moveRect.x, this.moveRect.y - 40);
            font.draw(batch, "eating: " + String.valueOf(this.eating), this.moveRect.x, this.moveRect.y - 80);
            font.draw(batch, "eatTimer: " + String.valueOf(this.eatTimer), this.moveRect.x, this.moveRect.y - 120);
        }
    }

    public void updateEnergy(){
        this.energy--;
        if (this.energy < 1 && this.hunger < MAX_HUNGER){
            this.sleeping = true;
        }
        else{
            this.sleeping = false;
        }
    }

    public void setEnergy(float newEnergy){
        this.energy = newEnergy;
    }

    public void sleep(){
        this.speed = 0;
        this.energy += 10;
        if (this.energy >= this.energyMax){
            this.energy = this.energyMax;
            this.sleeping = false;
            this.energyMax -= ((float) this.lifeTime / (float) MAX_LIFETIME) * 100;
            this.speed = staticMethods.getRandom(4, 8);
        }
    }

    public int getMaxHunger(){
        return MAX_HUNGER;
    }

    public void setHunger(int newHunger){
        this.hunger = newHunger;
    }

    public void updateHunger(){
        if (!this.eating) {
            this.hunger += hungerIncreaseAmount;
        }
        if (this.hunger >= MAX_HUNGER){
            if (!movingToTarget) {
                this.targetFood = FoodHandler.getClosestFood(this);
                if (this.targetFood != null) {
                    setTarget((int) this.targetFood.getPos(true).x, (int) this.targetFood.getPos(true).y, this.targetFood.getHalfSpriteWidth(), this.targetFood.getHalfSpriteHeight(),true);
                    checkFoodCollision();
                }
            }
            this.hunger = MAX_HUNGER;
            this.hungerDamageTimer--;
            if (this.hungerDamageTimer < 1){
                damageHealth(5);
                this.hungerDamageTimer = 100;
                this.hungerIncreaseAmount += 1;
            }
        }
    }

    public void checkFoodCollision(){
        if (this.moveRect.overlaps(this.targetFood.getFoodRect())){
            this.targetX = 0;
            this.targetY = 0;
            this.movingToTarget = false;
            this.eating = true;
            speed = 0;
        }
    }

    public void eat(){
        this.eatTimer--;
        if (this.eatTimer < 1){
            this.hunger = 0;
            this.energy += 100;
            if (this.energy > this.energyMax){
                this.energy = this.energyMax;
            }
            this.eating = false;
            if (this.targetFood != null) {
                this.targetFood.setFoodAmount(this.targetFood.getFoodAmount() - 10);
            }
            this.targetFood = null;
            this.eatTimer = 100;
        }
    }

    private void checkHealth(){
        if (this.health <= 0){
            endOfLife();
        }
    }

    public void reproductionCheck() {
        if (!this.reproduction){
            if (this.lifeTime >= MAX_LIFETIME / 10) {
                this.reproduction = true;
            }
        }
        else{
            //Target other creature...
        }
    }

    public void update(SpriteBatch batch){
        if (this.sleeping){
            batch.draw(altSprites[2], moveRect.x, moveRect.y);
        }
        else if (this.hunger >= MAX_HUNGER || this.eating){
            batch.draw(altSprites[1], moveRect.x, moveRect.y);
        }
        else if (this.energy >= this.energyMax){
            batch.draw(altSprites[3], moveRect.x, moveRect.y);
        }
        else {
            batch.draw(sprite, moveRect.x, moveRect.y);
        }
        offScreen();
        updateLifetime();
        updateHunger();
        if (!sleeping) {
            if (this.eating){
                eat();
            }
            updateEnergy();
            checkHealth();
            if (!this.movingToTarget) {
                if (!this.eating) {
                    randomMovement();
                }
            } else {
                drawTargetLine(batch);
                pointDirection(true);
            }
            screenBounce();
            move();
            /*Sometimes, when creatures have a target, they perpetually move in one direction and don't stop
            even if they reach the edges of the screen... Investigate this*/
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
