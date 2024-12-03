package com.mygdx.asteroids;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class ShipManager {
    Sprite movingShipSprite;
    Ship ship;
    Batch batch;

    public ShipManager(SpriteBatch batch, Ship ship) {
        this.ship = ship;
        ship.getSprite().rotate(90);
        ship.getMovingShipSprite().setRotation(ship.getSprite().getRotation());
        this.batch = batch;
    }

    public void update() {
        shipMovement();
        drawShip();
        ship.updateBoundingRectangle();
        handleScreenWrap();
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
        float rotationAmount = (300 * deltaTime);

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            batch.begin();
            ship.getSprite().rotate(rotationAmount);
            batch.end();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            batch.begin();
            ship.getSprite().rotate(-rotationAmount);
            batch.end();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            float baseAcceleration = 300f;
            float accelerationX = baseAcceleration * MathUtils.cosDeg(ship.getSprite().getRotation());
            float accelerationY = baseAcceleration * MathUtils.sinDeg(ship.getSprite().getRotation());

            ship.setSpeedX(ship.getSpeedX() + accelerationX * deltaTime);
            ship.setSpeedY(ship.getSpeedY() + accelerationY * deltaTime);
        }
        ship.slowDown(Gdx.graphics.getDeltaTime());
        ship.applySpeed(Gdx.graphics.getDeltaTime());
    }
}
