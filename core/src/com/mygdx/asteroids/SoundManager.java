package com.mygdx.asteroids;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {

    private final Sound thrusterSound;
    private final Sound mainSound;
    private final Sound shootingSound;
    private long thrusterSoundId = -1;
    private float mainSoundTimer = 0f;

    private static final float MAIN_SOUND_INTERVAL = 1.0f;

    public SoundManager() {
        this.mainSound = Gdx.audio.newSound(Gdx.files.internal("beat1.ogg"));
        this.shootingSound = Gdx.audio.newSound(Gdx.files.internal("fire.mp3"));
        this.thrusterSound = Gdx.audio.newSound(Gdx.files.internal("thrust.ogg"));
    }

    public void playShootingSound() {
        shootingSound.play(0.5f);
    }

    public void update() {
        playMainAudio();
        handleThrusterAudio();
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

