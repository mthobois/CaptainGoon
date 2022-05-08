package com.mygdx.game.ammo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Laser extends Ammunition{
    public Laser(int x, int y){
        super(x, y, 10 , 30 , 15, 10);
        this.setSprite(new Texture(Gdx.files.internal(SPRITE_AMMO_DIR + "bullets/glowtube_small.png")));
    }
}
