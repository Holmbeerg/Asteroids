package com.mygdx.asteroids;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;

public class Application extends Game { // Game vs ApplicationAdapter? https://www.echalk.co.uk/amusements/Games/asteroidsClassic/ateroids.html
    SpriteBatch batch; // draws to screen, räcker med en
    Texture img;
    TextureRegion shipImg; //Takes a piece from Texture, mer effektivt bara ladda Texture en gång.
    TextureRegion movingShip;
    Ship ship;
    Sprite shipSprite;
    float rotation; // Håll koll på rotation av skeppet

    @Override
    public void create() {
        batch = new SpriteBatch();
        img = new Texture("asteroids.png"); // https://www.youtube.com/watch?v=ONe91ro51cQ
        shipImg = new TextureRegion(img, 96, 128, 48, 32); // högst upp är y = 0, 80+48=128 från sheeten, cutting guide 2x sen / 2
        movingShip = new TextureRegion(img, 144, 128, 48, 32);
        ship = new Ship(300, 300);
        shipSprite = new Sprite(shipImg);
        this.rotation = 90;
        shipSprite.rotate(90);// Kolla mer på hur man ska använda detta (sprites, TextureRegion etc), GDX Texture Packer

    }

    @Override
    public void render() { // render() anropas 60 gånger i sekunden
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) { // Roterar skeppet 45 grader
            batch.begin();
            shipSprite.rotate(5);
            rotation += 5;
            //System.out.println(rotation);
            batch.end();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            batch.begin();
            shipSprite.rotate(-5);
            //System.out.println(rotation);
            rotation -= 5;
            batch.end();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) { // Lista ut hur räkna ut acceleration mm
            ship.setSpeedX((float) ((ship.getSpeedX()) + 0.1 * MathUtils.cosDeg(rotation)));
            ship.setSpeedY((float) ((ship.getSpeedY()) + 0.1 * MathUtils.sinDeg(rotation))); //Trigonometri för att accelerera, pls work (kolla mer hur det fungerar)
            System.out.println(ship.getSpeedY());
            System.out.println(ship.getSpeedX());
            //System.out.println(rotation);
        }

        ship.applySpeed();
        ship.slowDown();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ScreenUtils.clear(0, 0, 0, 0);
        batch.begin();
        // batch.draw(shipImg, 0, 0);
        shipSprite.draw(batch);
        shipSprite.setPosition(ship.getX(), ship.getY());
        batch.end();
        float delta = Gdx.graphics.getDeltaTime();
        float speed = 10;
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }
}
