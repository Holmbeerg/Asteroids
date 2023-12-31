package com.mygdx.asteroids;

abstract public class Entities {
    private float xPos;
    private float yPos;
    private float speed;
    private float rotation;
    private float speedX;
    private float speedY;
    private float MAX_SPEED;
    private boolean dead;

    public Entities(float x, float y)  {
        this.xPos = x;
        this.yPos = y;
        this.dead = false;
        this.MAX_SPEED = 200;
        this.speedX = 0;
        this.speedY = 0;
    }
    public float getSpeedX() {
        return speedX;
    }

    public void applySpeed() {

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

    public void update() {

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
