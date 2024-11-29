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
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

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

    float currentShipRotation;

    SoundManager soundManager;
    AsteroidsManager asteroidsManager;

    private Array<Bullet> bullets;
    private float shootCooldown = 0;
    private static final float SHOOT_DELAY = 0.2f;

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
        asteroidsManager = new AsteroidsManager(batch, asteroidSprite);

        ship = new Ship(400, 400);
        this.currentShipRotation = 90; // Start game with ship facing up
        shipSprite.rotate(90);
        movingShipSprite.rotate(90);

        bullets = new Array<>();
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ScreenUtils.clear(0, 0, 0, 0);
        shipMovement();
        bulletMovement();
        asteroidsManager.update();
        soundManager.update();
        shipSprite.setPosition(ship.getX(), ship.getY());
        batch.begin();
        shipSprite.draw(batch);
        batch.end();
    }

    private void shipMovement() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        float rotationAmount = (300 * deltaTime);

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            batch.begin();
            shipSprite.rotate(rotationAmount);
            movingShipSprite.rotate(rotationAmount);
            currentShipRotation += rotationAmount;
            batch.end();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            batch.begin();
            shipSprite.rotate(-rotationAmount);
            movingShipSprite.rotate(-rotationAmount);
            currentShipRotation -= rotationAmount;
            batch.end();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            float baseAcceleration = 300f;
            float accelerationX = baseAcceleration * MathUtils.cosDeg(currentShipRotation);
            float accelerationY = baseAcceleration * MathUtils.sinDeg(currentShipRotation);

            ship.setSpeedX(ship.getSpeedX() + accelerationX * deltaTime);
            ship.setSpeedY(ship.getSpeedY() + accelerationY * deltaTime);
        }
        ship.slowDown(Gdx.graphics.getDeltaTime());
        ship.applySpeed(Gdx.graphics.getDeltaTime());
    }

    private void bulletMovement() {
        shootCooldown += Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && shootCooldown >= SHOOT_DELAY) {
            shootBullet();
            shootCooldown = 0;
        }

        for (int i = bullets.size - 1; i >= 0; i--) {
            Bullet bullet = bullets.get(i);
            bullet.applySpeed(Gdx.graphics.getDeltaTime());
            bullet.update(Gdx.graphics.getDeltaTime());

            if (bullet.isDead() || bullet.isOffScreen()) {
                bullets.removeIndex(i);
                continue;
            }

            batch.begin();
            batch.draw(bulletSprite, bullet.getX(), bullet.getY());
            batch.end();
        }
    }

    private void shootBullet() {
        float bulletX = ship.getX() + shipSprite.getWidth() / 2;
        float bulletY = ship.getY() + shipSprite.getHeight() / 2;
        Bullet newBullet = new Bullet(bulletX, bulletY, currentShipRotation, ship.getSpeedX(), ship.getSpeedY(), bulletSprite);
        bullets.add(newBullet);
        soundManager.playShootingSound();
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }
}