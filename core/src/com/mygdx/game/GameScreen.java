package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.ammo.Ammunition;
import com.mygdx.game.ammo.Bomb;
import com.mygdx.game.ammo.Laser;
import com.mygdx.game.ships.Alien;
import com.mygdx.game.ships.Boss;
import com.mygdx.game.ships.Goon;
import com.mygdx.game.ships.Vaisseau;
import java.util.Iterator;

public class GameScreen implements Screen {
	private final CaptainGoon captainGoon;

	private final OrthographicCamera camera;

	private final Goon goon;
	private final Alien alien1;
	private final Alien alien2;
	private final Boss boss;

	private long lastBombDrop;
	private long lastMissileDrop;

	private final Texture backgroundTexture;

	private final Array<Vaisseau> ships;
	private final Array<Ammunition> ammos;
	private final Array<Vaisseau> distroyed;


	private final TextureAtlas textureAtlas;
	private final Animation<TextureRegion> animation;
	private int victory = -1;
	private long victoryTime = 0;

	public GameScreen(CaptainGoon captainGoon){
		this.captainGoon = captainGoon;
		this.captainGoon.addScreen(this);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1000, 750);

		captainGoon.font.getData().setScale(1.5f, 1.5f);

		goon = new Goon();
		boss = new Boss(0, Gdx.graphics.getHeight() - 150, 5);
		alien1 = new Alien(0, Gdx.graphics.getHeight() - 200, 5);
		alien2 = new Alien(Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 200, 5);
		ships = new Array<>();
		ammos = new Array<>();
		distroyed = new Array<>();

		ships.add(goon, boss, alien1, alien2);

