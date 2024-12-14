package com.mygdx.asteroids.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.asteroids.Application;
import com.mygdx.asteroids.models.Player;

public class GameOverScreen implements Screen {
    Application game;
    Player player;
    Stage stage; // https://www.youtube.com/watch?v=YbeDMmajH9s
    Table table;
    TextButton playAgainButton;

    public GameOverScreen(final Application game, Player player) {
        this.game = game;
        this.player = player;
        this.stage = new Stage();
        Skin skin = new Skin(Gdx.files.internal("metalui/metal-ui.json")); // som css
        Gdx.input.setInputProcessor(stage);

        this.table = new Table(); // https://libgdx.com/wiki/graphics/2d/scene2d/table
        table.setFillParent(true);
        stage.addActor(table);

        Label gameOverLabel = new Label("GAME OVER! FINAL SCORE: " + player.getScore(), skin, "white");
        gameOverLabel.setColor(Color.RED);

        table.add(gameOverLabel);
        table.row();

        this.playAgainButton = new TextButton("Play Again", skin);
        table.add(playAgainButton).padTop(20f);

        this.playAgainButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Changing to GameScreen");
                game.setScreen(new GameScreen(game));
                dispose();
            }
        });
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        stage.act();
        stage.draw();
    }

    public void draw() {
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
        stage.dispose();
    }
}
