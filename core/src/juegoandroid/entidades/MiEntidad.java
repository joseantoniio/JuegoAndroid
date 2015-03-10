package juegoandroid.entidades;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

import juegoandroid.JuegoAndroid;
import juegoandroid.MiMundo;
import juegoandroid.animaciones.MiAnimacion;

/**
 * Created by jose on 04/03/2015.
 */
public abstract class MiEntidad {

    protected JuegoAndroid juegoAndroid;
    protected Sprite sprite;
    protected Body cuerpo;
    protected MiAnimacion animacion;

    public MiEntidad(JuegoAndroid juegoAndroid){
        this.juegoAndroid=juegoAndroid;
        this.sprite=new Sprite();
    }

    public void render(float delta){
        //Asignamos el respectivo frame que le toca de la animación
        sprite.setRegion(animacion.getFrame(delta));

        //Colocamos el sprite en la posición del cuerpo
        sprite.setPosition(cuerpo.getPosition().x * MiMundo.CONVERSOR,
                cuerpo.getPosition().y * MiMundo.CONVERSOR);
        sprite.draw(juegoAndroid.getSpriteBatch());
    }

    public Sprite getSprite(){return sprite;}
    public Body getCuerpo(){return cuerpo;}
}
