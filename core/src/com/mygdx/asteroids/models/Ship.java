package com.mygdx.asteroids.models;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Ship extends Entities {
    final float DECELERATE_CONSTANT = 0.992F;
    private final Sprite normalSprite;
    private final Sprite thrustingSprite;
    private final Rectangle boundingRectangle;


    public Ship(int xPos, int yPos, Sprite shipSprite, Sprite thrustingSprite) {
        super(xPos, yPos, shipSprite);
        this.thrustingSprite = thrustingSprite;
        this.boundingRectangle = new Rectangle(xPos, yPos, shipSprite.getWidth() - 10, shipSprite.getHeight() - 10);
        this.normalSprite = shipSprite;

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

    public void setSpriteToThrusting() {
        super.setSprite(thrustingSprite);
    }

    public void setSpriteToNormal() {
        super.setSprite(normalSprite);
    }

    public void setSpriteRotation(float rotation) {
        this.normalSprite.rotate(rotation);
        this.thrustingSprite.rotate(rotation);
    }

    public Rectangle getBoundingRectangle() {
        return this.boundingRectangle;
    }

    public void updateBoundingRectangle() {
        boundingRectangle.setPosition(getX(), getY());
    }

    public Sprite getMovingShipSprite() {
        return this.thrustingSprite;
    }
}


