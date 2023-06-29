package com.snowflakecatcher.tarasdyshko.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.snowflakecatcher.tarasdyshko.Main;
import com.snowflakecatcher.tarasdyshko.actors.SnowflakeActor;

import java.util.Iterator;

public class GameScreen implements Screen {

    public static float SCREEN_WIDTH;
    public static float SCREEN_HEIGHT;

    final Main main;
    private OrthographicCamera camera;
    private Stage stage;
    private Viewport viewport;
    private float scale = Gdx.graphics.getHeight() / 800;

    public static boolean isPaused = false;
    private boolean isGameOver = false;

    private ImageButton pauseButton;
    private ImageButton restartButton;

    private Array<SnowflakeActor> snowflakes = new Array<>();
    private Group snowflakesGroup;
    private long lastDropTime = 0;

    private float Score = 0;
    private float fontScale = 1.5f * scale;
    private GlyphLayout fontBounds = new GlyphLayout();



    public GameScreen(Main main) {
        this.main = main;
        SCREEN_WIDTH = Gdx.graphics.getWidth();
        SCREEN_HEIGHT = Gdx.graphics.getHeight();
    }

    @Override
    public void show() {
        setCamera();

        Texture buttonPauseTexture = new Texture("pause-button.png");
        Texture buttonRestartTexture = new Texture("restart_button.png");

        Image pauseButtonImage = new Image(buttonPauseTexture);
        Image restartButtonImage = new Image(buttonRestartTexture);

        pauseButton = new ImageButton(pauseButtonImage.getDrawable());
        restartButton = new ImageButton(restartButtonImage.getDrawable());

        pauseButton.setSize(50 * scale, 50 * scale);
        restartButton.setSize(50 * scale, 50 * scale);


        pauseButton.setPosition(SCREEN_WIDTH - 20 - pauseButton.getWidth(), SCREEN_HEIGHT - 20 - pauseButton.getHeight());
        restartButton.setPosition(SCREEN_WIDTH/2 - restartButton.getWidth()/2, SCREEN_HEIGHT/7*3);

        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isPaused = !isPaused;
            }
        });
        restartButton.setVisible(false);
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isPaused = !isPaused;
                main.setScreen(new GameScreen(main));
            }
        });

        main.bitmapFont.getData().setScale(fontScale);

        snowflakesGroup = new Group();

        stage.addActor(pauseButton);
        stage.addActor(restartButton);
        stage.addActor(snowflakesGroup);
    }

    @Override
    public void render(float delta) {
        updateCamera();

        stage.draw();

        main.batch.begin();
        renderText();
        main.batch.end();


        if (!isPaused) {
            stage.act(delta);
            update();
        }
    }

    private void update() {
        spawnSnowflake();
        moveSnowflakes();
    }

    @Override
    public void resize(int width, int height) {
        resizeCamera(width, height);
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

    }

    private void setCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, camera);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
    }

    private void updateCamera() {
        ScreenUtils.clear(124, 163, 173, 1);
        camera.update();
        viewport.apply();
        main.batch.setProjectionMatrix(viewport.getCamera().combined);
    }

    private void resizeCamera(int width, int height) {
        viewport.update(width, height, true);
        stage.getViewport().update(width, height, true);
        camera.setToOrtho(false, width, height);
    }



    private void renderText() {
        main.bitmapFont.getData().setScale(fontScale);
        main.bitmapFont.setColor(Color.BLACK);
        main.bitmapFont.draw(main.batch, "Score: " + (int) Score, 20, SCREEN_HEIGHT - 20);

        if (isPaused && !isGameOver) {
            fontBounds.setText(main.bitmapFont, "PAUSE");
            float textWidth = fontBounds.width;
            float textX = SCREEN_WIDTH / 2 - textWidth / 2;
            main.bitmapFont.draw(main.batch, "PAUSE", textX, SCREEN_HEIGHT/2);
        }

        if (isGameOver && isPaused) {
            fontBounds.setText(main.bitmapFont, "GAME OVER");
            float textWidth = fontBounds.width;
            float textX = SCREEN_WIDTH / 2 - textWidth / 2;
            main.bitmapFont.draw(main.batch, "GAME OVER", textX, SCREEN_HEIGHT/2);
        }
    }

    private void spawnSnowflake() {
        if (TimeUtils.nanoTime() - lastDropTime > 1000000000) {
            final SnowflakeActor snowflakeActor = new SnowflakeActor(new Texture("snowflake.png"), 120*scale, 120*scale);
            snowflakes.add(snowflakeActor);
            snowflakesGroup.addActor(snowflakeActor);
            lastDropTime = TimeUtils.nanoTime();
            snowflakeActor.setTouchable(Touchable.enabled);
            snowflakeActor.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    snowflakes.removeValue(snowflakeActor, false);
                    snowflakesGroup.removeActor(snowflakeActor);
                    Score++;
                }
            });

        }
    }

    private void moveSnowflakes() {
        for (Iterator<SnowflakeActor> iterator = snowflakes.iterator(); iterator.hasNext(); ) {
            SnowflakeActor snowflake = iterator.next();
            snowflake.y -= snowflake.speed * Gdx.graphics.getDeltaTime();
            snowflake.setPosition(snowflake.x, snowflake.y);
            if (snowflake.getY() + snowflake.getHeight() < 0) {
//                Score++;
//                iterator.remove();
//                snowflakesGroup.removeActor(snowflake);
                isGameOver = true;
                isPaused = true;
                restartButton.setVisible(true);
            }
        }
    }
}
