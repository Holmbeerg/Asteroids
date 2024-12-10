package com.mygdx.asteroids.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.mygdx.asteroids.models.Asteroid;

public class AsteroidsManager {

    private int maxAsteroids = 4;
    private Array<Asteroid> asteroids;
    private final Sprite asteroidSprite;
    private SpriteBatch batch;

    public AsteroidsManager(SpriteBatch batch, Sprite asteroidSprite) {
        this.asteroidSprite = asteroidSprite;
        this.batch = batch;
        this.asteroids = new Array<>();
    }

    public void update() {
        asteroidSpawner();
        asteroidHandler();
    }

    public Array<Asteroid> getAsteroids() {
        return asteroids;
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

        float buffer = 50f;

        switch (border) {
            case 1: // Top border
                x = MathUtils.random(0, screenWidth);
                y = screenHeight + buffer;
                asteroidRotation = MathUtils.random(210, 330);
                break;

            case 2: // Bottom border
                x = MathUtils.random(0, screenWidth);
                y = -buffer;
                asteroidRotation = MathUtils.random(30, 150);
                break;

            case 3: // Left border
                x = -buffer;
                y = MathUtils.random(0, screenHeight);
                asteroidRotation = MathUtils.random(-60, 60);
                break;

            case 4: // Right border
                x = screenWidth + buffer;
                y = MathUtils.random(0, screenHeight);
                asteroidRotation = MathUtils.random(120, 240);
                break;
        }
        Asteroid newAsteroid = new Asteroid(x, y, asteroidRotation, asteroidSprite, buffer);
        asteroids.add(newAsteroid);
    }

    public void destroyAsteroid(Asteroid asteroid) {
        asteroids.removeValue(asteroid, true);
    }

    private void asteroidHandler() {
        if (asteroids.size < maxAsteroids) {
            asteroidSpawner();
        }

        for (int i = asteroids.size - 1; i >= 0; i--) {
            Asteroid asteroid = asteroids.get(i);
            asteroid.applySpeed(Gdx.graphics.getDeltaTime());

            if (asteroid.isDead() || asteroid.isOffScreen()) {
                asteroids.removeIndex(i);
                continue;
            }
            batch.begin();
            batch.draw(asteroidSprite, asteroid.getX(), asteroid.getY());
            batch.end();
        }
    }
}
