package com.mygdx.game.ships;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.ammo.Bomb;

public class Boss extends Vaisseau{

    public Boss(int x, int y, int speed){
        super(x, y, 150, 150, 100);
        this.setSpeed(speed);
        this.setSprite(new Texture(Gdx.files.internal(SPRITE_SHIP_DIR + "spco_small.png")));
    }

    @Override
    public void move(){
        if(getX() + getWidth() > Gdx.graphics.getWidth() || getX() < 0) setSpeed(-getSpeed());
        setX(getX() + getSpeed());
    }

    @Override
    public void generateBullets() {
        this.getAmmos().add(new Bomb(this.getX() + this.getWidth() / 2, this.getY() - this.getHeight() / 2));
    }
}
