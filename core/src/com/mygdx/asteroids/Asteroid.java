package com.mygdx.asteroids;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

public class Asteroid extends Entities {

    private static final float ASTEROID_SPEED = 1.5f;
    private final float spawning_buffer;
    private Rectangle boundingRectangle;

    public Asteroid(float x, float y, float rotation, Sprite asteroidSprite, float buffer) {
        super(x, y, asteroidSprite);
        this.boundingRectangle = new Rectangle(x, y, asteroidSprite.getWidth(), asteroidSprite.getHeight());
        float speedX = ASTEROID_SPEED * MathUtils.cosDeg(rotation);
        float speedY = ASTEROID_SPEED * MathUtils.sinDeg(rotation);
        setSpeedX(speedX);
        setSpeedY(speedY);
        this.spawning_buffer = buffer;
    }

    public void applySpeed(float deltaTime) {
        float scaledDeltaTime = deltaTime * 100f;
        setX(getX() + getSpeedX() * scaledDeltaTime);
        setY(getY() + getSpeedY() * scaledDeltaTime);
        updateBoundingRectangle();
    }

    @Override
    public boolean isOffScreen() {
        return (getX() + super.getSprite().getWidth() + spawning_buffer < 0 || getX() - spawning_buffer > Gdx.graphics.getWidth() ||
                getY() + super.getSprite().getHeight() + spawning_buffer < 0 || getY() - spawning_buffer > Gdx.graphics.getHeight());
    }

    public void updateBoundingRectangle() {
        boundingRectangle.setPosition(getX(), getY());
    }

    public Rectangle getBoundingRectangle() {
        return boundingRectangle;
    }
}
