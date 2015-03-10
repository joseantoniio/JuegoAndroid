package juegoandroid.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import juegoandroid.JuegoAndroid;
import juegoandroid.MiMundo;
import juegoandroid.managers.MiAssetsManager;

/**
 * Created by jose on 09/03/2015.
 */
public class PantallaGameOver implements Screen, InputProcessor{

    OrthographicCamera camara;
    SpriteBatch spriteBatch;
    PantallaJuego pantallaJuego;
    Texture texture;
    JuegoAndroid juegoAndroid;

    Rectangle rectangleReiniciar;
    Rectangle rectangleSalir;


    public PantallaGameOver(JuegoAndroid juegoAndroid,PantallaJuego pantallaJuego){
        this.juegoAndroid=juegoAndroid;
        this.pantallaJuego=pantallaJuego;
    }

    @Override
    public void show() {
        spriteBatch=new SpriteBatch();
        camara=new OrthographicCamera();
        camara.setToOrtho(false, MiMundo.ANCHO,MiMundo.ALTO);
        texture=MiAssetsManager.getManager().get("gameover.png", Texture.class);
        rectangleReiniciar=new Rectangle(230,MiMundo.ALTO-270,160,40);
        rectangleSalir=new Rectangle(270,MiMundo.ALTO-305,70,30);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        spriteBatch.setProjectionMatrix(camara.combined);
        spriteBatch.begin();
        spriteBatch.draw(texture,0,0,MiMundo.ANCHO,MiMundo.ALTO);
        spriteBatch.end();

        //Debug
        ShapeRenderer shapeRenderer=new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camara.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.rect(rectangleReiniciar.x, rectangleReiniciar.y, rectangleReiniciar.width, rectangleReiniciar.height);
        shapeRenderer.rect(rectangleSalir.x,rectangleSalir.y,rectangleSalir.width,rectangleSalir.height);
        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {
        camara.setToOrtho(false, MiMundo.ANCHO,MiMundo.ALTO);
        camara.update();
        spriteBatch.setProjectionMatrix(camara.combined);
        spriteBatch.disableBlending();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
        spriteBatch.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 temporal=camara.unproject(new Vector3(screenX,screenY,0));

        Circle dedo=new Circle(temporal.x,temporal.y,5);
        if(Intersector.overlaps(dedo,rectangleSalir))
            Gdx.app.exit();
        if(Intersector.overlaps(dedo,rectangleReiniciar))
            juegoAndroid.setScreen(pantallaJuego);

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
