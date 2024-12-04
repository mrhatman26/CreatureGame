package com.gdx.creatureg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class CreatureHandler {
    private Array<Creature> creatures;
    private int selectedCreatureType;
    CreatureHandler(){
        this.creatures = new Array<Creature>();
        this.selectedCreatureType = 0;
    }

    private void createCreature(){
        creatures.add(new Creature(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY(), 0, selectedCreatureType));
    }

    public void update(SpriteBatch batch, BitmapFont font){
        for (Creature creature: creatures){
            creature.update(batch);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            createCreature();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)){
            selectedCreatureType = 0;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)){
            selectedCreatureType = 1;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)){
            selectedCreatureType = 2;
        }
    }

    public void drawText(SpriteBatch batch, BitmapFont font){
        for (Creature creature: creatures){
            creature.drawOwnText(batch, font, creature.getCreatureType());
        }
    }

    public void dispose(){
        for (Creature creature: creatures){
            creature.dispose();
        }
    }
}
