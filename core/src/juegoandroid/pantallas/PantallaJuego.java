package juegoandroid.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

import juegoandroid.JuegoAndroid;
import juegoandroid.MiMundo;
import juegoandroid.entidades.Personaje;
import juegoandroid.managers.EntidadesManager;

/**
 * Created by jose on 10/03/2015.
 */
public class PantallaJuego implements Screen {

    private MiMundo mundo;
    private OrthographicCamera camara;
    private OrthographicCamera camaraDatos;
    private Personaje personaje;
    private SpriteBatch spriteBatch;
    private JuegoAndroid juegoAndroid;
    private boolean ganar;
    private boolean perder;
    BitmapFont font;

    public PantallaJuego(JuegoAndroid juegoAndroid){
        this.juegoAndroid=juegoAndroid;
        ganar=false;
    }

    @Override
    public void show() {
        spriteBatch=new SpriteBatch();
        camara=new OrthographicCamera();
        camaraDatos=new OrthographicCamera();
        //camara.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        camara.setToOrtho(false,MiMundo.ANCHO,MiMundo.ALTO);
        camaraDatos.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        mundo=new MiMundo(this);
        personaje=new Personaje(this);
        font=new BitmapFont();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camara.update();
        mundo.render(delta);
        spriteBatch.setProjectionMatrix(camara.combined);
        spriteBatch.begin();
        EntidadesManager.render(delta);
        spriteBatch.end();
        spriteBatch.setProjectionMatrix(camaraDatos.combined);
        spriteBatch.begin();
        font.draw(spriteBatch,"FPS: " + Gdx.graphics.getFramesPerSecond(),10, 20);
        spriteBatch.end();
        if(ganar)
            juegoAndroid.setScreen(new PantallaPresentacion(juegoAndroid));
        if(perder) {
            juegoAndroid.setScreen(new PantallaGameOver(juegoAndroid, this));
            perder=false;
        }
    }

    @Override
    public void resize(int width, int height) {
        camara.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        camara.zoom=450f/Gdx.graphics.getHeight();
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
        System.out.println("Muere");
        mundo.dispose();
        spriteBatch.dispose();
    }

    public void perderJuego(){
        EntidadesManager.clear();
        perder=true;
    }

    public void ganarJuego(){
        ganar=true;
        EntidadesManager.clear();
    }

    public Personaje getPersonaje(){return personaje;}
    public World getWorld(){return mundo.getWorld();}
    public OrthographicCamera getCamara(){return camara;}
    public SpriteBatch getSpriteBatch(){return spriteBatch;}
}
