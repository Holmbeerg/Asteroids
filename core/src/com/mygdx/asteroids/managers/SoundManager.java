package com.mygdx.asteroids.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.mygdx.asteroids.models.Player;

public class SoundManager {

    private final Sound thrusterSound;
    private final Sound mainSound;
    private final Sound shootingSound;
    private final Sound asteroidSound;
    private long thrusterSoundId = -1;
    private float mainSoundTimer = 0f;

    private static final float MAIN_SOUND_INTERVAL = 1.0f;

    Player player;

    public SoundManager(Player player) {
        this.mainSound = Gdx.audio.newSound(Gdx.files.internal("audio/beat1.ogg"));
        this.shootingSound = Gdx.audio.newSound(Gdx.files.internal("audio/fire.mp3"));
        this.thrusterSound = Gdx.audio.newSound(Gdx.files.internal("audio/thrust.ogg"));
        this.asteroidSound = Gdx.audio.newSound(Gdx.files.internal("audio/bangLarge.ogg"));
        this.player = player;
    }

    public void playShootingSound() {
        shootingSound.play(0.5f);
    }

    public void playAsteroidSound() {
        asteroidSound.play(0.5f);
    }

    public void update() {
        playMainAudio();

        if (!player.isDead()) {
            handleThrusterAudio();
        } else {
            thrusterSound.stop(thrusterSoundId);
            thrusterSoundId = -1;
        }
    }

    private void handleThrusterAudio() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            if (thrusterSoundId != -1) {
                thrusterSound.stop(thrusterSoundId);
            }
            thrusterSoundId = thrusterSound.play(0.5f);
            thrusterSound.setLooping(thrusterSoundId, true);
        }

        if (!Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if (thrusterSoundId != -1) {
                thrusterSound.stop(thrusterSoundId);
                thrusterSoundId = -1;
            }
        }
    }

    private void playMainAudio() {
        mainSoundTimer += Gdx.graphics.getDeltaTime();
        if (mainSoundTimer >= MAIN_SOUND_INTERVAL) {
            mainSound.play(0.5f);
            mainSoundTimer = 0;
        }
    }
}

