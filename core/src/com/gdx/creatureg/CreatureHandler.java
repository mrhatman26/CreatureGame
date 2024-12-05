package com.gdx.creatureg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class CreatureHandler {
    private static Array<Creature> creatures;
    private static int selectedCreatureType;
    CreatureHandler(){
        this.creatures = new Array<Creature>();
        this.selectedCreatureType = 0;
    }

    private static void createCreature(){
        creatures.add(new Creature(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY(), 0, selectedCreatureType, creatures.size));
    }

    public static void deleteCreature(int id){
        for (int i = 0; i < creatures.size; i++){
            if (creatures.get(i).id == id){
                creatures.get(i).dispose();
                creatures.removeIndex(i);
            }
        }
    }

    public static void update(SpriteBatch batch, BitmapFont font){
        for (Creature creature: creatures){
            /*if (creature.delete){
                deleteCreature(creature);
            }*/
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

    public static void drawText(SpriteBatch batch, BitmapFont font){
        for (Creature creature: creatures){
            creature.drawOwnText(batch, font, creature.getCreatureType());
        }
    }

    public static void dispose(){
        for (Creature creature: creatures){
            creature.dispose();
        }
    }
}
