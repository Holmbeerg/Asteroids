package com.mygdx.asteroids;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Bullet extends Entities {

    private static final float DEFAULT_BULLET_SPEED = 4f;
    private float lifeTime;
    private static final float BULLET_LIFETIME = 1.5f;
    private Rectangle boundingRectangle;

    public Bullet(float x, float y, float rotation, float shipSpeedX, float shipSpeedY, Sprite sprite) {
        super(x, y, sprite);
        this.boundingRectangle = new Rectangle(x, y, sprite.getWidth(), sprite.getHeight());
        float speedFactor = 0.01f;
        float bulletSpeedX = shipSpeedX * speedFactor + DEFAULT_BULLET_SPEED * MathUtils.cosDeg(rotation);
        float bulletSpeedY = shipSpeedY * speedFactor + DEFAULT_BULLET_SPEED * MathUtils.sinDeg(rotation);

        setSpeedX(bulletSpeedX);
        setSpeedY(bulletSpeedY);
    }

    public void update(float deltaTime) {
        updateBoundingRectangle();
        lifeTime += deltaTime;
        if (lifeTime >= BULLET_LIFETIME) {
            setDead(true);
        }
    }

    public void applySpeed(float deltaTime) {
        float scaledDeltaTime = deltaTime * 100f;
        setX(getX() + getSpeedX() * scaledDeltaTime);
        setY(getY() + getSpeedY() * scaledDeltaTime);
    }

    private void updateBoundingRectangle() {
        boundingRectangle.setPosition(getX(), getY());
    }

    public Rectangle getBoundingRectangle() {
        return boundingRectangle;
    }
}


