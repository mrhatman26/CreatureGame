package com.gdx.creatureg;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import jdk.internal.foreign.abi.Binding;

public class CreatureGame extends ApplicationAdapter {
	private static SpriteBatch batch;
	private static CreatureHandler creatureHandler;
	private static FoodHandler foodHandler;
	private static OrthographicCamera camera;
	private static ShapeRenderer shapeRenderer;
	private static BitmapFont font;
	public static boolean debug = true;
	
	@Override
	public void create () {
		if (debug){
			BuildHandler.updateNo();
		}
		batch = new SpriteBatch();
		creatureHandler = new CreatureHandler();
		foodHandler = new FoodHandler();
		camera = new OrthographicCamera();
		shapeRenderer = new ShapeRenderer();
		FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Helvetica.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		fontParameter.size = 16;
		font = fontGenerator.generateFont(fontParameter);
		fontGenerator.dispose();
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		camera.update();
		batch.begin();
		batch.enableBlending();
		font.draw(batch, "Version: " + BuildHandler.readVersionNo(), 32, Gdx.graphics.getHeight() - 32);
		creatureHandler.drawText(batch, font);
		creatureHandler.update(batch, font);
		foodHandler.update(batch, font);
		batch.disableBlending();
		batch.end();
		if (Gdx.input.isKeyPressed(Input.Keys.ALT_LEFT) && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
			creatureHandler.overwriteTargetToMouse();
		}
		staticMethods.miscControls();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		creatureHandler.dispose();
		foodHandler.dispose();
	}
}
