package juegoandroid.entidades;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import juegoandroid.MiMundo;
import juegoandroid.pantallas.PantallaJuego;
import juegoandroid.animaciones.AnimacionKunai;
import juegoandroid.managers.EntidadesManager;
import juegoandroid.managers.MainFixtureBodyManager;


/**
 * Created by jose on 02/03/2015.
 */
public class Kunai extends MiEntidad{

    //Misma velocidad para todos los kunais
    private static float velocidad=5;

    private boolean isStaticBody;
    private float tiempoClavado;

    public Kunai(PantallaJuego juegoAndroid){
        super(juegoAndroid);
        EntidadesManager.anhadirEntidad(this);
        animacion=new AnimacionKunai();
        sprite.setSize(27, 16);
        crearCuerpo();
    }

    private void crearCuerpo(){
        Sprite spritePersonaje= pantallaJuego.getPersonaje().getSprite();
        Rectangle rectangle=sprite.getBoundingRectangle();
        rectangle.setPosition(spritePersonaje.getX()+spritePersonaje.getWidth(),
                spritePersonaje.getY()+25);
        cuerpo= MainFixtureBodyManager.colisionRectangulo(pantallaJuego.getWorld(), rectangle, "CuerpoKunai");
        cuerpo.setType(BodyDef.BodyType.DynamicBody);
        cuerpo.setGravityScale(0);

        //Fixture sensor para la punta del kunai
        PolygonShape shapePunta =new PolygonShape();
        shapePunta.set(new Vector2[]{
                new Vector2(sprite.getWidth() / MiMundo.CONVERSOR, 0),
                new Vector2(sprite.getWidth() / MiMundo.CONVERSOR + 0.05f, 0),
                new Vector2(sprite.getWidth() / MiMundo.CONVERSOR,
                        sprite.getHeight() / MiMundo.CONVERSOR),
                new Vector2(sprite.getWidth() / MiMundo.CONVERSOR + 0.05f,
                        sprite.getHeight() / MiMundo.CONVERSOR)
        });
        FixtureDef fixturePunta=new FixtureDef();
        fixturePunta.shape=shapePunta;
        fixturePunta.isSensor=true;

        cuerpo.createFixture(fixturePunta).setUserData(this);
        shapePunta.dispose();
        cuerpo.setLinearVelocity(velocidad, 0);
    }

    @Override
    public void render(float delta){
        //Si el cuerpo es estático es porque fue clavado contra algo.Queremos que permanezca
        //clavado un máximo de 2 segundos
        super.render(delta);
        if(isStaticBody) {
            //Convertiremos el cuerpo a estático si aún no lo era(Primera iteración)
            if (cuerpo.getType() != BodyDef.BodyType.StaticBody)
                cuerpo.setType(BodyDef.BodyType.StaticBody);
            //Usaremos delta para saber cuantos segundos han pasado
            tiempoClavado+=delta;
            if(tiempoClavado>2)
                EntidadesManager.eliminarEntidad(this);
        }
    }

    public static void setVelocidad(float velocidad){
        Kunai.velocidad=velocidad;
    }

    public void isStaticBody(boolean staticBody){
        this.isStaticBody =staticBody;
    }
}
