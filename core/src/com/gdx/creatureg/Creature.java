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
    private static final int MAX_HUNGER = 100;
    private static final int MAX_LIFETIME = 100;
    private int creatureType, hunger, lifeTime, health;

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
    }

    private void checkForDeleteClick(){
        if (checkForClick(true, true)){
            CreatureHandler.deleteCreature(this.id);
        }
    }

    public void update(SpriteBatch batch){
        batch.draw(sprite, moveRect.x, moveRect.y);
        speed = staticMethods.getRandom(4, 8);
        pointDirection(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
        move();
        drawTargetLine(batch);
        checkForDeleteClick();
    }

    public int getCreatureType(){
        return this.creatureType;
    }
}
