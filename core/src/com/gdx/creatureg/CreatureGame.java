package com.gdx.creatureg;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import jdk.internal.foreign.abi.Binding;

public class CreatureGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private CreatureHandler creatureHandler;
	private OrthographicCamera camera;
	private ShapeRenderer shapeRenderer;
	private BitmapFont font;
	public static boolean debug = false;
	
	@Override
	public void create () {
		if (debug){
			BuildHandler.updateNo();
		}
		batch = new SpriteBatch();
		creatureHandler = new CreatureHandler();
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
		font.draw(batch, "Version: " + BuildHandler.readVersionNo(), 32, 688 );
		creatureHandler.drawText(batch, font);
		creatureHandler.update(batch, font);
		batch.disableBlending();
		batch.end();
		staticMethods.miscControls();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		creatureHandler.dispose();
	}
}
