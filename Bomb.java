package com.hspedu.tankGame01;

public class Bomb {
    int x, y; // coordinate
    int life = 9; // lifecycle
    boolean isAlive = true;

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void lifeDown() {
        if (life > 0) {
            life--;
        } else {
            isAlive = false;
        }
    }
}
