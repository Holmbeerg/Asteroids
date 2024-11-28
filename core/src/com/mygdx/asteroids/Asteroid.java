package com.mygdx.asteroids;

import com.badlogic.gdx.math.MathUtils;

public class Asteroid extends Entities {

    private static final float ASTEROID_SPEED = 1.5f;

    public Asteroid(float x, float y, float rotation) {
        super(x, y);
        float speedX = ASTEROID_SPEED * MathUtils.cosDeg(rotation);
        float speedY = ASTEROID_SPEED * MathUtils.sinDeg(rotation);
        setSpeedX(speedX);
        setSpeedY(speedY);
    }

    public void applySpeed(float deltaTime) {
        float scaledDeltaTime = deltaTime * 100f;
        setX(getX() + getSpeedX() * scaledDeltaTime);
        setY(getY() + getSpeedY() * scaledDeltaTime);
    }
}
