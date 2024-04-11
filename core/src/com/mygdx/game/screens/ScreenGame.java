package com.mygdx.game.screens;

import static com.mygdx.game.MyGdxGame.SCR_HEIGHT;
import static com.mygdx.game.MyGdxGame.SCR_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.charters.Bird;
import com.mygdx.game.components.MovingBackground;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.components.PointCounter;
import com.mygdx.game.charters.Tube;

public class ScreenGame implements Screen {

    MyGdxGame myGdxGame;

    Bird bird;

    int tubeCount = 3;
    Tube[] tubes;
    int gamePoints;
    boolean isGameOver;
    PointCounter pointCounter;
    MovingBackground background = new MovingBackground("background/game_bg.png");
    final int pointCounterMarginTop = 60;
    final int pointCounterMarginRight = 400;

    public ScreenGame(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        initTubes();
        bird = new Bird(50, 0, 5, 150, 100);
    }


    @Override
    public void show() {
        isGameOver  = false;
        gamePoints = 0;
        bird.setStart();
        bird.setY(SCR_HEIGHT / 2);
        initTubes();
    }

    @Override
    public void render(float delta) {
        if (isGameOver) {
            myGdxGame.screenRestart.gamePoints = gamePoints;
            myGdxGame.setScreen(myGdxGame.screenRestart);
        }
        if (Gdx.input.justTouched()) {
            bird.onClick();
        }
        for (Tube tube : tubes) {
            tube.move();
            if  (tube.isHit(bird)) {
                System.out.println("hit");
                isGameOver = true;
            }
            else if (tube.needAddPoint(bird)) {
                gamePoints += 1;
                tube.setPointReceived();
                System.out.println(gamePoints);
            }
        }
        pointCounter = new PointCounter(SCR_WIDTH - pointCounterMarginRight, SCR_HEIGHT - pointCounterMarginTop);
        background.move();
        bird.fly();

        for (Tube tube : tubes) tube.move();

        ScreenUtils.clear(1, 0, 0, 1);
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        myGdxGame.batch.begin();
        background.draw(myGdxGame.batch);

        bird.draw(myGdxGame.batch);
        for (Tube tube : tubes) tube.draw(myGdxGame.batch);
        if (!bird.isInField()) {
            System.out.println("not in field");
            isGameOver = true;
        }
        pointCounter.draw(myGdxGame.batch, gamePoints);

        myGdxGame.batch.end();
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
        bird.dispose();
        pointCounter.dispose();
        background.dispose();
        for (int i = 0; i < tubeCount; i++) {
            tubes[i].dispose();
        }
    }

    void initTubes() {
        tubes = new Tube[tubeCount];
        for (int i = 0; i < tubeCount; i++) {
            tubes[i] = new Tube(tubeCount, i);
        }
    }

}