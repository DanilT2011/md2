package com.mygdx.game.charters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import static com.mygdx.game.MyGdxGame.SCR_HEIGHT;

public class Bird {

    public int x;
    public int y;
    public int width;
    public int height;

    int speed;
    int jumpHeight;
    final int maxHeightOfJump = 200;
    boolean jump;

    int frameCounter;
    Texture[] framesArray;

    public Bird(int x, int y, int speed, int width, int height) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.width = width;
        this.height = height;
        frameCounter = 0;

        framesArray = new Texture[]{
                new Texture("bird/bird0.png"),
                new Texture("bird/bird1.png"),
                new Texture("bird/bird2.png"),
                new Texture("bird/bird1.png"),
        };
    }

    public void onClick() {
        jump = true;
        jumpHeight = maxHeightOfJump + y;
    }

    public void fly() {
        if (y >= jumpHeight) {
            jump = false;
        }

        if (jump) {
            y += (speed + 1);
        } else {
            y -= (speed + 2);
        }
    }

    public void draw(Batch batch) {
        int frameMultiplier = 10;
        batch.draw(framesArray[frameCounter / frameMultiplier], x, y, width, height);
        if (frameCounter++ == framesArray.length * frameMultiplier - 1) {
            frameCounter = 0;
        }
    }

    public void dispose() {
        for (Texture texture : framesArray) {
            texture.dispose();
        }
    }

    public boolean isInField() {
        if (y + height < 0) {
            return false;
        }
        if (y > SCR_HEIGHT) {
            return false;
        }
        return true;
    }

    public void setY(int y){
        this.y = y;
    }

    public void setStart(){
        jump = false;
        jumpHeight = maxHeightOfJump;
    }
}