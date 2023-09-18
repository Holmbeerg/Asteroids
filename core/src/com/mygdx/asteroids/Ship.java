package com.mygdx.asteroids;

import com.badlogic.gdx.math.MathUtils;

public class Ship {
    private float xPos;
    private float yPos;
    final float MAX_SPEED;
    private float speedX;
    private float speedY;
    private boolean dead;

    public Ship(int x, int y) {
        this.xPos = x;
        this.yPos = y;
        this.dead = false;
        this.MAX_SPEED = 200;
        this.speedX = 0;
        this.speedY = 0; // default är 0 (tror jag), men lite tydligare kanske om skriver ut
    }

    public float getSpeedX() {
        return speedX;
    }

    // Accelerera
    public void applySpeed() {
        this.setX(this.getX() + this.getSpeedX());
        this.setY(this.getY() + this.getSpeedY());
    }

    // big retard (retardation)
    public void slowDown() {
        this.setSpeedX((float) (this.getSpeedX() * 0.992));
        this.setSpeedY((float) (this.getSpeedY() * 0.992));
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
