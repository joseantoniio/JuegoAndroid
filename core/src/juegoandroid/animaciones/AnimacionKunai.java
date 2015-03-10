package juegoandroid.animaciones;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import juegoandroid.managers.MiAssetsManager;

/**
 * Created by jose on 02/03/2015.
 */
public class AnimacionKunai extends MiAnimacion{

    public AnimacionKunai(){
        animation=getAnimationKunai();
    }

    private Animation getAnimationKunai(){
        return new Animation(0.1f,cargarAnimacion(
                MiAssetsManager.getManager().get("animaciones/kunai.png",Texture.class),
                26,16,11));
    }

    @Override
    public TextureRegion getFrame(float delta) {
        duracion+=delta;
        return animation.getKeyFrame(duracion,true);
    }
}
