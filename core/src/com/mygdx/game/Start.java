package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

public class Start implements Screen {

    final CaptainGoon captainGoon;

    OrthographicCamera camera;

    public Start(final CaptainGoon captainGoon){
        this.captainGoon = captainGoon;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1000, 750);
    }

    public void render(float delta){
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        captainGoon.batch.setProjectionMatrix(camera.combined);

        captainGoon.batch.begin();
        captainGoon.font.draw(captainGoon.batch, "Welcome to Captain Goon !!!", 325, 425);
        captainGoon.font.draw(captainGoon.batch, "Tap anywhere to begin!", 360, 375);
        captainGoon.font.getData().setScale(2, 2);

        captainGoon.batch.end();

        if(Gdx.input.justTouched()){
            captainGoon.setScreen(new GameScreen(captainGoon));
        }

    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }


}
