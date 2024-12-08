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
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

public class Application extends Game {
    private SpriteBatch batch;
    private Texture img;
    TextureRegion shipImg;
    TextureRegion movingShip;
    TextureRegion bulletImg;
    TextureRegion asteroidImg;

    Ship ship;
    Sprite shipSprite;
    Sprite movingShipSprite;
    Sprite bulletSprite;
    Sprite asteroidSprite;

    private SoundManager soundManager;
    private AsteroidsManager asteroidsManager;
    private ShipManager shipManager;
    private BulletManager bulletManager;
    private Player player;


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
        ship = new Ship(400, 400, shipSprite, movingShipSprite);
        player = new Player();
        soundManager = new SoundManager(player);
        shipManager = new ShipManager(batch, ship);
        bulletManager = new BulletManager(batch, soundManager, shipManager, bulletSprite);
        asteroidsManager = new AsteroidsManager(batch, asteroidSprite, bulletManager, shipManager, player);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ScreenUtils.clear(0, 0, 0, 0);

        if (!player.isDead()) {
            bulletManager.update();
            shipManager.update();
        }
        asteroidsManager.update();
        soundManager.update();
    }


    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }
}