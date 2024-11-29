package com.mygdx.asteroids;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Ship extends Entities {
    final float DECELERATE_CONSTANT = 0.992F;
    final Sprite movingShipSprite;

    public Ship(int xPos, int yPos, Sprite shipSprite, Sprite movingShipSprite) {
        super(xPos, yPos, shipSprite);
        this.movingShipSprite = movingShipSprite;
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

    public Sprite getMovingShipSprite() {
        return this.movingShipSprite;
    }
}


