package com.mygdx.asteroids.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.asteroids.models.Ship;

public class ShipManager {
    private Ship ship;
    private SpriteBatch batch;
    private boolean isThrusting;
    private float respawnDelay = 2.0f;
    private float respawnTime = 0.0f;


    public ShipManager(SpriteBatch batch, Ship ship) {
        this.ship = ship;
        ship.getSprite().rotate(90);
        ship.getMovingShipSprite().setRotation(ship.getSprite().getRotation());
        this.batch = batch;
    }

    public void update() {
        if (ship.isDead()) {
            respawnTime += Gdx.graphics.getDeltaTime();
            resetShip();
            if (respawnTime >= respawnDelay) {
                respawnTime = 0.0f;
                ship.setDead(false);
            }
        } else {
            shipMovement();
            drawShip();
            handleScreenWrap();
            ship.updateBoundingRectangle();
            ship.getMovingShipSprite().setRotation(ship.getSprite().getRotation());
        }
    }

    private void handleScreenWrap() {
        float buffer = 50f;
        float screenHeight = Gdx.graphics.getHeight();
        float screenWidth = Gdx.graphics.getWidth();

        if (ship.getY() > screenHeight + buffer) {
            ship.setY(0 - ship.getSprite().getHeight() - buffer);
        }

        if (ship.getY() + ship.getSprite().getHeight() < -buffer) {
            ship.setY(screenHeight + buffer);
        }

        if (ship.getX() > screenWidth + buffer) {
            ship.setX(0 - ship.getSprite().getWidth() - buffer);
        }

        if (ship.getX() + ship.getSprite().getWidth() < -buffer) {
            ship.setX(screenWidth + buffer);
        }
    }

    public Ship getShip() {
        return this.ship;
    }

    private void drawShip() {
        ship.getSprite().setPosition(ship.getX(), ship.getY());
        batch.begin();
        ship.getSprite().draw(batch);
        batch.end();
    }

    private void shipMovement() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        float rotationAmount = (320 * deltaTime);

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            batch.begin();
            ship.setSpriteRotation(rotationAmount);
            batch.end();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            batch.begin();
            ship.setSpriteRotation(-rotationAmount);
            batch.end();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if (!isThrusting) {
                ship.setSpriteToThrusting();
                isThrusting = true;
            }

            float baseAcceleration = 300f;
            float accelerationX = baseAcceleration * MathUtils.cosDeg(ship.getSprite().getRotation());
            float accelerationY = baseAcceleration * MathUtils.sinDeg(ship.getSprite().getRotation());

            ship.setSpeedX(ship.getSpeedX() + accelerationX * deltaTime);
            ship.setSpeedY(ship.getSpeedY() + accelerationY * deltaTime);
        } else {
            if (isThrusting) {
                ship.setSpriteToNormal();
                isThrusting = false;
            }
        }

        ship.slowDown(Gdx.graphics.getDeltaTime());
        ship.applySpeed(Gdx.graphics.getDeltaTime());
    }

    public void resetShip() {
        ship.setSpriteRotation(90);
        ship.setSpeedX(0.0f);
        ship.setSpeedY(0.0f);
        ship.setX(400);
        ship.setY(400);
        ship.updateBoundingRectangle();
    }
}
