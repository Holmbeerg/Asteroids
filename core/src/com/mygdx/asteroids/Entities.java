package com.mygdx.asteroids;

import com.badlogic.gdx.Gdx;

abstract public class Entities {
    private float xPos;
    private float yPos;
    private float speedX;
    private float speedY;
    private boolean dead;

    public Entities(float x, float y) {
        this.xPos = x;
        this.yPos = y;
        this.dead = false;
        this.speedX = 0;
        this.speedY = 0;
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
        return this.getX() < 0 || this.getX() > Gdx.graphics.getWidth() ||
                this.getY() < 0 || this.getY() > Gdx.graphics.getHeight();
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
