package juegoandroid;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

import juegoandroid.entidades.MiEntidad;
import juegoandroid.entidades.Personaje;
import juegoandroid.managers.EntidadesManager;
import juegoandroid.managers.MiAssetsManager;

public class JuegoAndroid extends ApplicationAdapter {
    //Debe ser cambiado para que extienda de Game para dar soporte a multiScreen

	MiMundo mundo;
	OrthographicCamera camara;
    Personaje personaje;
    SpriteBatch spriteBatch;

	@Override
	public void create () {
        //Cargamos el AssetManager cada vez que el juego se cree
        MiAssetsManager.cargar();
        EntidadesManager.incializarEntidades();
        spriteBatch=new SpriteBatch();
        camara=new OrthographicCamera();
        //camara.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        camara.setToOrtho(false,600,450);
        mundo=new MiMundo(this);
        personaje=new Personaje(this);
	}

	@Override
	public void render () {
        float delta=Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camara.update();
        mundo.render(delta);
        spriteBatch.setProjectionMatrix(camara.combined);
        spriteBatch.begin();
        EntidadesManager.render(delta);
        spriteBatch.end();
	}

    public void dispose(){
        mundo.dispose();
        MiAssetsManager.getManager().dispose();
        spriteBatch.dispose();
    }

    //    <--------600-------->
    //    |                   |
    //   450                 450
    //    |                   |
    //    <--------600-------->
    //    Quiero que la altura del mundo siempre ocupe toda la pantalla independientemente de
    //    su tamaño, solo cambiará la distancia que se ve a lo ancho.
    //
    //    Cuando la ventana mida "x" de alto, el valor de escalado que usará la cámara
    //    es 450/x, solo deberemos hacer zoom ese mismo valor para obtener
    //    el resultado deseado.

    @Override
    public void resize(int width, int height) {
        camara.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        camara.zoom=450f/Gdx.graphics.getHeight();;
    }

    public Personaje getPersonaje(){return personaje;}
    public World getWorld(){return mundo.getWorld();}
    public OrthographicCamera getCamara(){return camara;}
    public SpriteBatch getSpriteBatch(){return spriteBatch;}
}