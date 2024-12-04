package com.gdx.creatureg;

import com.badlogic.gdx.Gdx;
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
    private int creatureType, moveTimer;

    Creature(int startX, int startY, double startDir, int creatureType){
        super(startX, startY, startDir);
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
        this.moveTimer = 100;
    }

    public void update(SpriteBatch batch){
        batch.draw(sprite, moveRect.x, moveRect.y);
        speed = 10;
        //moveTimer--;
        if (moveTimer < 1){
            direction = staticMethods.getRandom(0, 360);
            moveTimer = 100;
        }
        pointDirection(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
        move();
        drawTargetLine(batch);
    }

    public int getCreatureType(){
        return this.creatureType;
    }
}
