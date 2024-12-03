package com.mygdx.asteroids;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;

abstract public class Entities {
    private float xPos;
    private float yPos;
    private float speedX;
    private float speedY;
    private boolean dead;
    private Sprite sprite;

    public Entities(float x, float y, Sprite sprite) {
        this.xPos = x;
        this.yPos = y;
        this.dead = false;
        this.speedX = 0;
        this.speedY = 0;
        this.sprite = sprite;
    }

    public float getSpeedX() {
        return speedX;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public boolean isOffScreen() {
        return (getX() + sprite.getWidth() < 0 || getX() > Gdx.graphics.getWidth() ||
                getY() + sprite.getHeight() < 0 || getY() > Gdx.graphics.getHeight());
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public float getX() {
        return xPos;
    }

    public void setX(float x) {
        this.xPos = x;
    }

    public void setY(float y) {
        this.yPos = y;
    }

    public float getY() {
        return yPos;
    }

}
