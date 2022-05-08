package com.mygdx.game.ships;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.ammo.Missile;

public class Alien extends Vaisseau{

    public Alien(int x , int y, int speed){
            super(x, y,100, 100, 100);
            this.setSpeed(speed);
        this.setSprite(new Texture(Gdx.files.internal(SPRITE_SHIP_DIR + "roundysh_small.png")));
    }

    @Override
    public void move(){
        if(getX() >= 0 && getX() < Gdx.graphics.getWidth() / 2){
            if(getX() + getWidth() > Gdx.graphics.getWidth() / 2 || getX() < 0) setSpeed(-getSpeed());
        }
        else{
            if(getX() + getWidth() < Gdx.graphics.getWidth() / 2 || getX() + getWidth() > Gdx.graphics.getWidth()) setSpeed(-getSpeed());
        }
        setX(getX() + getSpeed());
    }

    @Override
    public void generateBullets() {
        this.getAmmos().add(new Missile(this.getX() + this.getWidth() / 2, this.getY() - this.getHeight() / 2));
    }
}
