package com.mygdx.asteroids;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

// Inheritance = extending and inheriting from abstract base class Entities.
// polymorphism = many different forms. For example can override applySpeed and so it takes a different form than base class.
public class Ship extends Entities {
    final float DECELERATE_CONSTANT = 0.992F;

    public Ship(int xPos, int yPos) {
        super(xPos, yPos);
    }
    // Accelerate

    // big retard (retardation)
    public void slowDown() {
        this.setSpeedX((float) (this.getSpeedX() * DECELERATE_CONSTANT));
        this.setSpeedY((float) (this.getSpeedY() * DECELERATE_CONSTANT));
    }

    @Override
    public void applySpeed() {
        this.setX(this.getX() + this.getSpeedX()); // new position = old position + velocity
        this.setY(this.getY() + this.getSpeedY()); //Gdx.graphics.getDeltaTime(), use to get same results regardless of fps?
    }
}


