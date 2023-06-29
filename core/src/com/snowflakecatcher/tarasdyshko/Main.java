package com.snowflakecatcher.tarasdyshko;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.snowflakecatcher.tarasdyshko.screens.MainMenuScreen;

public class Main extends Game {

	public static float SCREEN_WIDTH;
	public static float SCREEN_HEIGHT;

	public SpriteBatch batch;
	public BitmapFont bitmapFont;
	
	@Override
	public void create () {
		SCREEN_WIDTH = Gdx.graphics.getWidth();
		SCREEN_HEIGHT = Gdx.graphics.getHeight();
		batch = new SpriteBatch();
		bitmapFont = new BitmapFont();
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		bitmapFont.dispose();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}
}
