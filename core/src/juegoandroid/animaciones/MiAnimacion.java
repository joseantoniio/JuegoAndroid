package juegoandroid.animaciones;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import juegoandroid.entidades.MiEntidad;

/**
 * Created by jose on 03/03/2015.
 */
public abstract class MiAnimacion {
    protected Animation animation;
    protected boolean loopAnimacion;
    protected float duracion;
    protected MiEntidad entidad;

    public MiAnimacion(MiEntidad entidad){
        this.entidad=entidad;
    }

    protected static TextureRegion[] cargarAnimacion(Texture texture, int ancho, int alto, int frames){
        TextureRegion region=new TextureRegion(texture);
        TextureRegion[][] temp=region.split(ancho,alto);
        TextureRegion[] regionArray=new TextureRegion[frames];
        for(int i=0;i<frames;i++){
            regionArray[i]=temp[0][i];
        }
        return regionArray;
    }

    public abstract TextureRegion getFrame(float delta);
}
