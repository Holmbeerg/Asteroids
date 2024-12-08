package com.mygdx.asteroids;

public class Player {
    private int score;
    private int lives;

    public Player() {
        this.score = 0;
        this.lives = 3;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public int getScore() {
        return score;
    }

    public boolean isDead() {
        return lives <= 0;
    }

    public void removeLife() {
        this.lives -= 1;
    }
}

