package com.mygdx.asteroids;

import com.badlogic.gdx.math.MathUtils;

public class Bullet extends Entities {
    private static final float DEFAULT_BULLET_SPEED = 4f;
    private static final float BULLET_LIFETIME = 1.5f; // Seconds before bullet disappears
    private float lifeTime;

    public Bullet(float x, float y, float rotation, float shipSpeedX, float shipSpeedY) {
        super(x, y);
        this.lifeTime = 0;

        float speedFactor = 0.01f;
        float bulletSpeedX = shipSpeedX * speedFactor + DEFAULT_BULLET_SPEED * MathUtils.cosDeg(rotation);
        float bulletSpeedY = shipSpeedY * speedFactor + DEFAULT_BULLET_SPEED * MathUtils.sinDeg(rotation);

        setSpeedX(bulletSpeedX);
        setSpeedY(bulletSpeedY);
    }

    public void applySpeed(float deltaTime) {
        float scaledDeltaTime = deltaTime * 100f;
        setX(getX() + getSpeedX() * scaledDeltaTime);
        setY(getY() + getSpeedY() * scaledDeltaTime);
    }

    public void update(float deltaTime) {
        lifeTime += deltaTime;
        if (lifeTime >= BULLET_LIFETIME) {
            setDead(true);
        }
    }
}
