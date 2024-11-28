package com.mygdx.asteroids;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class AsteroidsManager {

    private int maxAsteroids = 3;
    private Array<Asteroid> asteroids;
    private Sprite asteroidSprite;
    private SpriteBatch batch;

    public AsteroidsManager(SpriteBatch batch, Sprite asteroidSprite) {
        this.asteroidSprite = asteroidSprite;
        this.batch = batch;
        this.asteroids = new Array<>();
    }

    public void update() {
        asteroidSpawner();
        asteroidHandler();
        System.out.println(asteroids.size);
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
