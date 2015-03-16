package juegoandroid.entidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import juegoandroid.MiMundo;
import juegoandroid.managers.MiAssetsManager;
import juegoandroid.pantallas.PantallaJuego;
import juegoandroid.PersonajeInputAdapter;
import juegoandroid.animaciones.AnimacionPersonaje;
import juegoandroid.managers.EntidadesManager;
import juegoandroid.managers.MainFixtureBodyManager;


/**
 * Created by jose on 01/03/2015.
 */
public class Personaje extends MiEntidad{

    private static Vector2 POSICION_INICIAL;

    private float velocidad;
    private boolean apoyado;

    public Personaje(PantallaJuego pantallaJuego){
        super(pantallaJuego);
        EntidadesManager.anhadirEntidad(this);
        //Por si fuera modificada la velocidad de los kunais, la volvemos a su velocidad inicial
        Kunai.setVelocidad(5);
        animacion=new AnimacionPersonaje(this);
        Gdx.input.setInputProcessor(new PersonajeInputAdapter(this));
        sprite.setSize(65, 53);
        sprite.setPosition(10, 50);
        POSICION_INICIAL=new Vector2(10/MiMundo.CONVERSOR,50/MiMundo.CONVERSOR);
        crearHitbox();
        velocidad=2.5f;
    }

    private void crearHitbox(){
        //Crea un cuerpo y una fixture cuadrados con tamaño igual al sprite
        cuerpo= MainFixtureBodyManager.colisionRectangulo(pantallaJuego.getWorld(),
                sprite.getBoundingRectangle(), "Personaje");
        //Como el método anterior crea los cuerpo estáticos, especificamos que debe ser dinámico
        cuerpo.setType(BodyDef.BodyType.DynamicBody);
        //cuerpo.setMassData(new MassData());

        //Fixture sensor para colisión lateral
        PolygonShape shapeLateral =new PolygonShape();
        shapeLateral.set(new Vector2[]{
                new Vector2(sprite.getWidth() / MiMundo.CONVERSOR, 0.05f),
                new Vector2(sprite.getWidth() / MiMundo.CONVERSOR + 0.05f, 0.05f),
                new Vector2(sprite.getWidth() / MiMundo.CONVERSOR + 0.05f,
                        sprite.getHeight() / MiMundo.CONVERSOR),
                new Vector2(sprite.getWidth() / MiMundo.CONVERSOR,
                        sprite.getHeight() / MiMundo.CONVERSOR)
        });
        FixtureDef fixtureLateral =new FixtureDef();
        fixtureLateral.isSensor=true;
        fixtureLateral.shape= shapeLateral;

        //Fixture sensor para los pies
        PolygonShape shapePies=new PolygonShape();
        shapePies.set(new Vector2[]{
                new Vector2(0,0),
                new Vector2(0,-0.05f),
                new Vector2(sprite.getWidth()/MiMundo.CONVERSOR-0.05f,0),
                new Vector2(sprite.getWidth()/MiMundo.CONVERSOR-0.05f,-0.05f)
        });
        FixtureDef fixturePies=new FixtureDef();
        fixturePies.isSensor=true;
        fixturePies.shape=shapePies;

        cuerpo.createFixture(fixturePies).setUserData("Pies");
        cuerpo.createFixture(fixtureLateral).setUserData("Lateral");
        cuerpo.setBullet(true);

        shapeLateral.dispose();
        shapePies.dispose();
    }

    @Override
    public void render(float delta){
        super.render(delta);

        //Modificaremos solo la velocidad en el eje x
        cuerpo.setLinearVelocity(velocidad,cuerpo.getLinearVelocity().y);

        if(pantallaJuego.getCamara().position.x
                <MiMundo.ANCHO_TOTAL-Gdx.graphics.getWidth()/2f *pantallaJuego.getCamara().zoom)
            //Colocamos la cámara en la posición deseada en cada movimiento, así se moverá al mismo tiempo
            pantallaJuego.getCamara().position.set(cuerpo.getPosition().x*100f+
                            (Gdx.graphics.getWidth()/2f-100)*pantallaJuego.getCamara().zoom,30*15/2,0);
        if(cuerpo.getPosition().y<0) {
            cuerpo.setTransform(POSICION_INICIAL, cuerpo.getAngle());
            pantallaJuego.perderJuego();
        }
    }

    public void setVelocidad(float velocidad){
        this.velocidad=velocidad;
    }

    public void setApoyado(boolean apoyado){
        this.apoyado=apoyado;
    }

    public void saltar(){
        //Solo puede saltar si se encuentra apoyado
        if(apoyado) {
            MiAssetsManager.getManager().get("sonidos/jump_air.mp3",Sound.class).play();
            cuerpo.applyLinearImpulse(new Vector2(0, 3.5f), cuerpo.getWorldCenter(), true);
        }
    }

    public void disparar(){
        MiAssetsManager.getManager().get("sonidos/knife_slash1.mp3", Sound.class).play();
        ((AnimacionPersonaje) animacion).setAnimationPersonaje(
                        AnimacionPersonaje.ANIMACION_ACTUAL.AIRTHROW);
        new Kunai(pantallaJuego);
    }

    public void ganar(){
        sprite.setSize(58,96);
        ((AnimacionPersonaje)animacion).setAnimationPersonaje(
                AnimacionPersonaje.ANIMACION_ACTUAL.WIN);
    }

    public void aplicarImpulso(Vector2 impulso){
        cuerpo.setLinearVelocity(0,0);
        cuerpo.applyLinearImpulse(impulso,cuerpo.getWorldCenter(),true);
    }

    public boolean isApoyado(){return apoyado;}
}