package com.mygdx.game.ammo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Bomb extends Ammunition{
    public Bomb(int x, int y){
        super(x, y, 40 , 70 , 10, 15);
        this.setSprite(new Texture(Gdx.files.internal(SPRITE_AMMO_DIR + "aliendropping_small.png")));
    }
}
