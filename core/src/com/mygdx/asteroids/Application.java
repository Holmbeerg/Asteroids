package com.mygdx.asteroids;

/*
# TODO: Fix bullet positioning on sprite
# TODO: Collision detection
# TODO: Wrap around screen with ship
# TODO: Add sounds, https://classicgaming.cc/classics/asteroids/sounds
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

    Sound mainSound;
    Sound shootingSound;
    Sound thrusterSound;

    float currentShipRotation;
    private long thrusterSoundId = -1;
    private float mainSoundTimer = 0;

    private Array<Bullet> bullets;
    private float shootCooldown = 0;
    private static final float SHOOT_DELAY = 0.2f;

    private int maxAsteroids = 3;
    private Array<Asteroid> asteroids;

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

        mainSound = Gdx.audio.newSound(Gdx.files.internal("beat1.ogg"));
        shootingSound = Gdx.audio.newSound(Gdx.files.internal("fire.mp3"));
        thrusterSound = Gdx.audio.newSound(Gdx.files.internal("thrust.ogg"));

        ship = new Ship(400, 400);
        this.currentShipRotation = 90; // Start game with ship facing up
        shipSprite.rotate(90);
        movingShipSprite.rotate(90);

        bullets = new Array<>();
        asteroids = new Array<>();
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ScreenUtils.clear(0, 0, 0, 0);
        shipMovement();
        bulletMovement();
        asteroidHandler();
        audioHandler();
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

    private void audioHandler() {
        playMainAudio();
        handleThrusterAudio();
    }

    private void handleThrusterAudio() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            if (thrusterSoundId != -1) {
                thrusterSound.stop(thrusterSoundId);
            }
            thrusterSoundId = thrusterSound.play(0.5f);
            thrusterSound.setLooping(thrusterSoundId, true);
        }

        if (!Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if (thrusterSoundId != -1) {
                thrusterSound.stop(thrusterSoundId);
                thrusterSoundId = -1;
            }
        }
    }

    private void playMainAudio() {
        mainSoundTimer += Gdx.graphics.getDeltaTime();
        if (mainSoundTimer >= 1) {
            mainSound.play(0.5f);
            mainSoundTimer = 0;
        }
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

            if (bullet.isDead() || isOffScreen(bullet)) {
                bullets.removeIndex(i);
                continue;
            }
            batch.begin();
            batch.draw(bulletSprite, bullet.getX(), bullet.getY());
            batch.end();
        }
    }

    private void asteroidSpawner() {
        if (asteroids.size >= maxAsteroids) {
            return;
        }

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        int border = MathUtils.random(1, 4); // 1 = Top, 2 = Bottom, 3 = Left, 4 = Right

        float x = 0;
        float y = 0;
        float asteroidRotation = 0;

        switch (border) {
            case 1: // Top border
                x = MathUtils.random(0, screenWidth); // Random x position along the top
                y = screenHeight;                     // Top edge (y = max height)
                asteroidRotation = MathUtils.random(90, 270); // Moves downward
                break;

            case 2: // Bottom border
                x = MathUtils.random(0, screenWidth); // Random x position along the bottom
                y = 0;                                // Bottom edge (y = 0)
                asteroidRotation = MathUtils.random(-90, 90); // Moves upward
                break;

            case 3: // Left border
                x = 0;                                // Left edge (x = 0)
                y = MathUtils.random(0, screenHeight); // Random y position along the left
                asteroidRotation = MathUtils.random(-45, 45); // Moves rightward
                break;

            case 4: // Right border
                x = screenWidth;                      // Right edge (x = max width)
                y = MathUtils.random(0, screenHeight); // Random y position along the right
                asteroidRotation = MathUtils.random(135, 225); // Moves leftward
                break;
        }
        Asteroid newAsteroid = new Asteroid(x, y, asteroidRotation);
        asteroids.add(newAsteroid);
    }

    private void asteroidHandler() {
        if (asteroids.size < maxAsteroids) {
            asteroidSpawner();
        }

        for (int i = asteroids.size - 1; i >= 0; i--) {
            Asteroid asteroid = asteroids.get(i);
            asteroid.applySpeed(Gdx.graphics.getDeltaTime());

            if (asteroid.isDead() || isOffScreen(asteroid)) {
                asteroids.removeIndex(i);
                continue;
            }
            batch.begin();
            batch.draw(asteroidSprite, asteroid.getX(), asteroid.getY());
            batch.end();
        }
    }

    private void shootBullet() {
        float bulletX = ship.getX() + shipSprite.getWidth() / 2;
        float bulletY = ship.getY() + shipSprite.getHeight() / 2;
        Bullet newBullet = new Bullet(bulletX, bulletY, currentShipRotation, ship.getSpeedX(), ship.getSpeedY());
        bullets.add(newBullet);
        shootingSound.play(0.5f);
    }

    private boolean isOffScreen(Entities entity) {
        return entity.getX() < 0 || entity.getX() > Gdx.graphics.getWidth() ||
                entity.getY() < 0 || entity.getY() > Gdx.graphics.getHeight();
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
        thrusterSound.dispose();
        mainSound.dispose();
        shootingSound.dispose();
    }

    private void levelUp() {
        this.maxAsteroids += 2;
    }
}