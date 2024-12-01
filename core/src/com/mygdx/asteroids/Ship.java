package com.mygdx.asteroids;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Ship extends Entities {
    final float DECELERATE_CONSTANT = 0.992F;
    private final Sprite movingShipSprite;
    private final Rectangle boundingRectangle;

    public Ship(int xPos, int yPos, Sprite shipSprite, Sprite movingShipSprite) {
        super(xPos, yPos, shipSprite);
        this.movingShipSprite = movingShipSprite;
        this.boundingRectangle = new Rectangle(xPos, yPos, shipSprite.getWidth(), shipSprite.getHeight());
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

    public Rectangle getBoundingRectangle() {
        return this.boundingRectangle;
    }

    public void updateBoundingRectangle() {
        boundingRectangle.setPosition(getX(), getY());
    }

    public Sprite getMovingShipSprite() {
        return this.movingShipSprite;
    }
}


