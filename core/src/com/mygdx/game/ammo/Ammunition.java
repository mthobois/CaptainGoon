package com.mygdx.game.ammo;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;


public class Ammunition {

    private final int x;
    private int y;
    private final int width;
    private final int height;
    private final int speed;
    private final int dmg;
    private final Rectangle hitbox;
    private Texture sprite;

     public float elaspseTime = 0f;

    protected static final String SPRITE_AMMO_DIR = "assets/sprites/ammo/";

    public int getX() {
        return x;
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

    public int getDmg() {
        return dmg;
    }

    public Rectangle getHitbox() {
        return hitbox;
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

    public Ammunition(int x, int y, int width, int height, int speed, int dmg){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.dmg = dmg;
        this.hitbox = new Rectangle(x, y, width, height);
    }

    public void hitboxUpdate(){
        hitbox.setX(x);
        hitbox.setY(y);
    }
}
