package com.mygdx.game.charters;
import com.badlogic.gdx.graphics.Texture;
import java.util.Random;
import com.badlogic.gdx.graphics.g2d.Batch;
import static com.mygdx.game.MyGdxGame.SCR_HEIGHT;
import static com.mygdx.game.MyGdxGame.SCR_WIDTH;

public class Tube {
    Random random;
    int x, gapY, distanceBetweenTubes;

    Texture textureUpperTube;
    Texture textureDownTube;
    int speed = 5;
    final int width = 200;
    final int height = 700;
    int gapHeight = 400;
    int padding = 100;
    boolean isPointReceived = false;

    public Tube(int tubeCount, int tubeIdx) {
        random = new Random();

        gapY = gapHeight / 2 + padding + random.nextInt(SCR_HEIGHT - 2 * (padding + gapHeight / 2));
        distanceBetweenTubes = (SCR_WIDTH + width) / (tubeCount - 1);
        x = distanceBetweenTubes * tubeIdx + SCR_WIDTH;

        textureUpperTube = new Texture("tubes/tube_flipped.png");
        textureDownTube = new Texture("tubes/tube.png");
    }
    public void draw(Batch batch) {
        batch.draw(textureUpperTube, x, gapY + gapHeight / 2, width, height);
        batch.draw(textureDownTube, x, gapY - gapHeight / 2 - height, width, height);
    }
    public void dispose() {
        textureDownTube.dispose();
        textureUpperTube.dispose();
    }
    public void move() {
        x -= speed;
        if (x < -width) {
            x = SCR_WIDTH + distanceBetweenTubes;
            gapY = gapHeight / 2 + padding + random.nextInt(SCR_HEIGHT - 2 * (padding + gapHeight / 2));
            isPointReceived = false;
        }
    }
    public boolean isHit(com.mygdx.game.charters.Bird bird){
        if  (bird.y <= gapY - gapHeight  / 2 && bird.x + bird.width >= x && bird.x <= x + width){
            return true;
        }
        else if (bird.y + bird.height >= gapY + gapHeight / 2 && bird.x + bird.width >= x && bird.x <= x)
            return true;

        return false;
    }

    public boolean needAddPoint(com.mygdx.game.charters.Bird bird) {
        return bird.x > x && !isPointReceived;
    }

    public void setPointReceived() {
        isPointReceived = true;
    }
}