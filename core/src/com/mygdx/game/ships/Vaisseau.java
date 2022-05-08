package com.mygdx.game.ships;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.ammo.Ammunition;

public abstract class Vaisseau {
    private int x;
    private int y;

    private int life;

    private Texture sprite;

    private final int width;
    private final int height;
    private int speed;
    private final Rectangle hitbox;
    private float elaspseTime = 0f;

    protected String SPRITE_SHIP_DIR = "assets/sprites/ships/";

    private final Array<Ammunition> ammos;

    public Array<Ammunition> getAmmos() {
        return ammos;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getSpeed() {
        return speed;
    }
    public void setSpeed(int speed){
        this.speed = speed;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }


    public Texture getSprite() {
        return sprite;
    }

    public void setSprite(Texture sprite) {
        this.sprite = sprite;
    }

    public float getElaspseTime() {
        return elaspseTime;
    }

    public void setElaspseTime(float elaspseTime) {
        this.elaspseTime = elaspseTime;
    }

    public Vaisseau(int x, int y, int width, int height, int life){
        this.x = x;
        this.y = y;
        this.life = life;
        this.width = width;
        this.height = height;
        ammos = new Array<>();
        this.hitbox = new Rectangle(x, y, width, height);
    }

    public void hitboxUpdate(){
        hitbox.setX(x);
        hitbox.setY(y);
    }

    public abstract void move();

    public abstract void generateBullets();
}
