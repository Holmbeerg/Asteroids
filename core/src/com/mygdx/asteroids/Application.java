package com.mygdx.asteroids;

/*
# TODO: Fix bullet positioning on sprite
A Sprite is always rectangular and its position (x, y) are located in the bottom left corner of that rectangle.
A Sprite also has an origin around which rotations and scaling are performed (that is, the origin is not modified by rotation and scaling).
The origin is given relative to the bottom left corner of the Sprite, its position.
 */

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.asteroids.screens.GameScreen;

public class Application extends Game {
    public SpriteBatch batch;
    public BitmapFont font;


    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        this.setScreen(new GameScreen(this));
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ScreenUtils.clear(0, 0, 0, 0);
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}