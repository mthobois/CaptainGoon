package com.mygdx.game.ammo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Missile  extends Ammunition{
    public Missile(int x, int y){
        super(x, y, 15 , 30 , 10, 10);
        this.setSprite(new Texture(Gdx.files.internal(SPRITE_AMMO_DIR + "wship-4.png")));
    }
}
