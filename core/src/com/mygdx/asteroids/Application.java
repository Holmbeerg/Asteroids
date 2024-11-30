package com.mygdx.asteroids;

/*
# TODO: Fix bullet positioning on sprite
# TODO: Collision detection
# TODO: Wrap around screen with ship
# TODO: Add sounds, https://classicgaming.cc/classics/asteroids/sounds, SoundManager class?
# TODO: Adjust off-screen detection for asteroids so that they never get immediately deleted upon touching edge
A Sprite is always rectangular and its position (x, y) are located in the bottom left corner of that rectangle.
A Sprite also has an origin around which rotations and scaling are performed (that is, the origin is not modified by rotation and scaling).
The origin is given relative to the bottom left corner of the Sprite, its position.
 */

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Arrays;

public class Application extends Game {
    SpriteBatch batch;
    Texture img;
    TextureRegion shipImg;
    TextureRegion movingShip;
    TextureRegion bulletImg;
    TextureRegion asteroidImg;

    Ship ship;
    Sprite shipSprite;
    Sprite movingShipSprite;
    Sprite bulletSprite;
    Sprite asteroidSprite;

    SoundManager soundManager;
    AsteroidsManager asteroidsManager;
    ShipManager shipManager;
    BulletManager bulletManager;


    @Override
    public void create() {
        batch = new SpriteBatch();

        img = new Texture("asteroids.png");
        shipImg = new TextureRegion(img, 96, 128, 48, 32);
        movingShip = new TextureRegion(img, 144, 128, 48, 32);
        bulletImg = new TextureRegion(img, 224, 144, 16, 16);
        asteroidImg = new TextureRegion(img, 0, 0, 80, 80);

        shipSprite = new Sprite(shipImg);
        movingShipSprite = new Sprite(movingShip);
        bulletSprite = new Sprite(bulletImg);
        asteroidSprite = new Sprite(asteroidImg);

        soundManager = new SoundManager();
        ship = new Ship(400, 400, shipSprite, movingShipSprite);
        shipManager = new ShipManager(batch, ship);
        bulletManager = new BulletManager(batch, soundManager, shipManager, bulletSprite);
        asteroidsManager = new AsteroidsManager(batch, asteroidSprite, bulletManager);
        Player player = new Player();
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ScreenUtils.clear(0, 0, 0, 0);
        bulletManager.update();
        shipManager.update();
        asteroidsManager.update();
        soundManager.update();
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }
}