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

    public static void overwriteTargetToMouse(){
        for (Creature creature: creatures){
            if (creature.getCreatureType() == selectedCreatureType) {
                creature.setTarget(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY(), 5, 5, true);
            }
        }
    }

    private static void overWriteHunger(){
        for (Creature creature: creatures){
            creature.setHunger(creature.getMaxHunger());
        }
    }

    private static void overWriteSleep(){
        for (Creature creature: creatures){
            creature.setEnergy(0);
        }
    }

    private static void overWriteLifetime(){
        for (Creature creature: creatures){
            creature.setLifetime(1000);
        }
    }

    private static void createCreatureAtMouse(){
        creatures.add(new Creature(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY(), 0, selectedCreatureType, creatures.size));
    }

    public static void createCreature(int startX, int startY, int creatureType){
        creatures.add(new Creature(startX, Gdx.graphics.getHeight() - startY, 0, creatureType, creatures.size));
    }

    public static void deleteCreature(int id){
        for (int i = 0; i < creatures.size; i++){
            if (creatures.get(i).id == id){
                creatures.get(i).dispose();
                creatures.removeIndex(i);
            }
        }
    }

    public static Creature getClosestCreature(Creature creature, boolean checkReproduceability){
        Creature closestCreature = null;
        float lowestDist = -1;
        float newDist = -1;
        if (!creatures.isEmpty()){
            for (int i = 0; i < creatures.size; i++){
                if (lowestDist < 0){
                    closestCreature = creatures.get(i);
                    if (checkReproduceability){
                        if (closestCreature.getReproduction() && creature != closestCreature) {
                            lowestDist = creatures.get(i).getPos(true).dst(creature.getPos(true));
                        }
                        else{
                            lowestDist = -1;
                            closestCreature = null;
                        }
                    }
                    else {
                        if (creature != closestCreature) {
                            lowestDist = creatures.get(i).getPos(true).dst(creature.getPos(true));
                        }
                    }
                }
                else{
                    newDist = creatures.get(i).getPos(true).dst(creature.getPos(true));
                    if (checkReproduceability){
                        if (creatures.get(i).getReproduction()){
                            if (newDist <= lowestDist && creatures.get(i) != creature){
                                closestCreature = creatures.get(i);
                                lowestDist = newDist;
                            }
                        }
                    }
                    else {
                        if (newDist <= lowestDist && creatures.get(i) != creature) {
                            closestCreature = creatures.get(i);
                            lowestDist = newDist;
                        }
                    }
                }
            }
            return closestCreature;
        }
        else{
            return null;
        }
    }

    public static void update(SpriteBatch batch, BitmapFont font){
        for (Creature creature: creatures){
            creature.update(batch);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            createCreatureAtMouse();
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
        if (CreatureGame.debug){
            if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) && Gdx.input.isKeyJustPressed(Input.Keys.H)){
                overWriteHunger();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) && Gdx.input.isKeyJustPressed(Input.Keys.S)){
                overWriteSleep();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) && Gdx.input.isKeyJustPressed(Input.Keys.L)){
                overWriteLifetime();
            }
        }
    }

    public static void drawText(SpriteBatch batch, BitmapFont font){
        for (Creature creature: creatures){
            creature.drawOwnText(batch, font);
        }
    }

    public static void dispose(){
        for (Creature creature: creatures){
            creature.dispose();
        }
    }
}
