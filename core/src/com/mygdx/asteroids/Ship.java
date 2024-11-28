package com.mygdx.asteroids;

public class Ship extends Entities {
    final float DECELERATE_CONSTANT = 0.992F;

    public Ship(int xPos, int yPos) {
        super(xPos, yPos);
    }

    public void slowDown(float deltaTime) {
        float decelFactor = (float) Math.pow(DECELERATE_CONSTANT, deltaTime * 60); // http://www.mathwords.com/e/exponential_decay.htm
        setSpeedX(getSpeedX() * decelFactor);
        setSpeedY(getSpeedY() * decelFactor);
    }

    public void applySpeed(float deltaTime) {
        setX(getX() + getSpeedX() * deltaTime);
        setY(getY() + getSpeedY() * deltaTime);
    }
}


