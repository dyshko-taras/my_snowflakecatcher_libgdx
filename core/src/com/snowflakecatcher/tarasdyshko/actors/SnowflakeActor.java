package com.snowflakecatcher.tarasdyshko.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.snowflakecatcher.tarasdyshko.Main;

public class SnowflakeActor extends Actor {
    private Texture img;
    private float scale = Gdx.graphics.getHeight() / 800;
    public float speed = 400 * scale;
    public float x, y;

    public SnowflakeActor(Texture texture, float width, float height) {
        img = texture;
        setX(MathUtils.random(0, Main.SCREEN_WIDTH - width));
        setY(Main.SCREEN_HEIGHT);
        setSize(width, height);
        x = getX();
        y = getY();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(img, getX(), getY(), getWidth(), getHeight());
    }
}
