package com.mygdx.asteroids;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;

public class Application extends Game { // Game vs ApplicationAdapter? https://www.echalk.co.uk/amusements/Games/asteroidsClassic/ateroids.html
    SpriteBatch batch; // draws to screen
    Texture img;
    TextureRegion shipImg; //Takes a piece from Texture, more efficient to load just one time.
    TextureRegion movingShip;
    Ship ship;
    Sprite shipSprite;
    Sprite movingShipSprite;
    float rotation; // Keep track of rotation of ship

    @Override
    public void create() {
        batch = new SpriteBatch();
        img = new Texture("asteroids.png"); // https://www.youtube.com/watch?v=ONe91ro51cQ
        shipImg = new TextureRegion(img, 96, 128, 48, 32); // Top of window is y = 0, 80+48=128 from sheet, cutting guide 2x then / 2
        movingShip = new TextureRegion(img, 144, 128, 48, 32);
        ship = new Ship(300, 300);
        shipSprite = new Sprite(shipImg);
        movingShipSprite = new Sprite(movingShip);
        this.rotation = 90;
        shipSprite.rotate(90);// Look into (sprites, TextureRegion etc), GDX Texture Packer
        movingShipSprite.rotate(90);

    }

    @Override
    public void render() { // render() is called 60 times a second
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ScreenUtils.clear(0, 0, 0, 0);
        batch.begin();
        shipSprite.draw(batch);
        shipSprite.setPosition(ship.getX(), ship.getY()); // updates position of ship sprite
        batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            batch.begin();
            shipSprite.rotate(5);
            movingShipSprite.rotate(5);
            rotation += 5;
            batch.end();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            batch.begin();
            shipSprite.rotate(-5);
            rotation -= 5;
            batch.end();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) { //
            ship.setSpeedX((float) ((ship.getSpeedX()) + 0.1 * MathUtils.cosDeg(rotation)));
            ship.setSpeedY((float) ((ship.getSpeedY()) + 0.1 * MathUtils.sinDeg(rotation))); //Math for acceleration, pls work (Look more into how it works)
        }

        ship.applySpeed();
        ship.slowDown();

    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }
}
