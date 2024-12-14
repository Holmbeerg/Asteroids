package com.mygdx.asteroids.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.asteroids.Application;
import com.mygdx.asteroids.managers.*;
import com.mygdx.asteroids.models.Player;
import com.mygdx.asteroids.models.Ship;

public class GameScreen implements Screen {
    private Application game;
    private Texture img;
    TextureRegion shipImg;
    TextureRegion movingShip;
    TextureRegion bulletImg;
    TextureRegion largeAsteroidImg;
    TextureRegion smallAsteroidImg;

    Ship ship;
    Sprite shipSprite;
    Sprite movingShipSprite;
    Sprite bulletSprite;
    Sprite largeAsteroidSprite;
    Sprite mediumAsteroidSprite;

    private SoundManager soundManager;
    private AsteroidsManager asteroidsManager;
    private ShipManager shipManager;
    private BulletManager bulletManager;
    private CollisionManager collisionManager;
    private Player player;

    public GameScreen(final Application game) {
        this.game = game;
        loadAssets();
        ship = new Ship(400, 400, shipSprite, movingShipSprite);
        player = new Player();
        soundManager = new SoundManager(player);
        shipManager = new ShipManager(game.batch, ship);
        bulletManager = new BulletManager(game.batch, soundManager, shipManager, bulletSprite);
        asteroidsManager = new AsteroidsManager(game.batch, largeAsteroidSprite, mediumAsteroidSprite);
        collisionManager = new CollisionManager(asteroidsManager, bulletManager, player, shipManager, soundManager);
    }

    private void loadAssets() {
        img = new Texture("cuttings/asteroids.png");
        shipImg = new TextureRegion(img, 96, 128, 48, 32);
        movingShip = new TextureRegion(img, 144, 128, 48, 32);
        bulletImg = new TextureRegion(img, 224, 144, 16, 16);
        largeAsteroidImg = new TextureRegion(img, 0, 0, 80, 80);
        smallAsteroidImg = new TextureRegion(img, 0, 80, 48, 48);

        shipSprite = new Sprite(shipImg);
        movingShipSprite = new Sprite(movingShip);
        bulletSprite = new Sprite(bulletImg);
        largeAsteroidSprite = new Sprite(largeAsteroidImg);
        mediumAsteroidSprite = new Sprite(smallAsteroidImg);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        draw();
        update();
    }

    private void update() {
        if (player.isDead()) {
            game.setScreen(new GameOverScreen(game, player));
            dispose();
        }

        if (!player.isDead()) {
            bulletManager.update();
            shipManager.update();
        }
        asteroidsManager.update();
        soundManager.update();
        collisionManager.update();
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        game.batch.begin();
        game.font.draw(game.batch, "Lives left: " + player.getLives(), 20, Gdx.graphics.getHeight() - 20); // not using viewports rn
        game.font.draw(game.batch, "Score: " + player.getScore(), Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 20);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        img.dispose();
    }
}
