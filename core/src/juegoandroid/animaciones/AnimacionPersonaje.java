package juegoandroid.animaciones;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import juegoandroid.entidades.MiEntidad;
import juegoandroid.managers.MiAssetsManager;


/**
 * Created by jose on 01/03/2015.
 */
public class AnimacionPersonaje extends MiAnimacion{

    public enum ANIMACION_ACTUAL{
        CORRER,AIRTHROW,WIN
    }

    private ANIMACION_ACTUAL animacion_actual;

    public AnimacionPersonaje(MiEntidad entidad){
        super(entidad);
        animacion_actual=ANIMACION_ACTUAL.CORRER;
        animation=getAnimationRun();
        loopAnimacion=true;
    }

    @Override
    public TextureRegion getFrame(float delta){
        duracion+=delta;
        if(animacion_actual!= ANIMACION_ACTUAL.CORRER && animacion_actual!=ANIMACION_ACTUAL.WIN
                && animation.getAnimationDuration()<duracion)
            setAnimationPersonaje(ANIMACION_ACTUAL.CORRER);
        if(animacion_actual==ANIMACION_ACTUAL.WIN && animation.getAnimationDuration()<duracion)
            entidad.getPantallaJuego().ganarJuego();
        return animation.getKeyFrame(duracion,loopAnimacion);
    }

    private Animation getAnimationRun(){
        return new Animation(0.1f,cargarAnimacion(
                MiAssetsManager.getManager().get("animaciones/narutoRun.png",Texture.class),
                65,53,6));
    }

    private Animation getAnimationAirThrow(){
        return new Animation(0.1f,cargarAnimacion(
                MiAssetsManager.getManager().get("animaciones/narutoAirThrow.png",Texture.class),
                56,64,3));
    }

    private Animation getAnimationWin(){
        return new Animation(0.15f,cargarAnimacion(
                MiAssetsManager.getManager().get("animaciones/narutoWin.png",Texture.class),
                58,92,8));
    }

    public void setAnimationPersonaje(ANIMACION_ACTUAL animacion) {
        switch (animacion) {
            case AIRTHROW:
                animacion_actual = ANIMACION_ACTUAL.AIRTHROW;
                duracion = 0;
                animation = getAnimationAirThrow();
                loopAnimacion = false;
                break;
            case CORRER:
                animacion_actual = ANIMACION_ACTUAL.CORRER;
                duracion = 0;
                animation = getAnimationRun();
                loopAnimacion = true;
                break;
            case WIN:
                animacion_actual = ANIMACION_ACTUAL.WIN;
                duracion = 0;
                animation = getAnimationWin();
                loopAnimacion = false;
                break;
        }
    }
}