		backgroundTexture = new Texture(Gdx.files.internal("assets/backgrounds/Planets.jpg"));
		textureAtlas = new TextureAtlas(Gdx.files.internal("assets/sprites/kisspng-sprite-explosion_pack/kisspng-sprite-explosion_pack.atlas"));
		animation = new Animation<TextureRegion>(1f/30f, textureAtlas.getRegions());
	}

	@Override
	public void render (float delta) {
		ScreenUtils.clear(Color.WHITE);
		camera.update();
		captainGoon.batch.setProjectionMatrix(camera.combined);

		captainGoon.batch.begin();

		captainGoon.batch.draw(backgroundTexture, -100, -10, 1200, 760);

		for (Vaisseau ship : ships) {
			captainGoon.batch.setColor(1, (float) ship.getLife() / 100f, (float) ship.getLife() / 100f, 1);
			captainGoon.batch.draw(ship.getSprite(), ship.getX(), ship.getY(), ship.getWidth(), ship.getHeight());
			captainGoon.batch.setColor(Color.WHITE);
			drawBullets(ship);
			ship.hitboxUpdate();
			ship.move();
			dispawnBullets(ship);
		}

		dispawnShip();

		munitionExplosion();
		shipExplosion();

		hud();
		victoryDisplay();
		captainGoon.batch.end();

		checkVictory();
		if(victory > -1 && TimeUtils.nanoTime() - victoryTime >= 2000000000 && Gdx.input.isTouched()) captainGoon.setScreen(new Start(captainGoon));
	}

	@Override
	public void dispose () {
		captainGoon.batch.dispose();
		textureAtlas.dispose();

	}

	public void drawBullets(Vaisseau ship){
		spawnBullets(ship);
		for(Ammunition ammo : ship.getAmmos()){
			captainGoon.batch.draw(ammo.getSprite(), ammo.getX(), ammo.getY(), ammo.getWidth(), ammo.getHeight());
			if(ship instanceof Goon) ammo.setY(ammo.getY() + ammo.getSpeed());
			else ammo.setY(ammo.getY() - ammo.getSpeed());
		}
	}

	public void spawnBullets(Vaisseau ship){
		if(ship instanceof Goon){
			if(Gdx.input.justTouched()) ship.generateBullets();
		}
		else if(ship instanceof Boss && TimeUtils.nanoTime() - lastBombDrop > 1000000000){
			ship.generateBullets();
			lastBombDrop = TimeUtils.nanoTime();
		}
		else{
			if(TimeUtils.nanoTime() - lastMissileDrop > 800000000f){
				lastMissileDrop = TimeUtils.nanoTime();
				alien1.generateBullets();
				alien2.generateBullets();
			}
		}
	}

	public void dispawnBullets(Vaisseau ship){
		for(int i = 0; i < ships.size; i++){
			for (Iterator<Ammunition> iter = ship.getAmmos().iterator(); iter.hasNext(); ) {
				Ammunition ammo = iter.next();
				ammo.hitboxUpdate();
				if(ammo.getY() > Gdx.graphics.getHeight() || ammo.getY() + ammo.getHeight() < 0) iter.remove();
				if(collision(ammo, ship, ships.get(i))) {
					ammos.add(ammo);
					iter.remove();
				}
			}
		}
	}

	private boolean collision(Ammunition ammo, Vaisseau aggressor, Vaisseau touched){
		if (Intersector.overlaps(ammo.getHitbox(), touched.getHitbox())) {
			if (!(aggressor instanceof Boss && touched instanceof Alien)) {
				touched.setLife(touched.getLife() - ammo.getDmg());
				if(touched.getLife() <= 0) touched.setLife(0);
				return true;
			}
		}
		return false;
	}

	private void dispawnShip(){
		for(Iterator<Vaisseau> iter = ships.iterator(); iter.hasNext();){
			Vaisseau ship = iter.next();
			if(ship.getLife() <= 0) {
				distroyed.add(ship);
				iter.remove();
			}
		}
	}

	public void munitionExplosion(){
		for (Iterator<Ammunition> iter = ammos.iterator(); iter.hasNext(); ) {
			Ammunition ammo = iter.next();
			ammo.setElaspseTime(ammo.getElaspseTime() + Gdx.graphics.getDeltaTime());
			if(ammo instanceof Laser) captainGoon.batch.draw(animation.getKeyFrame(ammo.getElaspseTime()), ammo.getX() - 50, ammo.getY(), 100, 100);
			else if(ammo instanceof Bomb) captainGoon.batch.draw(animation.getKeyFrame(ammo.getElaspseTime()), ammo.getX() - ammo.getWidth(), ammo.getY() - ammo.getHeight()/2f, 100, 100);
			else captainGoon.batch.draw(animation.getKeyFrame(ammo.getElaspseTime()), ammo.getX() - 50, ammo.getY() - ammo.getHeight(), 100, 100);
			if (animation.isAnimationFinished(ammo.getElaspseTime())) {
				iter.remove();
			}
		}
	}

	public void shipExplosion(){
		for (Iterator<Vaisseau> iterator = distroyed.iterator(); iterator.hasNext(); ) {
			Vaisseau distroy = iterator.next();
			distroy.setElaspseTime(distroy.getElaspseTime() + Gdx.graphics.getDeltaTime());
			if( distroy instanceof Alien) captainGoon.batch.draw(animation.getKeyFrame(distroy.getElaspseTime()), distroy.getX() -100, distroy.getY() -100, 300, 300);
			else if(distroy instanceof Boss) captainGoon.batch.draw(animation.getKeyFrame(distroy.getElaspseTime()), distroy.getX() - 200, distroy.getY() - 200, 500, 500);
			else captainGoon.batch.draw(animation.getKeyFrame(distroy.getElaspseTime()), distroy.getX() - 225, distroy.getY() - 200, 500, 500);
			if (animation.isAnimationFinished(distroy.getElaspseTime())) {
				iterator.remove();
			}
		}
	}

	public void checkVictory(){
		if(goon.getLife() <= 0) {
			victory = 0;
			if(victoryTime == 0) victoryTime = TimeUtils.nanoTime();
		}

		else if(ships.size == 1) {
			victory = 1;
			if(victoryTime == 0) victoryTime = TimeUtils.nanoTime();
		}
	}

	public void victoryDisplay(){
		if(victory == 0) captainGoon.font.draw(captainGoon.batch, "GAME OVER", 420, 410);
		if(victory == 1) captainGoon.font.draw(captainGoon.batch, "YOU SAVE THE WORLD !", 370, 410);
	}

	public void hud(){
		captainGoon.batch.draw(goon.getSprite(), 10, 20, 50, 50);
		captainGoon.font.draw(captainGoon.batch, goon.getLife() + "%", 80, 50);
		captainGoon.batch.draw(boss.getSprite(), Gdx.graphics.getWidth() - 60, 20, 50, 50);
		captainGoon.font.draw(captainGoon.batch, boss.getLife() + "%", Gdx.graphics.getWidth() - 120, 50);
	}


	@Override
	public void resize(int width, int height) {
	}
	@Override
	public void show() {
	}
	@Override
	public void hide() {}
	@Override
	public void pause() {}
	@Override
	public void resume() {}
}
