package com.mygdx.game.ships;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.ammo.Laser;

public class Goon extends Vaisseau{

    public Goon(){
        super(20, 20, 100, 130, 100);
        this.setSprite(new Texture(Gdx.files.internal(SPRITE_SHIP_DIR + "blueships1_small.png")));
    }

    @Override
    public void move(){

        if(Gdx.input.getX() - getWidth() / 2 < 0) setX(0);
        else if(Gdx.input.getX() + getWidth() / 2 > Gdx.graphics.getWidth()) setX(Gdx.graphics.getWidth() - getWidth());
        else setX(Gdx.input.getX() - getWidth() / 2);


        if(Gdx.graphics.getHeight() - Gdx.input.getY() - getHeight() / 2 < 0) setY(0);
        else if( Gdx.graphics.getHeight() - Gdx.input.getY() - getHeight() / 2 > Gdx.graphics.getHeight() / 6) setY(Gdx.graphics.getHeight() / 6);
        else setY(Gdx.graphics.getHeight() - Gdx.input.getY() - getHeight() / 2);
    }

    @Override
    public void generateBullets(){
        this.getAmmos().add(new Laser(this.getX() + getWidth()/2, this.getY() + this.getHeight()));
    }
}
