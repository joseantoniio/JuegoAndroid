package juegoandroid.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by jose on 12/03/2015.
 */
public class HUD implements Disposable {

    private SpriteBatch spriteBatch;
    private BitmapFont font;
    private OrthographicCamera camara;

    public HUD(){
        spriteBatch=new SpriteBatch();
        font=new BitmapFont();
        camara=new OrthographicCamera();
        camara.setToOrtho(false, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
    }

    public void render(){
        spriteBatch.begin();
        font.draw(spriteBatch,"FPS: " + Gdx.graphics.getFramesPerSecond(),10, 20);
        spriteBatch.end();
    }

    @Override
    public void dispose(){
        spriteBatch.dispose();
    }

}
