package juegoandroid.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
    private Personaje personaje;
    private SpriteBatch spriteBatch;
    private JuegoAndroid juegoAndroid;
    private HUD hud;
    private boolean ganar;
    private boolean perder;

    public PantallaJuego(JuegoAndroid juegoAndroid){
        this.juegoAndroid=juegoAndroid;
        ganar=false;
    }

    @Override
    public void show() {
        spriteBatch=new SpriteBatch();
        camara=new OrthographicCamera();
        //camara.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        camara.setToOrtho(false,MiMundo.ANCHO,MiMundo.ALTO);
        mundo=new MiMundo(this);
        personaje=new Personaje(this);
        hud=new HUD();
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
        hud.render();
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
        hud.dispose();
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
