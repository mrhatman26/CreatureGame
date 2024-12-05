package com.gdx.creatureg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.gdx.creatureg.CreatureGame;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		new BuildHandler(CreatureGame.debug);
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("CreatureGame");
		config.setWindowedMode(1920, 1080);
		config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
		config.useVsync(false);
		config.setResizable(false);
		new Lwjgl3Application(new CreatureGame(), config);
		UserInput.closeUserInput();
	}
}
