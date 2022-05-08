package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class CaptainGoon  extends Game {
    public SpriteBatch batch;
    public BitmapFont font;

    private final Array<Screen> screens = new Array<>();

    public void addScreen(Screen screen){
        this.screens.add(screen);
    }

    public void create(){
        batch = new SpriteBatch();
        font = new BitmapFont();
        Screen start = new Start(this);
        this.addScreen(start);
        this.setScreen(start);
    }

    public void render(){
        super.render();
    }

    public void dispose(){
        batch.dispose();
        font.dispose();
        for(Screen s: screens){
            s.dispose();
        }
    }
}
