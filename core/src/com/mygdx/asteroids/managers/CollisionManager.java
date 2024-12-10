package com.mygdx.asteroids.managers;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.asteroids.models.Asteroid;
import com.mygdx.asteroids.models.Bullet;
import com.mygdx.asteroids.models.Player;

public class CollisionManager {
    private AsteroidsManager asteroidManager;
    private BulletManager bulletManager;
    private Player player;
    private ShipManager shipManager;

    public CollisionManager(AsteroidsManager asteroidManager, BulletManager bulletManager, Player player, ShipManager shipManager) {
        this.asteroidManager = asteroidManager;
        this.bulletManager = bulletManager;
        this.player = player;
        this.shipManager = shipManager;
    }

    public void update() {
        if (!player.isDead()) {
            collisionHandler();
        }
    }

    private void collisionHandler() {
        for (Asteroid asteroid : asteroidManager.getAsteroids()) {
            checkCollisionWithBullets(asteroid);
            checkCollisionWithPlayer(asteroid);
        }
    }

    private void checkCollisionWithBullets(Asteroid asteroid) { // Rectangles for now, can try switching to polygons later
        Rectangle asteroidRect = asteroid.getBoundingRectangle();

        for (Bullet bullet : bulletManager.getBullets()) {
            Rectangle bulletRect = bullet.getBoundingRectangle();

            if (asteroidRect.contains(bulletRect)) {
                asteroidManager.destroyAsteroid(asteroid);
                bulletManager.removeBullet(bullet);
                player.addScore(20); // #TODO different sizes of asteroids exist, give different score depending on size of asteroid
            }
        }
    }

    private void checkCollisionWithPlayer(Asteroid asteroid) {
        Rectangle asteroidRect = asteroid.getBoundingRectangle();

        if (asteroidRect.contains(shipManager.getShip().getBoundingRectangle())) {
            asteroidManager.destroyAsteroid(asteroid);
            player.removeLife();
        }
    }
}
