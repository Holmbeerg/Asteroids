package com.mygdx.asteroids;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {
    private Application game;
    private Texture img;
    TextureRegion shipImg;
    TextureRegion movingShip;
    TextureRegion bulletImg;
    TextureRegion asteroidImg;

    Ship ship;
    Sprite shipSprite;
    Sprite movingShipSprite;
    Sprite bulletSprite;
    Sprite asteroidSprite;

    private SoundManager soundManager;
    private AsteroidsManager asteroidsManager;
    private ShipManager shipManager;
    private BulletManager bulletManager;
    private Player player;

    public GameScreen(final Application game) {
        this.game = game;
        img = new Texture("cuttings/asteroids.png");
        shipImg = new TextureRegion(img, 96, 128, 48, 32);
        movingShip = new TextureRegion(img, 144, 128, 48, 32);
        bulletImg = new TextureRegion(img, 224, 144, 16, 16);
        asteroidImg = new TextureRegion(img, 0, 0, 80, 80);

        shipSprite = new Sprite(shipImg);
        movingShipSprite = new Sprite(movingShip);
        bulletSprite = new Sprite(bulletImg);
        asteroidSprite = new Sprite(asteroidImg);
        ship = new Ship(400, 400, shipSprite, movingShipSprite);
        player = new Player();
        soundManager = new SoundManager(player);
        shipManager = new ShipManager(game.batch, ship);
        bulletManager = new BulletManager(game.batch, soundManager, shipManager, bulletSprite);
        asteroidsManager = new AsteroidsManager(game.batch, asteroidSprite, bulletManager, shipManager, player);
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
