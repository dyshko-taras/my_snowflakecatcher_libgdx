package com.snowflakecatcher.tarasdyshko.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.snowflakecatcher.tarasdyshko.Main;

public class MainMenuScreen implements Screen {

    public static final float SCREEN_WIDTH = Main.SCREEN_WIDTH;
    public static final float SCREEN_HEIGHT = Main.SCREEN_HEIGHT;

    private final Main main;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage stage;
    private float scale = Gdx.graphics.getHeight() / 800;

    private Texture logoTexture;
    private Image logoImage;
    private ImageButton buttonPlay;

    public MainMenuScreen(Main main) {
        this.main = main;
    }


    @Override
    public void show() {
        setCamera();

        Texture buttonPlayTexture = new Texture("button_play.png");
        Image playButtonImage = new Image(buttonPlayTexture);
        buttonPlay = new ImageButton(playButtonImage.getDrawable());
        buttonPlay.setSize(200 * scale, 200 * scale);
        buttonPlay.setPosition(SCREEN_WIDTH / 2 - buttonPlay.getWidth() / 2, SCREEN_HEIGHT / 4 - buttonPlay.getHeight() / 2);

        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.setScreen(new GameScreen(main));
            }
        });

        logoTexture = new Texture("logo.png");
        logoImage = new Image(logoTexture);
        logoImage.setSize(logoImage.getWidth() * scale, logoImage.getHeight() * scale);
        logoImage.setPosition(SCREEN_WIDTH / 2 - logoImage.getWidth() / 2, SCREEN_HEIGHT/2);

        stage.addActor(buttonPlay);
        stage.addActor(logoImage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(124, 163, 173, 1);
        camera.update();
        viewport.apply();
        main.batch.setProjectionMatrix(viewport.getCamera().combined);
        main.batch.begin();
        main.batch.end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.setToOrtho(false, width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private void setCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        viewport = new StretchViewport(SCREEN_WIDTH, SCREEN_HEIGHT, camera);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
    }
}
