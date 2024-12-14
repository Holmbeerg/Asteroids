package com.mygdx.asteroids.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.asteroids.models.Bullet;

public class BulletManager {
    private Array<Bullet> bullets;
    private float shootCooldown = 0;
    private static final float SHOOT_DELAY = 0.1f;
    private Batch batch;
    private ShipManager shipManager;
    private SoundManager soundManager;
    private Sprite bulletSprite;

    public BulletManager(SpriteBatch batch, SoundManager soundManager, ShipManager shipManager, Sprite bulletSprite) {
        bullets = new Array<>();
        this.batch = batch;
        this.shipManager = shipManager;
        this.soundManager = soundManager;
        this.bulletSprite = bulletSprite;
    }

    public void update() {
        bulletMovement();
    }

    public void removeBullet(Bullet bullet) {
        bullets.removeValue(bullet, true);
    }

    private void bulletMovement() {
        shootCooldown += Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && shootCooldown >= SHOOT_DELAY && bullets.size < 4) {
            shootBullet();
            shootCooldown = 0;
        }

        for (int i = bullets.size - 1; i >= 0; i--) {
            Bullet bullet = bullets.get(i);
            bullet.applySpeed(Gdx.graphics.getDeltaTime());
            bullet.update(Gdx.graphics.getDeltaTime());

            if (bullet.isDead() || bullet.isOffScreen()) {
                bullets.removeIndex(i);
                continue;
            }

            batch.begin();
            batch.draw(bulletSprite, bullet.getX(), bullet.getY());
            batch.end();
        }
    }

    public Array<Bullet> getBullets() {
        return bullets;
    }

    private void shootBullet() {
        float bulletX = shipManager.getShip().getX() + (shipManager.getShip().getSprite().getWidth() / 2) - (bulletSprite.getWidth() / 2);
        float bulletY = shipManager.getShip().getY() + (bulletSprite.getWidth() / 2);
        Bullet newBullet = new Bullet(bulletX, bulletY, shipManager.getShip().getSprite().getRotation(), shipManager.getShip().getSpeedX(), shipManager.getShip().getSpeedY(), bulletSprite);
        bullets.add(newBullet);
        soundManager.playShootingSound();
    }
}

